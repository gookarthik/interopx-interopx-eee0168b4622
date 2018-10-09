package com.interopx.platform.datamatching.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.interopx.platform.datamatching.dao.PatientMatchingDao;
import com.interopx.platform.repository.model.ConditionResource;
import com.interopx.platform.repository.model.Conflicts;
import com.interopx.platform.repository.model.DataProcessingStatus;
import com.interopx.platform.repository.model.EncounterResource;
import com.interopx.platform.repository.model.Groups;
import com.interopx.platform.repository.model.ImmunizationResource;
import com.interopx.platform.repository.model.LabResultsResource;
import com.interopx.platform.repository.model.LocationResource;
import com.interopx.platform.repository.model.MedicationResource;
import com.interopx.platform.repository.model.PatientResource;
import com.interopx.platform.repository.model.ProcedureResource;
import com.interopx.platform.repository.model.SmokingResource;
import com.interopx.platform.repository.model.VitalSigns;
import com.interopx.platform.repository.service.AllergyIntoleranceService;
import com.interopx.platform.repository.service.ConditionService;
import com.interopx.platform.repository.service.ConflictsService;
import com.interopx.platform.repository.service.EncounterService;
import com.interopx.platform.repository.service.GroupsService;
import com.interopx.platform.repository.service.ImmunizationService;
import com.interopx.platform.repository.service.LabResultsService;
import com.interopx.platform.repository.service.LocationService;
import com.interopx.platform.repository.service.MedicationService;
import com.interopx.platform.repository.service.PatientService;
import com.interopx.platform.repository.service.ProcedureService;
import com.interopx.platform.repository.service.SmokingService;
import com.interopx.platform.repository.service.VitalsService;
import com.interopx.platform.repository.util.CommonUtil;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;
import com.interopx.platform.repository.resource.Patient;

@Service
@Transactional
public class PatientMatchingServiceImpl implements PatientMatchingService {

	@Autowired
	private PatientMatchingDao patientMatchingDao;

	@Autowired
	private PatientService patientService;

	@Autowired
	private AllergyIntoleranceService allergyService;

	@Autowired
	private ConditionService conditionService;

	@Autowired
	private EncounterService encounterService;

	@Autowired
	private ImmunizationService immunizationService;

	@Autowired
	private LabResultsService labResultsService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private MedicationService medicationService;

	@Autowired
	private ProcedureService procedureService;

	@Autowired
	private SmokingService smokingService;

	@Autowired
	private VitalsService vitalSignsService;

	@Autowired
	private GroupsService groupService;

	@Autowired
	private ConflictsService conflictsService;

	@Override
	public List<PatientResource> processPatientMatchingAndLinking(Integer extractionId) {

		// Get New Patient records to process Id Matching
		List<PatientResource> newPatientResources = patientService.getPatientsByetIdandProcessingStatus(extractionId,
				DataProcessingStatusEnum.PATIENTIDMATCHINGANDLINKING, false);

		for (PatientResource newPatientResource : newPatientResources) {

			// Re-check -- Read query fields from properties file as comma separated string
			String queryElements = "firstName,lastName,dateOfBirth,adminGender";

			if (queryElements != null && !queryElements.isEmpty()) {
				List<String> matchingElements = new ArrayList<String>(Arrays.asList(queryElements.split(",")));

				// Construct criteria object to fetch similar records from table
				Patient criteriaPatient = constructCriteriaPatientObject(newPatientResource, matchingElements);

				// Get Matching patients
				List<PatientResource> matchingPatients = getPatientsBySearchCriteria(criteriaPatient);

				// Set Data processing status for Matching and linking service
				String newResourceDataStatus = newPatientResource.getDataProcessingStatus();
				DataProcessingStatus processingStatus = new Gson().fromJson(newResourceDataStatus,
						DataProcessingStatus.class);
				processingStatus.setIdMatchingStatus(true);

				if (matchingPatients.size() > 0) {
					// process the matching and linking
					List<Groups> resultGroups = patientMatchingAndLinkingAlgorithm(newPatientResource, matchingPatients,
							matchingElements);

					conflictManagement(resultGroups, processingStatus, newPatientResource.getActualPatientId());
				} else {
					// Assigning New InteropX Patient Id for Non Matching records.
					String interopxPatientId = "IX-P-" + CommonUtil.generateRandomString(6);
					patientService.updateInteropXPatientId(newPatientResource.getId(), interopxPatientId,
							DataProcessingStatusEnum.PATIENTIDMATCHINGANDLINKING, true);
					updateInteropXIdForResources(newPatientResource.getActualPatientId(), interopxPatientId,
							DataProcessingStatusEnum.PATIENTIDMATCHINGANDLINKING, true);
				}
			}
		}

		return null;
	}

	@Override
	public List<PatientResource> getPatientsBySearchCriteria(Patient criteriaPatient) {

		// Get Matching Existing records with the new record
		List<PatientResource> matchingPatients = patientMatchingDao.getPatientsBySearchCriteria(criteriaPatient);

		return matchingPatients;
	}

	private Patient constructCriteriaPatientObject(PatientResource newPatientResource, List<String> matchingElements) {
		Patient criteriaPatient = new Patient();

		if (matchingElements.contains("firstName"))
			criteriaPatient.setFirstName(newPatientResource.getFirstName());

		if (matchingElements.contains("lastName"))
			criteriaPatient.setLastName(newPatientResource.getLastName());

		if (matchingElements.contains("dateOfBirth"))
			criteriaPatient.setDateOfBirth(newPatientResource.getDob());

		if (matchingElements.contains("adminGender"))
			criteriaPatient.setAdminGender(newPatientResource.getGender());

		return criteriaPatient;
	}

