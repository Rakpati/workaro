package com.workaro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Industry")
public class Industry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="industryId", nullable = false, unique = true)
	private Long industryId;

	@Column(name="industryType", nullable = false)
	private String industryType;
	
	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public Long getIndustryId() {
		return industryId;
	}

	public void setIndustryId(Long industryId) {
		this.industryId = industryId;
	}

	
}
