package com.interopx.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.interopx.platform.repository.model.PatientResource;
import com.interopx.platform.repository.service.PatientService;

@Controller
@RequestMapping("/Patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public List<PatientResource> getAllPatientResources(){
		
		return patientService.getAllPatients();
	}

	@RequestMapping(value="/repository/", method=RequestMethod.GET)
	@ResponseBody
	public List<PatientResource> getUniquePatients(){
		
		return patientService.getUniquePatients();
	}
	
	@RequestMapping(value="/{ixPatientId}", method=RequestMethod.GET)
	@ResponseBody
	public List<PatientResource> getPatientsByInteropXId(@PathVariable String ixPatientId){
		
		return patientService.getPatientByInteropXId(ixPatientId);
	}
}
