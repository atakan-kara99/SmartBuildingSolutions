package com.lms2ue1.sbsweb.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.Contract;
import com.lms2ue1.sbsweb.backend.model.Role;

public interface ContractRepository extends CrudRepository<Contract, Long> {
    List<Contract> findByRolesOrderByNameAsc(Role r);

    Optional<Contract> findByNameIgnoreCase(String name);

    Optional<Contract> findByAdessoID(long adessoID);

}
