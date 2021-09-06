package com.lms2ue1.sbsweb.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

public class BillingItem {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long roleId;
	@NotEmpty
	private String name;

	// ---- Associations ----//
	// private List<Projects> projects;
	// private List<Organization> organizations;
	// private List<User> users;
	// private List<Contract> contracts;
	// private List<BillingItem> billingItems;

	public Role() {
		}

	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//

	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
}
