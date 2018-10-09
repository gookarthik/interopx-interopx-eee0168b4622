package com.interopx.platform.user.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.user.security.entities.Group;

@Transactional
@Repository
public interface GroupRepositoy extends JpaRepository<Group, Long> { 
	
	Group findByGroupName(String username);
	
	
	Group findByGroupId(Long id);
	
	List<Group> findByGroupEnabled(Boolean accountEnabled);
	
	@Modifying
	@Query("update Group gd set gd.groupEnabled = ?1 where gd.groupId = ?2")
	int deactivateGroup(Boolean value, Long groupId);
}
