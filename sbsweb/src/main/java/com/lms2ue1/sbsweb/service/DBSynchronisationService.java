package com.lms2ue1.sbsweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Project;
import com.lms2ue1.sbsweb.backend.model.Status;
import com.lms2ue1.sbsweb.backend.repository.OrganisationRepository;
import com.lms2ue1.sbsweb.backend.repository.ProjectRepository;
import com.lms2ue1.sbsweb.backend.repository.StatusRepository;

@Service
public class DBSynchronisationService {

    // ---------- Repositories ---------//
    @Autowired
    private OrganisationRepository orgaRepo;
    @Autowired
    private ProjectRepository proRepo;
    @Autowired
    private StatusRepository statRepo;

    // ---------- Methods ------------//
    
    public void saveProjectList(List<Project> projectlist) {
	for (Project currentProject : projectlist) {
	    // Get every organisation.
	    Organisation tmpOrga = new Organisation(currentProject.getOwnerGroupIdentifier());
	    orgaRepo.save(tmpOrga);
	    currentProject.setOrganisation(tmpOrga);
	    
	    // Get every status.
	    Status tmpStat = new Status(currentProject.getAdessoStatus(), null);
	    statRepo.save(tmpStat);
	    currentProject.setRealStatus(tmpStat);
	}
	
	proRepo.saveAll(projectlist);

    }
}
