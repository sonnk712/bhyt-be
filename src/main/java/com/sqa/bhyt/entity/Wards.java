package com.sqa.bhyt.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "wards")
public class Wards {
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
	
	@Column(name = "district_code") 
	private String districtCode;
	
	@OneToMany(mappedBy = "wardIdentity")	
    private Set<Identity> identities;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="district_code", nullable=false, insertable = false, updatable = false)
    private Districts districtWard;
	
	@OneToMany(mappedBy = "wardAgency")	
    private Set<Agency> agencies;
}
