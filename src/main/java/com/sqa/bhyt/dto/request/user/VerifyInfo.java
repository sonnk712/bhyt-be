package com.sqa.bhyt.dto.request.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyInfo {
	private String token;
	private String submittedOtp;
	private String id;
	private String identityNumber;
	private String name;
	private String birthday;
	private int gender;
	private String issuerBy;
	private String issuerDate;
	private String currentAddress;
	private String permanentAddress;
	private String provinceCode;
	private String districtCode;
	private String wardCode;
	private String country;
}
