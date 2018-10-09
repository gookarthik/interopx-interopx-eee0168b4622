package com.interopx.platform.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.interopx.platform.repository.model.Conflicts;
import com.interopx.platform.repository.model.Groups;
import com.interopx.platform.repository.model.Pages;
import com.interopx.platform.repository.service.ConflictsService;
import com.interopx.platform.repository.service.GroupsService;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Controller
@RequestMapping("/groups")
public class PatientMatchingLinkingController {
	
	@Autowired
	private GroupsService groupService;
	
	@Autowired
	private ConflictsService conflictsService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	@ResponseBody
	public List<Groups> getAllConflictGroups(){
		
		return groupService.getAllConflictGroups();
	}
	
	@RequestMapping(value="/{ixId}",method=RequestMethod.GET)
	@ResponseBody
	public List<Groups> getConflictGroupsByInteropXId(@PathVariable String ixId){
		
		return groupService.getGroupsByInteropXPatientId(ixId);
	}
	
	@RequestMapping(value="/conflicts/{groupId}",method=RequestMethod.GET)
	@ResponseBody
	public List<Conflicts> getConflictsByInteropXPatientId(@PathVariable Integer groupId){
		
		return conflictsService.getConflictsByInteropXPatientId(groupId);
	}
	
	@RequestMapping(value="/conflicts",method=RequestMethod.GET)
	@ResponseBody
	public Pages getGroupsByPage(@RequestParam Integer currentPage, @RequestParam Integer pageSize){
		
		return groupService.getGroupsByPage(currentPage, pageSize);
	}
	
	@RequestMapping(value="/conflicts/resolve",method=RequestMethod.POST)
	@ResponseBody
	public Groups resolveConflicts(@RequestBody Groups group, HttpServletRequest request){
		
		return groupService.resolveConflicts(group);
	}
	
	@RequestMapping(value="/conflicts/exclude",method=RequestMethod.POST)
	@ResponseBody
	public Groups excludeConflicts(@RequestBody Groups group, HttpServletRequest request){
		
		return groupService.excludeConflicts(group, DataProcessingStatusEnum.PATIENTIDMATCHINGANDLINKING, false);
	}

}
