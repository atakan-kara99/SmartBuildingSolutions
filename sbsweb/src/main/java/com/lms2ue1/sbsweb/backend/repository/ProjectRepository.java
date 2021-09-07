package com.lms2ue1.sbsweb.backend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.backend.model.Project;


public interface ProjectRepository extends CrudRepository<Project, Long>{
	/* Should order by zip codes */
	List<Project> findByRolesOrderByCompletionDateAsc(Project p);
}
