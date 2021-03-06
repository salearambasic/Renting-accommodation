package com.project.web_service.wrappers.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.w3._2000._09.xmldsig.SignatureType;

@XmlRootElement(name = "addPricePlan", namespace = "http://com.project/web_service/wrappers")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addPricePlan", namespace = "http://com.project/web_service/wrappers", 
propOrder = {"apartmentId", "startDate", "endDate", "price", "Signature"})

public class AddPricePlan {

	@XmlElement(name = "apartmentId")
    private String apartmentId;
	
	@XmlElement(name = "startDate")
    private String startDate;
	
	@XmlElement(name = "endDate")
    private String endDate;
	
	@XmlElement(name = "price")
    private String price;
	
	@XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
	private SignatureType Signature;

	public SignatureType getSignature() {
		return Signature;
	}

	public void setSignature(SignatureType signature) {
		Signature = signature;
	}

	public String getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}
