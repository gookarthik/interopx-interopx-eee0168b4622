package com.interopx.platform.user.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interopx.platform.user.security.entities.Group;
import com.interopx.platform.user.security.entities.GroupPermission;
import com.interopx.platform.user.security.entities.Permission;
import com.interopx.platform.user.security.model.GroupDto;
import com.interopx.platform.user.security.model.PermissionDto;
import com.interopx.platform.user.security.repository.GroupRepositoy;
import com.interopx.platform.user.security.repository.PermissionRepository;

@Service
public class GroupDetailsServcie {

	@Autowired
	private GroupRepositoy groupRepo;
	
	@Autowired
	private PermissionRepository permissionRepo;

	public Group createGroup(GroupDto group) {

		Group groupEntity = new Group();

		// Converting groupDto to Group
		groupEntity = new Group();
		groupEntity.setGroupId(group.getGroupId());
		groupEntity.setGroupName(group.getGroupName());
		groupEntity.setGroupDesc(group.getGroupDesc());
		groupEntity.setGroupEnabled(true);
		
		GroupPermission groupPermission;
		Permission permissionEntity;
		
		if (group.getPermissions() != null) {

			for (PermissionDto permission : group.getPermissions()) {
				
				permissionEntity = new Permission();
				permissionEntity.setPermissionId(permission.getPermissionId());
				permissionEntity.setAuthority(permission.getPermissionName());
				
				groupPermission = new GroupPermission();
				groupPermission.setPermission(permissionEntity);
				groupPermission.setGroup(groupEntity);
				
				groupEntity.getGroupPermissions().add(groupPermission);

			}
		}

		return groupRepo.save(groupEntity);
	}
	
	public Group addPermissionsToGroup(Long groupId, List<PermissionDto> permissions) {
		Group groupDetails = groupRepo.findByGroupId(groupId);
		if (groupDetails != null) {
			
			GroupPermission groupPermission;
			Permission permissionEntity;

			for (PermissionDto permission : permissions) {

				// Converting groupDto to Group
				permissionEntity = new Permission();
				permissionEntity.setPermissionId(permission.getPermissionId());
				permissionEntity.setAuthority(permission.getPermissionName());

				groupPermission = new GroupPermission();
				groupPermission.setPermission(permissionEntity);
				groupPermission.setGroup(groupDetails);
				groupDetails.getGroupPermissions().add(groupPermission);
			}

			groupDetails = groupRepo.save(groupDetails);
		}
		return groupDetails;
	}

	public List<Group> retrieveAllGroups() {

		return groupRepo.findByGroupEnabled(true);
	}
	
	public List<Permission> retrieveAllPermissions() {

		return permissionRepo.findAll();
	}

	public void deleteGroup(Long groupId) {

		groupRepo.deactivateGroup(false, groupId);

	}
}
