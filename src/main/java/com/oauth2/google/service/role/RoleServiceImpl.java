package com.oauth2.google.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oauth2.google.model.user.Role;
import com.oauth2.google.repository.role.RoleRepository;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	private RoleRepository roleRepository;
	
	@Autowired
	RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}
	
	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}
}