	private List<Groups> patientMatchingAndLinkingAlgorithm(PatientResource newPatientResource,
			List<PatientResource> matchingPatients, List<String> matchingElements) {

		List<Groups> groupsList = new ArrayList<>();
		for (PatientResource matchingPatient : matchingPatients) {
			List<String> matchedElements = new ArrayList<>();
			List<String> misMatchedElements = new ArrayList<>();
			List<Boolean> matches = new ArrayList<>();
			if (matchingPatient.getFirstName().contains(newPatientResource.getFirstName())) {
				matches.add(true);
				matchedElements.add("firstName");
			} else {
				misMatchedElements.add("firstName");
			}

			if (matchingPatient.getLastName().contains(newPatientResource.getLastName())) {
				matches.add(true);
				matchedElements.add("lastName");
			} else {
				misMatchedElements.add("lastName");
			}

			if (matchingPatient.getDob().equals(newPatientResource.getDob())) {
				matches.add(true);
				matchedElements.add("dateOfBirth");
			} else {
				misMatchedElements.add("dateOfBirth");
			}

			if (matchingPatient.getGender().equals(newPatientResource.getGender())) {
				matches.add(true);
				matchedElements.add("adminGender");
			} else {
				misMatchedElements.add("adminGender");
			}

			float score = (float) (matches.size() * 100) / matchingElements.size();

			// set group object
			Groups group = new Groups();
			group.setDataSourceId(matchingPatient.getDataSourceId());
			group.setExtractionId(matchingPatient.getExtractionTaskId());
			group.setInteropxId(matchingPatient.getInteropxPatientId());
			group.setInteropxRecord(matchingPatient.getData());
			Gson gson = new Gson();
			group.setQueriedElements(gson.toJson(matchingElements));

			// set conflict object
			List<Conflicts> conflicts = new ArrayList<>();
			Conflicts conflict = new Conflicts();
			conflict.setDataSourceId(newPatientResource.getDataSourceId());
			conflict.setExtractionId(newPatientResource.getExtractionTaskId());
			conflict.setMatchedElements(gson.toJson(matchedElements));
			conflict.setMisMatchedElements(gson.toJson(misMatchedElements));
			conflict.setRecordId(newPatientResource.getId());
			conflict.setScore(score);
			conflict.setConflictedRecord(newPatientResource.getData());
			conflicts.add(conflict);
			group.setConflicts(conflicts);

			groupsList.add(group);

		}
		return groupsList;
	}

	private void conflictManagement(List<Groups> resultGroups, DataProcessingStatus processingStatus,
			String actualPatientId) {

		for (Groups group : resultGroups) {

			List<Conflicts> conflicts = group.getConflicts();

			for (Conflicts conflict : conflicts) {

				if (conflict.getScore() == 100) {

					patientService.updateInteropXPatientId(conflict.getRecordId(), group.getInteropxId(),
							DataProcessingStatusEnum.PATIENTIDMATCHINGANDLINKING, true);

					updateInteropXIdForResources(actualPatientId, group.getInteropxId(),
							DataProcessingStatusEnum.PATIENTIDMATCHINGANDLINKING, true);

				} else if (conflict.getScore() > 50 && conflict.getScore() < 100) {

					List<Groups> checkGroups = groupService.getGroupsByInteropXPatientId(group.getInteropxId());

					if (checkGroups.size() > 0) {

						Groups groups = checkGroups.get(0);

						List<Conflicts> checkExcluededConflicts = conflictsService
								.getConflictsByRecordIdAndExcluded(groups.getGroupId(),conflict.getRecordId(), true);
						
						List<Conflicts> checkConflictStatus = conflictsService
								.getConflictsByRecordIdAndExcluded(null,conflict.getRecordId(), false);

						if (checkExcluededConflicts.size() == 0 && checkConflictStatus.size() == 0) {
							
							groups.setConflicts(conflicts);

							conflict.setGroups(groups);

							groupService.saveOrUpdate(groups);
							
						} 

					} else {

						conflict.setGroups(group);

						groupService.saveOrUpdate(group);

					}

					patientService.updateInteropXPatientId(conflict.getRecordId(), null,
							DataProcessingStatusEnum.PATIENTIDMATCHINGANDLINKING, true);

				} else if (conflict.getScore() <= 50) {

					String interopxPatientId = "IX-P-" + CommonUtil.generateRandomString(6);

					patientService.updateInteropXPatientId(conflict.getRecordId(), interopxPatientId,
							DataProcessingStatusEnum.PATIENTIDMATCHINGANDLINKING, true);

					updateInteropXIdForResources(actualPatientId, interopxPatientId,
							DataProcessingStatusEnum.PATIENTIDMATCHINGANDLINKING, true);

				}
			}
		}
	}

	private void updateInteropXIdForResources(String actualPatientId, String interopXPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		allergyService.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
		conditionService.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
		encounterService.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
		immunizationService.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName,
				processingStatus);
		labResultsService.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
		locationService.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
		medicationService.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
		procedureService.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
		smokingService.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
		vitalSignsService.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
	}

}
