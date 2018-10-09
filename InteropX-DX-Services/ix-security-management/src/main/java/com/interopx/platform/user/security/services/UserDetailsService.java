package com.interopx.platform.user.security.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.interopx.platform.user.security.entities.Group;
import com.interopx.platform.user.security.entities.User;
import com.interopx.platform.user.security.entities.UserGroup;
import com.interopx.platform.user.security.model.GroupDto;
import com.interopx.platform.user.security.model.UserDto;
import com.interopx.platform.user.security.repository.UserRepository;
import com.interopx.platform.user.security.util.HashException;
import com.interopx.platform.user.security.util.SaltedPasswordHashUtil;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
	
	private final BCryptPasswordEncoder encryptor = new BCryptPasswordEncoder();

	@Override
	public final User loadUserByUsername(String username)throws UsernameNotFoundException {
		final User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		detailsChecker.check(user);
		return user;

	}

	@Transactional
	public void createUser(UserDto user) throws HashException {
		User userEntity = new User();
		userEntity.setEmail(user.getEmail());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setPassword(encryptor.encode(user.getPassword()));
		userEntity.setUsername(user.getUserName());
		userEntity.setAccountExpired(false);
		userEntity.setAccountLocked(false);
		userEntity.setAccountEnabled(true);
		userEntity.setCredentialsExpired(false);

		Group groupEntity;
		UserGroup userGroup;

		if (user.getGroups() != null && user.getGroups().size() > 0) {
			for (GroupDto group : user.getGroups()) {

				// Converting groupDto to Group
				groupEntity = new Group();
				groupEntity.setGroupId(group.getGroupId());
				groupEntity.setGroupName(group.getGroupName());

				userGroup = new UserGroup();
				userGroup.setUser(userEntity);
				userGroup.setGroup(groupEntity);
				userEntity.getUserGroups().add(userGroup);
			}
		}

		userRepo.save(userEntity);

	}

	public List<User> retrieveAllUsers() {
		return userRepo.findByAccountEnabled(true);
	}

	public void deactivateUser(Long userId) {
		userRepo.deactivateUser(false, userId);
	}

	public User addGroupsToUser(Long userId, List<GroupDto> groups) {
		User userDetails = userRepo.findByuserId(userId);
		if (userDetails != null) {
			Group groupEntity;
			UserGroup userGroup;

			for (GroupDto group : groups) {

				// Converting groupDto to Group
				groupEntity = new Group();
				groupEntity.setGroupId(group.getGroupId());
				groupEntity.setGroupName(group.getGroupName());

				userGroup = new UserGroup();
				userGroup.setUser(userDetails);
				userGroup.setGroup(groupEntity);
				userDetails.getUserGroups().add(userGroup);
			}

			userDetails = userRepo.save(userDetails);
		}
		return userDetails;
	}

	public void updateUserPassword(UserDto user) throws HashException {
		User userEntity = userRepo.findByUsername(user.getUserName());
		userEntity.setPassword(SaltedPasswordHashUtil.getSecurePassword(user.getPassword(), 32));
		userRepo.save(userEntity);
	}

	public UserDto authenticateUser(UserDto user) throws HashException {
		User userEntity = userRepo.findByUsername(user.getUserName());
		boolean authenticated = SaltedPasswordHashUtil.validatePassword(user.getPassword(), userEntity.getPassword());
		user.setUserAuthenticated(authenticated);
		user.setPassword(null);
		if (authenticated) {
			user.setFirstName(userEntity.getFirstName());
			user.setLastName(userEntity.getLastName());
		}
		return user;

	}
}
