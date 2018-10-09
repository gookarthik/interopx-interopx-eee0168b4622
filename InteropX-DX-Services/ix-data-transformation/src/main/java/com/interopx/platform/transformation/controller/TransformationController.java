package com.interopx.platform.transformation.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.interopx.platform.repository.model.Audit;
import com.interopx.platform.repository.model.ExtractionDetails;
import com.interopx.platform.repository.service.AuditService;
import com.interopx.platform.repository.service.ExtractionService;
import com.interopx.platform.transformation.service.AllergyIntoleranceMapService;
import com.interopx.platform.transformation.service.ConditionMapService;
import com.interopx.platform.transformation.service.EncounterMapService;
import com.interopx.platform.transformation.service.ImmunizationMapService;
import com.interopx.platform.transformation.service.LabResultsMapService;
import com.interopx.platform.transformation.service.LocationMapService;
import com.interopx.platform.transformation.service.MedicationMapService;
import com.interopx.platform.transformation.service.PatientMapService;
import com.interopx.platform.transformation.service.ProcedureMapService;
import com.interopx.platform.transformation.service.SmokingMapService;
import com.interopx.platform.transformation.service.VitalsMapService;

@Controller
@RequestMapping("/transformation")
public class TransformationController {

	@Autowired
	private ExtractionService extractionService;

	@Autowired
	private PatientMapService patientService;

	@Autowired
	private AllergyIntoleranceMapService allergyService;

	@Autowired
	private ConditionMapService conditionService;

	@Autowired
	private EncounterMapService encounterService;

	@Autowired
	private ImmunizationMapService immunizationService;

	@Autowired
	private LabResultsMapService labService;

	@Autowired
	private LocationMapService locationService;

	@Autowired
	private MedicationMapService medicationService;

	@Autowired
	private ProcedureMapService procedureService;

	@Autowired
	private SmokingMapService smokingService;

	@Autowired
	private VitalsMapService vitalsService;
	
	@Autowired
	private AuditService auditService;

