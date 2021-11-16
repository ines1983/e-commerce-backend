package com.oauth2.google.service.role;

import com.oauth2.google.model.user.Role;
import com.oauth2.google.model.user.RoleType;

public interface RoleService {
	
	/**
	 * Find by name
	 * 
	 * @param name
	 * @return {@link Role}
	 */
	Role findByName(RoleType name);
	
	/**
	 * Save role
	 * 
	 * @param role
	 * @return
	 */
	Role save(Role role);

}
