package com.interopx.platform.datadeduplication.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.interopx.platform.datadeduplication.service.DataDeDuplicationService;
import com.interopx.platform.repository.model.Audit;
import com.interopx.platform.repository.service.AuditService;

@Controller
@RequestMapping("/data-deduplication")
public class DataDeDuplicationServiceController {
	
	@Autowired
	private DataDeDuplicationService deduplicationService;
	
	@Autowired
	private AuditService auditService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> checkDataQuality(@RequestParam("etId") Integer extractionId,
			HttpServletRequest request) {
		
		//create audit event 
		Audit audit = new Audit();
		Gson gson = new Gson();
		audit.setCategory("Data DeDuplication");
		audit.setEvent_name("Automatic Data DeDuplication");
		audit.setEvent_status("Started");
		audit.setResource_name(getClass().getName());
		HashMap<String, String> data = new HashMap<>();
		data.put("etid", ""+extractionId);
		data.put("description", "extraction with id "+extractionId+" data deduplication process started");
		audit.setEvent_data(gson.toJson(data));
		auditService.saveOrUpdate(audit);
		
		//process deduplication
		deduplicationService.processDeDuplication(extractionId);
		
		HashMap<String, String> exitdata = new HashMap<>();
		exitdata.put("etid", ""+extractionId);
		exitdata.put("description", "extraction with id "+extractionId+" data deduplication process completed");
		audit.setEvent_status("Completed");
		audit.setAuditId(null);
		auditService.saveOrUpdate(audit);
		
		return new ResponseEntity<String>("Process Completed...!", HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getDataQualityStatus(@RequestParam("etId") Integer extractionId,
			HttpServletRequest request) {
		/*DataProcessingStatus status = dataProcessingStatusService.getDataProcessingStatus(extractionId);
		System.out.println(status.getDataQuality());*/
		return new ResponseEntity<String>("Status: ", HttpStatus.OK);
	}

}
