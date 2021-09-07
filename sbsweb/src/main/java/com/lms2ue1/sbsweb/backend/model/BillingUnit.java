package com.lms2ue1.sbsweb.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 * BillingUnit of each contract. Associated to one contract and one or more
 * billing items.
 * 
 * @author juliusdaum
 *
 */
@Entity
public class BillingUnit {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private long id;
	private String shortDescription;
	private String longDescription;
	private String unit;
	private String completionDate;
	private String ownContractDefined;
	private double totalQuantity;
	private double totalPrice;

	// ---- Associations ----//
	@ManyToOne
	private Contract contract;
	@Size(min = 1)
	@OneToMany(mappedBy = "billingUnit", orphanRemoval = true)
	private List<BillingItem> billingItems;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public BillingUnit() {
	}

	/**
	 * Initializes a billing unit object.
	 * 
	 * @param sDesc              = short description
	 * @param lDesc              = long description
	 * @param unit               = unit
	 * @param completionDate     = completion date
	 * @param ownContractDefined = own contract defined
	 * @param totalQuantity      = total quantity
	 * @param totalPrice         = total price
	 * @param contract           = contract
	 * @param billingItems       = billing items
	 */
	public BillingUnit(String sDesc, String lDesc, String unit, String completionDate, String ownContractDefined, double totalQuantity, double totalPrice, Contract contract,
			List<BillingItem> billingItems) {
		this.shortDescription = sDesc;
		this.longDescription = lDesc;
		this.unit = unit;
		this.completionDate = completionDate;
		this.ownContractDefined = ownContractDefined;
		this.totalQuantity = totalQuantity;
		this.totalPrice = totalPrice;
		this.contract = contract;
		this.billingItems = billingItems;
	}

	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//
	public long getId() {
		return this.id;
	}

	public String getShortDescription() {
		return this.shortDescription;
	}

	public String getLongDescription() {
		return this.longDescription;
	}

	public String getUnit() {
		return this.unit;
	}

	public String getCompletionDate() {
		return this.completionDate;
	}

	public String getownContractDefined() {
		return this.ownContractDefined;
	}

	public double getTotalQuantity() {
		return this.totalQuantity;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public Contract getContract() {
		return this.contract;
	}

	public List<BillingItem> getBillingItems() {
		return this.billingItems;
	}

	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
	public void setId(long id) {
		this.id = id;
	}

	public void setShortDescription(String sDesc) {
		this.shortDescription = sDesc;
	}

	public void setLongDescription(String lDesc) {
		this.longDescription = lDesc;
	}

	public void setUnit(String u) {
		this.unit = u;
	}

	public void setCompletionDate(String cD) {
		this.completionDate = cD;
	}

	public void setownContractDefined(String oCD) {
		this.ownContractDefined = oCD;
	}

	public void setTotalQuantity(double tQ) {
		this.totalQuantity = tQ;
	}

	public void setTotalPrice(double tP) {
		this.totalPrice = tP;
	}

	public void setContract(Contract c) {
		this.contract = c;
	}

	public void setBillingItems(List<BillingItem> bis) {
		this.billingItems = bis;
	}
}
