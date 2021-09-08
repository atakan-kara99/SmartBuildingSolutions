package com.lms2ue1.sbsweb.backend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Role;


public interface RoleRepository extends CrudRepository<Role, Long>{
	List<Role> findByOrganisationOrderByNameAsc(Organisation o);
}
