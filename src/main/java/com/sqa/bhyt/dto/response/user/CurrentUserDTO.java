package com.sqa.bhyt.dto.response.user;

import java.util.Set;


import com.sqa.bhyt.dto.request.place.DistrictDTO;
import com.sqa.bhyt.dto.request.place.ProvinceDTO;
import com.sqa.bhyt.dto.request.place.WardDTO;
import com.sqa.bhyt.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserDTO {
	private String id;
	
	private String identityNumber;

	private String username;
	
	private String password;

	private String name;

	private String email;

	private String phoneNumber;
	
	private String country;

	private Integer gender;
	
	private Integer status;

	private String birthday;

	private String placeOfOrigin;
	
	private String placeOfResidence;
	
	private String persionalIdentification;
	
	private DistrictDTO district;

	private ProvinceDTO province;
	
	private WardDTO ward;


	private Set<Role> roles;

}
