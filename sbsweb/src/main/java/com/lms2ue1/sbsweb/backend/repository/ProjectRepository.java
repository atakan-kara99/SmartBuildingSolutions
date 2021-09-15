package com.lms2ue1.sbsweb.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findByRolesOrderByCompletionDateAsc(Project p);

    Optional<Project> findByNameIgnoreCase(String name);

    Optional<Project> findByAdessoID(long adessoID);
}
