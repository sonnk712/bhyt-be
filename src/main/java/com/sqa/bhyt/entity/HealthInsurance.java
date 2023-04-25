package com.sqa.bhyt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "health_insurance")
public class HealthInsurance implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "identity_number")
	private String identityNumber;
	
	@Column(name = "name") 
	private String name;

	@Column(name = "birthday")
	private Date birthday;
	
	@Column(name = "gender")
	private int gender;
	
	@Column(name = "register_place_code")
	private String registerPlaceCode;
	
	@Column(name = "register_place_name")
	private String registerPlaceName;

	@Column(name = "valid_time_from")
	private Date validTimeFrom;

	@Column(name = "valid_time_to")
	private Date validTimeTo;
	
	@Column(name = "card_issuer_code")
	private String cardIssuerCode;
	
	@Column(name = "card_issuer_name")
	private String cardIssuerName;

	@Column(name = "status")
	private int status;
	
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_user")
	private String createdUser;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_user")
	private String updatedUser;

	@Column(name = "is_deleted")
	private int isDeleted;
	
	@Column(name = "number_of_period")
	private int numberOfPeriod;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "identity_id")
	private Identity identity;
}
