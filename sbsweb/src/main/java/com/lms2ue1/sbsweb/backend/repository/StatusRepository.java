package com.lms2ue1.sbsweb.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.Status;

public interface StatusRepository extends CrudRepository<Status, Long>{
	public Status findByName(String statusName);
	public Status findById(long statusID);
}
