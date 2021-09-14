package com.lms2ue1.sbsweb.backend.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.Contract;
import com.lms2ue1.sbsweb.backend.model.Role;


public interface ContractRepository extends CrudRepository<Contract, Long>{
	List<Contract> findByRolesOrderByNameAsc(Role r);
	
	Contract findByName(String name);
}
