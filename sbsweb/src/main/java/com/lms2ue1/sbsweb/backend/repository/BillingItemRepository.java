package com.lms2ue1.sbsweb.backend.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.BillingItem;
import com.lms2ue1.sbsweb.backend.model.Role;



public interface BillingItemRepository extends CrudRepository<BillingItem, Long>{
	List<BillingItem> findByRoles(Role r);
}
