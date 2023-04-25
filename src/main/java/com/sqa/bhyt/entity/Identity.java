package com.sqa.bhyt.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "identity")
public class Identity {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "cccd") 
	private String cccd;
	
	@Column(name = "name") 
	private String name;

	@Column(name = "birthday")
	private Date birthday;
	
	@Column(name = "gender")
	private int gender;
	
	@Column(name = "nationality")
	private String nationality;
	
	@Column(name = "place_of_origin")
	private String placeOfOrigin;
	
	@Column(name = "place_of_residence")
	private String placeOfResidence;
	
	@Column(name = "persional_identification")
	private String persionalIdentification;
	
	@Column(name = "is_house_hold")
	private boolean isHouseHold;
	
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
	
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="family_record_id", nullable=false)
    private FamilyRecord family;

	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="district_code", nullable=false)
    private Districts districtIdentity;

	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="province_code", nullable=false)
    private Provinces provinceIdentity;
	
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="ward_code", nullable=false)
    private Wards wardIdentity;
	
	@OneToOne(mappedBy = "identity")
	private HealthInsurance healthInsurance;
	
	@OneToOne(mappedBy = "identityUser")
	private User user;
}
