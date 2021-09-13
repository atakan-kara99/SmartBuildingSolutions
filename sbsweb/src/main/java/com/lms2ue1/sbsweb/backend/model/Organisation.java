package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.List;

@Entity
public class Organisation {
<<<<<<< HEAD
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private long id;
	@NotEmpty
	@Column(unique = true)
	private String name;

	// ---- Associations ----//
	@ManyToMany
	@JoinTable(name = "organisation_status", joinColumns = { @JoinColumn(name = "organisation_id") }, inverseJoinColumns = {
            @JoinColumn(name = "status_id") })
	private List<Status> stati;
	@Size(min = 1)
	@OneToMany(mappedBy = "organisation")
	private List<Project> projects;
	@Size(min = 2)
	@OneToMany(mappedBy = "organisation")
	private List<Role> roles;
	@Size(min = 1)
	@ManyToMany(mappedBy = "organisations")
	private List<Contract> contracts;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Organisation() {
	}

	/**
	 * Constructor to insert the data of the rest api json request.
	 * Only the parameters of the constructor are columns (plus the FKs).
	 * 
	 * @param name  = name of the organisation.
	 */
	public Organisation(String name) {
		this.name = name;
	}

	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//
	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public List<Status> getStatus() {
		return this.stati;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public List<Contract> getContracts() {
		return this.contracts;
	}

	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String n) {
		this.name = n;
	}
	
	public void setStatus(List<Status> stati) {
		this.stati = stati;
	}

	public void setProjects(List<Project> p) {
		this.projects = p;
	}

	public void setRoles(List<Role> r) {
		this.roles = r;
	}

	public void setContracts(List<Contract> c) {
		this.contracts = c;
=======
    // ---- Attributes ----//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private long id;
    @NotEmpty
    @Column(unique = true)
    private String name;

    // ---- Associations ----//
    @Size(min = 1)
    @OneToMany(mappedBy = "organisation")
    private List<Project> projects;
    @Size(min = 2)
    @OneToMany(mappedBy = "organisation") // Mapping is necessary!
    private List<Role> roles;
    @Size(min = 1)
    @ManyToMany(mappedBy = "organisations")
    private List<Contract> contracts;

    // ----------------------------------//
    // ---------- Constructors ----------//
    // ----------------------------------//
    public Organisation() {
    }

    /**
     * Constructor to insert the data of the rest api json request. Only the
     * parameters of the constructor are columns (plus the FKs).
     * 
     * @param name = name of the organisation.
     */
    public Organisation(String name) {
	this.name = name;
    }

    // ----------------------------//
    // ---------- Getter ----------//
    // ----------------------------//
    public long getId() {
	return this.id;
    }

    public String getName() {
	return this.name;
    }

    public List<Project> getProjects() {
	return this.projects;
    }

    public List<Role> getRoles() {
	return this.roles;
    }

    public List<Contract> getContracts() {
	return this.contracts;
    }

    // ----------------------------//
    // ---------- Setter ----------//
    // ----------------------------//
    protected void setId(long id) {
	this.id = id;
    }

    protected void setName(String n) {
	this.name = n;
    }

    protected void setProjects(List<Project> p) {
	this.projects = p;
    }

    protected void setRoles(List<Role> r) {
	this.roles = r;
    }

    protected void setContracts(List<Contract> c) {
	this.contracts = c;
    }

    // ----------------------------//
    // ---------- Misc ------------//
    // ----------------------------//

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Organisation) {
	    Organisation tmpOrga = (Organisation) obj;
	    return tmpOrga.getId() == this.id;
>>>>>>> web-dev
	}
	return false;
    }
}
