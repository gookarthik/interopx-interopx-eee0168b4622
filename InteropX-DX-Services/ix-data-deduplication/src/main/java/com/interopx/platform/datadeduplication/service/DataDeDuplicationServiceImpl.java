package com.interopx.platform.datadeduplication.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interopx.platform.repository.model.AllergyIntolerance;
import com.interopx.platform.repository.model.ConditionResource;
import com.interopx.platform.repository.model.EncounterResource;
import com.interopx.platform.repository.model.ImmunizationResource;
import com.interopx.platform.repository.model.LabResultsResource;
import com.interopx.platform.repository.model.LocationResource;
import com.interopx.platform.repository.model.MedicationResource;
import com.interopx.platform.repository.model.ProcedureResource;
import com.interopx.platform.repository.model.SmokingResource;
import com.interopx.platform.repository.model.VitalSigns;
import com.interopx.platform.repository.service.AllergyIntoleranceService;
import com.interopx.platform.repository.service.ConditionService;
import com.interopx.platform.repository.service.EncounterService;
import com.interopx.platform.repository.service.ImmunizationService;
import com.interopx.platform.repository.service.LabResultsService;
import com.interopx.platform.repository.service.LocationService;
import com.interopx.platform.repository.service.MedicationService;
import com.interopx.platform.repository.service.ProcedureService;
import com.interopx.platform.repository.service.SmokingService;
import com.interopx.platform.repository.service.VitalsService;
import com.interopx.platform.repository.util.CommonUtil;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;
import com.interopx.platform.repository.resource.Allergy;
import com.interopx.platform.repository.resource.Encounter;
import com.interopx.platform.repository.resource.Immunization;
import com.interopx.platform.repository.resource.LabResults;
import com.interopx.platform.repository.resource.Location;
import com.interopx.platform.repository.resource.Medication;
import com.interopx.platform.repository.resource.Problem;
import com.interopx.platform.repository.resource.Procedure;
import com.interopx.platform.repository.resource.Smoking;
import com.interopx.platform.repository.resource.Vitals;
@Service
@Transactional
public class DataDeDuplicationServiceImpl implements DataDeDuplicationService {
	
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
	private MedicationService medicationService;
	
	@Autowired
	private ProcedureService procedureService;
	
	@Autowired
	private SmokingService smokingService;
	
	@Autowired
	private VitalsService vitalsService;
	
	@Autowired
	private LocationService locationService;

	public void processDeDuplication(Integer etId) {
		processAllergyDeDuplication(etId);
		processConditionDeDuplication(etId);
		processEncounterDeDuplication(etId);
		processImmunizationDeDuplication(etId);
		processLabResultDeDuplication(etId);
		processMedicationDeDuplication(etId);
		processProcedureDeDuplication(etId);
		processSmokingDeDuplication(etId);
		processVitalsDeDuplication(etId);
		processLocationDeDuplication(etId);
	}

