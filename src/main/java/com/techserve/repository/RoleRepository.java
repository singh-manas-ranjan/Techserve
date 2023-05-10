package com.techserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techserve.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
