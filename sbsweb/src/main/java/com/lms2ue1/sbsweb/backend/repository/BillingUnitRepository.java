package com.lms2ue1.sbsweb.backend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.BillingUnit;
import com.lms2ue1.sbsweb.backend.model.Contract;

public interface BillingUnitRepository extends CrudRepository<BillingUnit, Long> {
    List<BillingUnit> findByContract(Contract c);

    BillingUnit findByAdessoID(String adessoID);
}