	public void processAllergyDeDuplication(Integer etId) {

		List<AllergyIntolerance> allergyList = allergyService.getAllergiesByEtIdAndProcessingStatus(etId,
				DataProcessingStatusEnum.DEDUPLICATION, false);

		for (AllergyIntolerance allergy : allergyList) {

			ObjectMapper mapper = new ObjectMapper();
			Allergy al = new Allergy();
			try {
				al = mapper.readValue(allergy.getData(), Allergy.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<AllergyIntolerance> dupAllergies = allergyService.getDuplicateAllergies(allergy.getId(),
					al.getAllergyCode(), al.getAllergyCreateTimestamp(), allergy.getInteropxPatientId());

			if (dupAllergies.size() > 0) {
				allergyService.updateInteropXAllergyId(allergy.getId(),
						dupAllergies.get(0).getInteropxAllergyIntoleranceId(), DataProcessingStatusEnum.DEDUPLICATION,
						true);
			} else {
				String interopxAllergyId = "IX-A-" + CommonUtil.generateRandomString(6);
				allergyService.updateInteropXAllergyId(allergy.getId(), interopxAllergyId,
						DataProcessingStatusEnum.DEDUPLICATION, true);
			}
		}
	}

	public void processConditionDeDuplication(Integer etId) {

		List<ConditionResource> conditionList = conditionService.getConditionsByEtIdAndProcessingStatus(etId, DataProcessingStatusEnum.DEDUPLICATION, false);

		for (ConditionResource condition : conditionList) {

			ObjectMapper mapper = new ObjectMapper();
			Problem problem = new Problem();
			try {
				problem = mapper.readValue(condition.getData(), Problem.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<ConditionResource> dupConditions = conditionService.getDuplicateConditions(condition.getId(), problem.getProblemCode(), problem.getCreateTimestamp(), condition.getInteropxPatientId());

			if (dupConditions.size() > 0) {
				conditionService.updateInteropXConditionId(condition.getId(),
						dupConditions.get(0).getInteropxConditionId(), DataProcessingStatusEnum.DEDUPLICATION, true);
			} else {
				String interopxConditionId = "IX-C-" + CommonUtil.generateRandomString(6);
				conditionService.updateInteropXConditionId(condition.getId(), interopxConditionId,
						DataProcessingStatusEnum.DEDUPLICATION, true);
			}
		}
	}
	
	public void processEncounterDeDuplication(Integer etId) {

		List<EncounterResource> encounterList = encounterService.getEncountersByEtIdAndProcessingStatus(etId, DataProcessingStatusEnum.DEDUPLICATION, false);

		for (EncounterResource encounter : encounterList) {

			ObjectMapper mapper = new ObjectMapper();
			Encounter ec = new Encounter();
			try {
				ec = mapper.readValue(encounter.getData(), Encounter.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<EncounterResource> dupEncounters = encounterService.getDuplicateEncounters(encounter.getId(), ec.getEncounterCode(), ec.getStartTime(), encounter.getInteropxPatientId());

			if (dupEncounters.size() > 0) {
				encounterService.updateInteropXEncounterId(encounter.getId(),
						dupEncounters.get(0).getInteropxEncounterId(), DataProcessingStatusEnum.DEDUPLICATION, true);
			} else {
				String interopxEncounterId = "IX-E-" + CommonUtil.generateRandomString(6);
				encounterService.updateInteropXEncounterId(encounter.getId(), interopxEncounterId,
						DataProcessingStatusEnum.DEDUPLICATION, true);
			}
		}
	}
	
	public void processImmunizationDeDuplication(Integer etId) {

		List<ImmunizationResource> immunizationList = immunizationService.getImmunizationsByEtIdAndProcessingStatus(etId, DataProcessingStatusEnum.DEDUPLICATION, false);

		for (ImmunizationResource immunization : immunizationList) {

			ObjectMapper mapper = new ObjectMapper();
			Immunization im = new Immunization();
			try {
				im = mapper.readValue(immunization.getData(), Immunization.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<ImmunizationResource> dupImmunizations = immunizationService.getDuplicateImmunizations(immunization.getId(), im.getImmCode(), im.getCompletedDate(), immunization.getInteropxPatientId());

			if (dupImmunizations.size() > 0) {
				immunizationService.updateInteropXImmunizationId(immunization.getId(),
						dupImmunizations.get(0).getInteropxImmunizationId(), DataProcessingStatusEnum.DEDUPLICATION, true);
			} else {
				String interopxImmunizationId = "IX-I-" + CommonUtil.generateRandomString(6);
				immunizationService.updateInteropXImmunizationId(immunization.getId(), interopxImmunizationId,
						DataProcessingStatusEnum.DEDUPLICATION, true);
			}
		}
	}
	
	public void processLabResultDeDuplication(Integer etId) {

		List<LabResultsResource> labResultsList = labResultsService.getLabResultByEtIdAndProcessingStatus(etId, DataProcessingStatusEnum.DEDUPLICATION, false);

		for (LabResultsResource labResult : labResultsList) {
			
			ObjectMapper mapper = new ObjectMapper();
			LabResults lr = new LabResults();
			try {
				lr = mapper.readValue(labResult.getData(), LabResults.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<LabResultsResource> dupLabResults= labResultsService.getDuplicateLabResults(labResult.getId(), lr.getOriginalName(), lr.getLabType(), lr.getCollectionDate(), labResult.getInteropxPatientId());

			if (dupLabResults.size() > 0) {
				labResultsService.updateInteropXLabResultId(labResult.getId(),
						dupLabResults.get(0).getInteropxLabId(), DataProcessingStatusEnum.DEDUPLICATION, true);
			} else {
				String interopxdupLabResultsId = "IX-LR-" + CommonUtil.generateRandomString(6);
				labResultsService.updateInteropXLabResultId(labResult.getId(), interopxdupLabResultsId,
						DataProcessingStatusEnum.DEDUPLICATION, true);
			}
		}
	}
	
	public void processMedicationDeDuplication(Integer etId) {

		List<MedicationResource> medicationList = medicationService.getMedicationsByEtIdAndProcessingStatus(etId, DataProcessingStatusEnum.DEDUPLICATION, false);

		for (MedicationResource medication : medicationList) {

			ObjectMapper mapper = new ObjectMapper();
			Medication mc = new Medication();
			try {
				mc = mapper.readValue(medication.getData(), Medication.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<MedicationResource> dupMedications= medicationService.getDuplicateMedicaitons(medication.getId(), mc.getCode(), mc.getPrescriptionDate(), medication.getInteropxPatientId());

			if (dupMedications.size() > 0) {
				medicationService.updateInteropXMedicationId(medication.getId(),
						dupMedications.get(0).getInteropxMedicationId(), DataProcessingStatusEnum.DEDUPLICATION, true);
			} else {
				String interopxdupMedicationId = "IX-MC-" + CommonUtil.generateRandomString(6);
				medicationService.updateInteropXMedicationId(medication.getId(), interopxdupMedicationId,
						DataProcessingStatusEnum.DEDUPLICATION, true);
			}
		}
	}
	
	public void processProcedureDeDuplication(Integer etId) {

		List<ProcedureResource> procedureList = procedureService.getProceduresByEtIdAndProcessingStatus(etId, DataProcessingStatusEnum.DEDUPLICATION, false);

		for (ProcedureResource procedure : procedureList) {
			
			ObjectMapper mapper = new ObjectMapper();
			Procedure pr = new Procedure();
			try {
				pr = mapper.readValue(procedure.getData(), Procedure.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<ProcedureResource> dupProcedures = procedureService.getDuplicateProcedures(procedure.getId(), pr.getCcCptCode(), pr.getCcDateOfService(), procedure.getInteropxPatientId());

			if (dupProcedures.size() > 0) {
				procedureService.updateInteropXProcedureId(procedure.getId(),
						dupProcedures.get(0).getInteropxProcedureId(), DataProcessingStatusEnum.DEDUPLICATION, true);
			} else {
				String interopxdupProcedureId = "IX-PR-" + CommonUtil.generateRandomString(6);
				procedureService.updateInteropXProcedureId(procedure.getId(), interopxdupProcedureId,
						DataProcessingStatusEnum.DEDUPLICATION, true);
			}
		}
	}
	
	public void processSmokingDeDuplication(Integer etId) {

		List<SmokingResource> smokingList = smokingService.getSmokingsByEtIdAndProcessingStatus(etId, DataProcessingStatusEnum.DEDUPLICATION, false);

		for (SmokingResource smoking : smokingList) {
			
			ObjectMapper mapper = new ObjectMapper();
			Smoking sm = new Smoking();
			try {
				sm = mapper.readValue(smoking.getData(), Smoking.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<SmokingResource> dupSmoking = smokingService.getDuplicateSmokings(smoking.getId(), sm.getSmokingStatusCode(), sm.getStartTime(), smoking.getInteropxPatientId());

			if (dupSmoking.size() > 0) {
				smokingService.updateInteropXSmokingId(smoking.getId(), dupSmoking.get(0).getInteropxSmokingId(),
						DataProcessingStatusEnum.DEDUPLICATION, true);
			} else {
				String interopxdupSmokingId = "IX-SM-" + CommonUtil.generateRandomString(6);
				smokingService.updateInteropXSmokingId(smoking.getId(), interopxdupSmokingId,
						DataProcessingStatusEnum.DEDUPLICATION, true);
			}
		}
	}
	
	public void processVitalsDeDuplication(Integer etId) {

		List<VitalSigns> vitalSignsList = vitalsService.getVitalsByEtIdAndProcessingStatus(etId, DataProcessingStatusEnum.DEDUPLICATION, false);

		for (VitalSigns vitalSign : vitalSignsList) {
			
			ObjectMapper mapper = new ObjectMapper();
			Vitals vs = new Vitals();
			try {
				vs = mapper.readValue(vitalSign.getData(), Vitals.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<VitalSigns> dupVitalSigns = vitalsService.getDuplicateVitalSigns(vitalSign.getId(), vs.getVitalCode(), vs.getVitalsDate(), vitalSign.getInteropxPatientId());

			if (dupVitalSigns.size() > 0) {
				vitalsService.updateInteropXVitalId(vitalSign.getId(), dupVitalSigns.get(0).getInteropxVitalId(),
						DataProcessingStatusEnum.DEDUPLICATION, true);
			} else {
				String interopxdupVitalsId = "IX-VS-" + CommonUtil.generateRandomString(6);
				vitalsService.updateInteropXVitalId(vitalSign.getId(), interopxdupVitalsId,
						DataProcessingStatusEnum.DEDUPLICATION, true);
			}
		}
	}
	
	public void processLocationDeDuplication(Integer etId) {

		List<LocationResource> locationsList = locationService.getLocationsByEtIdAndProcessingStatus(etId, DataProcessingStatusEnum.DEDUPLICATION, false);

		for (LocationResource location : locationsList) {

			ObjectMapper mapper = new ObjectMapper();
			Location lc = new Location();
			try {
				lc = mapper.readValue(location.getData(), Location.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<LocationResource> dupLocations = locationService.getDuplicateLocations(location.getId(), lc.getLocType(), lc.getLocationCity(), lc.getLocationState(), lc.getPhone(), location.getInteropxPatientId());

			if (dupLocations.size() > 0) {
				locationService.updateInteropXLocationId(location.getId(),
						dupLocations.get(0).getInteropxLocationId(), DataProcessingStatusEnum.DEDUPLICATION, true);
			} else {
				String interopxdupLocationId = "IX-L-" + CommonUtil.generateRandomString(6);
				locationService.updateInteropXLocationId(location.getId(), interopxdupLocationId,
						DataProcessingStatusEnum.DEDUPLICATION, true);
			}
		}
	}

}
