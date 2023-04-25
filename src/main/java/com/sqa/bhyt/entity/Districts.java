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
@Table(name = "districts")
public class Districts {
	
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
	
	@Column(name = "province_code") 
	private String provinceCode;
	
	@Column(name = "region") 
	private int region;
	
	@OneToMany(mappedBy = "districtIdentity")	
    private Set<Identity> identities;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="province_code", nullable=false, insertable = false, updatable = false)
    private Provinces provinceDistrict;
	
	@JsonIgnore
	@OneToMany(mappedBy = "districtWard")	
    private Set<Wards> wards;
	
	@OneToMany(mappedBy = "districtAgency")	
    private Set<Agency> agencies;
}
