package com.sqa.bhyt.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "provinces")
public class Provinces {
	
	@Id
	@Column(name = "code") 
	private String code;
	
	@Column(name = "name") 
	private String name;
	
	@Column(name = "name_en") 
	private String nameEn;
	
	@Column(name = "full_name") 
	private String fullName;
	
	@Column(name = "full_name_en") 
	private String fullNameEn;
	
	@Column(name = "code_name") 
	private String codeName;
	
	@OneToMany(mappedBy = "provinceIdentity")	
    private Set<Identity> identities;
	
	@JsonIgnore
	@OneToMany(mappedBy = "provinceDistrict")	
    private Set<Districts> districts;
}
