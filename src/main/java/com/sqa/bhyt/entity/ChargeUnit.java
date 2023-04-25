package com.sqa.bhyt.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "charge_unit")
public class ChargeUnit {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "code") 
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "cost")
	private double cost;
	
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
}
