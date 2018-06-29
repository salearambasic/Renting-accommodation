package com.project.model.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MakeReservationDTO {

	@NotNull @Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}")
	private String startDate;
	
	@NotNull @Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}")
	private String endDate;
	
	public MakeReservationDTO(String startDate, String endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public MakeReservationDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	
	
	
}
