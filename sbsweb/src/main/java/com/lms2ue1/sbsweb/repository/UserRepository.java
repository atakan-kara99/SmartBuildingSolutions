package com.lms2ue1.sbsweb.repository;

import com.lms2ue1.sbsweb.model.Organization;
import com.lms2ue1.sbsweb.model.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
