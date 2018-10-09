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

import com.interopx.platform.user.security.entities.User;
import com.interopx.platform.user.security.model.GroupDto;
import com.interopx.platform.user.security.model.UserDto;
import com.interopx.platform.user.security.services.UserDetailsService;
import com.interopx.platform.user.security.services.UserGroupService;
import com.interopx.platform.user.security.util.HashException;

@RestController
public class UserController {

	@Autowired
	UserDetailsService userDetailService;
	
	@Autowired
	UserGroupService userGroupService;
	
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public ResponseEntity<String> authenticateUser(@RequestBody UserDto userDetails) throws HashException {
		return new ResponseEntity<String>("User Created", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/admin/api/users", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody UserDto userDetails) throws HashException {
		if (userDetails == null) {
			return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		userDetailService.createUser(userDetails);

		return new ResponseEntity<String>("User Created", HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/admin/api/users/{userId}/deactivate", method = RequestMethod.PUT)
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws HashException {
		if (userId == null) {
			return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		userDetailService.deactivateUser(userId);

		return new ResponseEntity<String>("User Deactivated", HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/api/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> retrieveAllUsers() {

		return new ResponseEntity<List<User>>(userDetailService.retrieveAllUsers(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/api/users/{userId}/addgroups", method = RequestMethod.PATCH)
	public ResponseEntity<String> addGroupsToUser(@PathVariable Long userId, @RequestBody List<GroupDto> groups){
		User userDetails = userDetailService.addGroupsToUser(userId, groups);
		if(userDetails == null) {
			return new ResponseEntity<String>("User Not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Group(s) added", HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/admin/api/groups/{groupId}/addUsers", method = RequestMethod.PATCH)
	public ResponseEntity<String> addGroupToUsers(@PathVariable Long groupId, @RequestBody List<UserDto> users){
		userGroupService.addGroupToUsers(groupId, users);
		return new ResponseEntity<String>("Group added to users", HttpStatus.CREATED);
	}

}
