package com.sqa.bhyt.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
@Table
public class User {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "username") 
	private String username;
	
	@Column(name = "password") 
	private String password;
	
	@Column(name = "name") 
	private String name;
	
	@Column(name = "gmail")
	private String gmail;
	
	@Column(name = "image")
	private String image;

	@Column(name = "phone_number")
	private String phoneNumber;
	
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
	
	@Column(name = "issuer_by")
	private String issuerBy;
	
	@Column(name = "issuer_date")
	private Date issuerDate;
	
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
	
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name=  "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "identity_id")
	private Identity identityUser;
	
	
}
