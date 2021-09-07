package com.lms2ue1.sbsweb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.model.Organization;
import com.lms2ue1.sbsweb.model.User;

public interface OrganizationRepository extends CrudRepository<Organization, Long>{
}
