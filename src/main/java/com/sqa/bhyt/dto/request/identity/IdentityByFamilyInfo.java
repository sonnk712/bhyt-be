package com.sqa.bhyt.dto.request.identity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityByFamilyInfo {
	private String CCCD;
	private String name;
	private Date birthDay;
	private int gender;
	private String nationality;
	private String placeOfOrigin;
	private String placeOfResidence;
	private String persionalIdentification;
	private Boolean isHousehold;
	
}
