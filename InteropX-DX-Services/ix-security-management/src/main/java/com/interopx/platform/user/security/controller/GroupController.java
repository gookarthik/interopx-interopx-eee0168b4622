package com.interopx.platform.user.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.interopx.platform.user.security.entities.Group;
import com.interopx.platform.user.security.entities.Permission;
import com.interopx.platform.user.security.model.GroupDto;
import com.interopx.platform.user.security.model.PermissionDto;
import com.interopx.platform.user.security.services.GroupDetailsServcie;
import com.interopx.platform.user.security.util.HashException;

@RestController
public class GroupController {

	@Autowired
	GroupDetailsServcie groupDetailService;

	@RequestMapping(value = "/admin/api/groups", method = RequestMethod.POST)
	public ResponseEntity<Group> createGroup(@RequestBody GroupDto groupDetails) throws HashException {

		Group newGroup = null;
		if (groupDetails == null) {
			return new ResponseEntity<Group>(newGroup, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		newGroup = groupDetailService.createGroup(groupDetails);

		return new ResponseEntity<Group>(newGroup, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/admin/api/groups", method = RequestMethod.GET)
	public ResponseEntity<List<Group>> retrieveAllGroups(){
		
		return new ResponseEntity<List<Group>>(groupDetailService.retrieveAllGroups(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/api/groups/{groupId}/deactivate", method = RequestMethod.PUT)
	public ResponseEntity<String> deleteGroup(@PathVariable Long groupId){
		
		if (groupId == null) {
			return new ResponseEntity<String>("Invalid Group id", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		groupDetailService.deleteGroup(groupId);
		return new ResponseEntity<String>("Group Deactivated", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/api/groups/{groupId}/addpermissions", method = RequestMethod.PATCH)
	public ResponseEntity<String> addGroupsToUser(@PathVariable Long groupId, @RequestBody List<PermissionDto> permissions){
		Group groupDetails = groupDetailService.addPermissionsToGroup(groupId, permissions);
		if(groupDetails == null) {
			return new ResponseEntity<String>("Group Not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Permissions(s) added", HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/admin/api/permissions", method = RequestMethod.GET)
	public ResponseEntity<List<Permission>> retrieveAllPermissions(){
		
		return new ResponseEntity<List<Permission>>(groupDetailService.retrieveAllPermissions(), HttpStatus.OK);
	}

}
