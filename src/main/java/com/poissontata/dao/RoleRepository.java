package com.poissontata.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poissontata.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
