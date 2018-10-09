package com.interopx.platform.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.interopx.platform.repository.service.AllergyIntoleranceService;
import com.interopx.platform.repository.service.ConditionService;
import com.interopx.platform.repository.service.EncounterService;
import com.interopx.platform.repository.service.ImmunizationService;
import com.interopx.platform.repository.service.LabResultsService;
import com.interopx.platform.repository.service.LocationService;
import com.interopx.platform.repository.service.MedicationService;
import com.interopx.platform.repository.service.PatientService;
import com.interopx.platform.repository.service.ProcedureService;
import com.interopx.platform.repository.service.SmokingService;
import com.interopx.platform.repository.service.VitalsService;

@Controller
@RequestMapping("/duplication")
public class ResourceController {
	
	@Autowired
	private MedicationService medicationService;
	
	@Autowired
	private ConditionService conditionService;
	
	@Autowired
	private AllergyIntoleranceService allergyService;
	
	@Autowired
	private ProcedureService procedureService;
	
	@Autowired
	private ImmunizationService immunizationService;
	
	@Autowired
	private EncounterService encounterService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LabResultsService labResultsService;
	
	@Autowired
	private SmokingService smokingService;
	
	@Autowired
	private VitalsService vitalsService;
	
	@Autowired
	private PatientService patientService;
	

	@RequestMapping(value = "/{resource}", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> getPatientBySearchParams(@PathVariable("resource") String resource, @RequestParam String interopXPatientId, HttpServletResponse response) throws DataFormatException, ParseException {
		List<Object> result = new ArrayList<>();
		Map<String, Object> mapObj = new LinkedHashMap<>();
		
		switch (resource) {
		case "Medication":
			result = medicationService.getUniqueMedicationsByInteropXPatientId(interopXPatientId);
			break;
			
		case "Condition":
			result = conditionService.getUniqueConditionsByInteropXPatientId(interopXPatientId);
			break;
			
		case "AllergyIntolerance":
			result = allergyService.getUniqueAllergiesByInteropXPatientId(interopXPatientId);
			break;
			
		case "Procedure":
			result = procedureService.getUniqueProceduresByInteropXPatientId(interopXPatientId);
			break;
			
		case "Immunization":
			result = immunizationService.getUniqueImmunizationsByInteropXPatientId(interopXPatientId);
			break;
			
		case "Location":
			result = locationService.getUniqueLocationsByInteropXPatientId(interopXPatientId);
			break;
			
		case "LabResults":
			result = labResultsService.getUniqueLabResultsByInteropXPatientId(interopXPatientId);
			break;
			
		case "Smoking":
			result = smokingService.getUniqueSmokingsByInteropXPatientId(interopXPatientId);
			break;
			
		case "VitalSigns":
			result = vitalsService.getUniqueVitalSignsByInteropXPatientId(interopXPatientId);
			break;
			
		case "Encounter":
			result = encounterService.getUniqueEncountersByInteropXPatientId(interopXPatientId);
			break;
			
		case "Patient":
			result = patientService.getUniquePatientByInteropXPatientId(interopXPatientId);
			break;
			
		default:
			mapObj.put(resource, "Not Available");
			result.add(mapObj);
			break;
		}
		
		return result;
	}
}
