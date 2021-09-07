package com.lms2ue1.sbsweb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.model.BillingUnit;
import com.lms2ue1.sbsweb.model.Contract;

public interface BillingUnitRepository extends CrudRepository<BillingUnit, Long>{
	List<BillingUnit> findByContract(Contract c);
}
