/*package com.oauth2.google.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oauth2.google.model.user.Role;
import com.oauth2.google.service.role.RoleService;

@Component
@Transactional
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;
	
	@Autowired
	private RoleService roleService;

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		// Create initial roles
		createRoleIfNotFound(Role.ROLE_ADMIN);
	    createRoleIfNotFound(Role.ROLE_CLIENT);
	    createRoleIfNotFound(Role.ROLE_SELLER);
		alreadySetup = true;
	}

	private Role createRoleIfNotFound(String name) {
		Role role = roleService.findByName(name);
		if (role == null)
			return roleService.save(new Role(name));
	
		return role;
	}
}*/