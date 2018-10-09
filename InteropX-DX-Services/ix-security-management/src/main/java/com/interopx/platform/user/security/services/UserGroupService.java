package com.interopx.platform.user.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interopx.platform.user.security.entities.Group;
import com.interopx.platform.user.security.entities.User;
import com.interopx.platform.user.security.entities.UserGroup;
import com.interopx.platform.user.security.model.UserDto;
import com.interopx.platform.user.security.repository.UserGroupRepo;

@Service
public class UserGroupService {

	@Autowired
	UserGroupRepo userGroupRepo;
	
	public List<UserGroup> addGroupToUsers(Long groupId, List<UserDto> users) {
		
		List<UserGroup> userGroups = new ArrayList<>(); 
		UserGroup userGroup ;
		for (UserDto user : users) {
			userGroup = new UserGroup();
			userGroup.setUser(new User(user.getUserId()));
			userGroup.setGroup(new Group(groupId));
			userGroups.add(userGroup);
		}
		
		return userGroupRepo.saveAll(userGroups);
		
	}

}