	@RequestMapping(value = "/{etId}", method = RequestMethod.POST)
	@ResponseBody
	public void processTransformationByExtractionId(@PathVariable Integer etId) throws IOException {

		ExtractionDetails ed = extractionService.getExtractionById(etId);
		
		//create audit event 
		Audit audit = new Audit();
		Gson gson = new Gson();
		audit.setCategory("Data Transformation");
		audit.setEvent_name("Automatic Data Transformation");
		audit.setEvent_status("Started");
		audit.setResource_name(getClass().getName());
		HashMap<String, String> data = new HashMap<>();
		data.put("etid", ""+etId);
		data.put("description", "extraction with id "+etId+" transformation processing started");
		audit.setEvent_data(gson.toJson(data));
		auditService.saveOrUpdate(audit);

		if (ed != null) {
			List<Future> futureList = new ArrayList<Future>();
			String filesLocation = ed.getFilesLocation();
			try (Stream<Path> paths = Files.walk(Paths.get(filesLocation))) {
				paths.filter(Files::isRegularFile).map(Path::toFile).forEach(file -> {

					String fileName = FilenameUtils.removeExtension(file.getName());
					try {
						switch (fileName.toLowerCase()) {

						case "patient":
							//create audit event for processing patient records
							HashMap<String, String> patientEvent = new HashMap<>();
							patientEvent.put("fileName", file.getName());
							patientEvent.put("fileLocation",file.getAbsolutePath());
							patientEvent.put("description", "Patient records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(patientEvent));
							audit.setEvent_name("Automatic - Patient Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process Patient records
							Future<Long> patientResult = patientService.processCSVFile(file, ed);
							futureList.add(patientResult);
							break;

						case "allergy":
							//create audit event for processing allergy records
							HashMap<String, String> allergyEvent = new HashMap<>();
							allergyEvent.put("fileName", file.getName());
							allergyEvent.put("fileLocation",file.getAbsolutePath());
							allergyEvent.put("description", "Allergy records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(allergyEvent));
							audit.setEvent_name("Automatic - Allergy Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process allergy records
							Future<Long> allergyResult = allergyService.processCSVFile(file, ed);
							futureList.add(allergyResult);
							break;

						case "encounter":
							//create audit event for processing encounter records
							HashMap<String, String> encounterEvent = new HashMap<>();
							encounterEvent.put("fileName", file.getName());
							encounterEvent.put("fileLocation",file.getAbsolutePath());
							encounterEvent.put("description", "Encounter records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(encounterEvent));
							audit.setEvent_name("Automatic - Encounter Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process encounter records
							Future<Long> encounterResult = encounterService.processCSVFile(file, ed);
							futureList.add(encounterResult);
							break;

						case "immunization":
							//create audit event for processing immunization records
							HashMap<String, String> immunizationEvent = new HashMap<>();
							immunizationEvent.put("fileName", file.getName());
							immunizationEvent.put("fileLocation",file.getAbsolutePath());
							immunizationEvent.put("description", "Immunization records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(immunizationEvent));
							audit.setEvent_name("Automatic - Immunization Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process immunization records
							Future<Long> immunizationResult = immunizationService.processCSVFile(file, ed);
							futureList.add(immunizationResult);
							break;

						case "labresults":
							//create audit event for processing labresult records
							HashMap<String, String> labEvent = new HashMap<>();
							labEvent.put("fileName", file.getName());
							labEvent.put("fileLocation",file.getAbsolutePath());
							labEvent.put("description", "LabResult records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(labEvent));
							audit.setEvent_name("Automatic - LabResult Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process labResult records
							Future<Long> labResult = labService.processCSVFile(file, ed);
							futureList.add(labResult);
							break;

						case "location":
							//create audit event for processing location records
							HashMap<String, String> locationEvent = new HashMap<>();
							locationEvent.put("fileName", file.getName());
							locationEvent.put("fileLocation",file.getAbsolutePath());
							locationEvent.put("description", "Location records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(locationEvent));
							audit.setEvent_name("Automatic - Location Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process location records
							Future<Long> locationResult = locationService.processCSVFile(file, ed);
							futureList.add(locationResult);
							break;

						case "medication":
							//create audit event for processing medication records
							HashMap<String, String> medicationEvent = new HashMap<>();
							medicationEvent.put("fileName", file.getName());
							medicationEvent.put("fileLocation",file.getAbsolutePath());
							medicationEvent.put("description", "Medication records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(medicationEvent));
							audit.setEvent_name("Automatic - Medication Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process medication records
							Future<Long> medicationResult = medicationService.processCSVFile(file, ed);
							futureList.add(medicationResult);
							break;

						case "problem":
							//create audit event for processing problem records
							HashMap<String, String> problemEvent = new HashMap<>();
							problemEvent.put("fileName", file.getName());
							problemEvent.put("fileLocation",file.getAbsolutePath());
							problemEvent.put("description", "Problem records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(problemEvent));
							audit.setEvent_name("Automatic - Problem Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process problem records
							Future<Long> conditionResult = conditionService.processCSVFile(file, ed);
							futureList.add(conditionResult);
							break;

						case "procedure":
							//create audit event for processing procedure records
							HashMap<String, String> procedureEvent = new HashMap<>();
							procedureEvent.put("fileName", file.getName());
							procedureEvent.put("fileLocation",file.getAbsolutePath());
							procedureEvent.put("description", "Procedure records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(procedureEvent));
							audit.setEvent_name("Automatic - Procedure Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process procedure records
							Future<Long> procedureResult = procedureService.processCSVFile(file, ed);
							futureList.add(procedureResult);
							break;

						case "smoking":
							//create audit event for processing smoking records
							HashMap<String, String> smokingEvent = new HashMap<>();
							smokingEvent.put("fileName", file.getName());
							smokingEvent.put("fileLocation",file.getAbsolutePath());
							smokingEvent.put("description", "Smoking records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(smokingEvent));
							audit.setEvent_name("Automatic - Smoking Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process smoking records
							Future<Long> smokingResult = smokingService.processCSVFile(file, ed);
							futureList.add(smokingResult);
							break;

						case "vitals":
							//create audit event for processing vitals records
							HashMap<String, String> vitalEvent = new HashMap<>();
							vitalEvent.put("fileName", file.getName());
							vitalEvent.put("fileLocation",file.getAbsolutePath());
							vitalEvent.put("description", "Vital records are processing from CSV file");
							audit.setAuditId(null);
							audit.setEvent_data(gson.toJson(vitalEvent));
							audit.setEvent_name("Automatic - Vitals Data Transformation");
							auditService.saveOrUpdate(audit);
							
							//process vitals records
							Future<Long> vitalsResult = vitalsService.processCSVFile(file, ed);
							futureList.add(vitalsResult);
							break;
						}

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

				});
				paths.close();
			}
			
			//Wait until all the async processes get done
			Boolean flag = false;
			while (!flag) 
			{
				ArrayList<Boolean> resources = new ArrayList<Boolean>();
				for (Future future : futureList) {
					boolean status = future.isDone();
					resources.add(status);
				}
				
				if (!resources.contains(false)) 
				{
					flag = true;
				}else {
					try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();} // sleep for 500 millisecond before checking again
				}
			}
			
			//create audit event on process exit
			HashMap<String, String> exitEvent = new HashMap<>();
			exitEvent.put("etId", ""+etId);
			exitEvent.put("description", "Extraction with id : "+etId+" processing completed");
			audit.setAuditId(null);
			audit.setEvent_data(gson.toJson(exitEvent));
			audit.setEvent_name("Automatic - Smoking Data Transformation");
			audit.setEvent_status("Completed");
			auditService.saveOrUpdate(audit);

		}

	}

}
