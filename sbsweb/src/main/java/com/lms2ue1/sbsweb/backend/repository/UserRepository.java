package com.lms2ue1.sbsweb.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    // Needed for the authentication.
    User findByUsername(String username);

    User findByUsernameIgnoreCase(String username);
}
