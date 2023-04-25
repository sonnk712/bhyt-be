package com.sqa.bhyt.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "profile_info")
public class ProfileInformation implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "cccd")
	private String cccd;
	
	@Column(name = "name") 
	private String name;
	
	@Column(name = "birth_day") 
	private Date birthDay;
	
	@Column(name = "gender")
	private int gender;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "is_represent")
	private int isRepresent;
	
	@Column(name = "personal_type_code")
	private int personalTypeCode;
	
	@Column(name = "personal_type_name")
	private String personalTypeName;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="profile_id", nullable=false)
    private Profile profile;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="period_id", nullable=false)
    private Period period;
}
