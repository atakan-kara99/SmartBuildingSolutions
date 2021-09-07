package com.lms2ue1.sbsweb.backend.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findByOrganisationsOrderByUsernameAsc(Organisation o);
}
