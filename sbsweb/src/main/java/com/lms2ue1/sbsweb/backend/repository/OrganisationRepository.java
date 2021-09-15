package com.lms2ue1.sbsweb.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.Organisation;

public interface OrganisationRepository extends CrudRepository<Organisation, Long> {
    Organisation findByName(String name);
}
