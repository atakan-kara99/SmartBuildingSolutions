package com.lms2ue1.sbsweb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lms2ue1.sbsweb.model.Project;
import com.lms2ue1.sbsweb.model.Role;

public interface ProjectRepository extends CrudRepository<Project, Long>{
}
