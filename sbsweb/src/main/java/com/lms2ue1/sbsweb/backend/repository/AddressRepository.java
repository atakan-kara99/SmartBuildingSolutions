package com.lms2ue1.sbsweb.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.Address;
import com.lms2ue1.sbsweb.backend.model.Project;


public interface AddressRepository extends CrudRepository<Address, Long>{
	Address findByProjectOrderByZipCodeAsc(Project p);
}
