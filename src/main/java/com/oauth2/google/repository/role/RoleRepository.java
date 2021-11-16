package com.oauth2.google.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oauth2.google.model.user.Role;
import com.oauth2.google.model.user.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("SELECT role FROM Role role "
			+ "WHERE role.name = :role")
	Role findByName(@Param("role") RoleType name);
}
