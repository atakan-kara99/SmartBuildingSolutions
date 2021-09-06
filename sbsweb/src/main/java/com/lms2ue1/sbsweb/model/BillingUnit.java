package com.lms2ue1.sbsweb.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="billing_units")
public class BillingUnit {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// TODO: set the associations
	private long id;
	private String shortDescription;
	private String longDescription;
	private String unit;
	private String completionDate;
	private String ownContractDefined;
	private double totalQuantity;
	private double totalPrice;

	// ---- Associations ----//
	private Contract contract;
	private List<BillingItem> billingItems;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public BillingUnit() {
	}

	public BillingUnit(long id, String shortDescription, String longDescription, String unit, String completionDate,
			String ownContractDefined, double totalQuantity, double totalPrice, Contract contract,
			List<BillingItem> billingItems) {
		this.id = id;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.unit = unit;
		this.completionDate = completionDate;
		this.ownContractDefined = ownContractDefined;
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

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public void setownContractDefined(String ownContractDefnied) {
		this.ownContractDefined = ownContractDefnied;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public void setBillingItems(List<BillingItem> billingItems) {
		this.billingItems = billingItems;
	}
}
