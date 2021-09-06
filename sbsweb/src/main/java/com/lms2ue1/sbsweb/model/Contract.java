package com.lms2ue1.sbsweb.model;

import java.util.List;

import javax.persistence.Entity;

/**
 * Contract of each organization. Each contract is associated to one or more
 * billing units, one project, two or more roles and two organizations.
 * 
 * @author juliusdaum
 *
 */
@Entity
public class Contract {
	//---- Attributes ----//
	private long id;
	private String name;
	private String description;
	private long projectId;
	private String consignee; // Empfänger -> auftragnehemr
	private String contractor; // auftragnehmer
	
	//---- Associations ----//
	private List<Organization> organizatinos;
	private List<Role> roles;
	private Project project;
	private List<BillingUnit> billingUnits;
	
	
}
