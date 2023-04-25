package com.sqa.bhyt.entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agency")
public class Agency {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "code") 
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "receiving_place")
	private String receivingPlace;
	
	@Column(name = "person")
	private String person;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "address")
	private String address;
	
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
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="ward_code")
    private Wards wardAgency;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="district_code")
    private Districts districtAgency;
}
