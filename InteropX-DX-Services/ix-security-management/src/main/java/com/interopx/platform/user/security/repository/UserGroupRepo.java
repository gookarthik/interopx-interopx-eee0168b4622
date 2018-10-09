package com.interopx.platform.user.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.user.security.entities.UserGroup;

@Transactional
@Repository
public interface UserGroupRepo extends JpaRepository<UserGroup, Long>{

}
