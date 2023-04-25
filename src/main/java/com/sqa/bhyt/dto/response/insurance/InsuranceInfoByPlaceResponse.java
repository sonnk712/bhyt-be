package com.sqa.bhyt.dto.response.insurance;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceInfoByPlaceResponse {
	private String identityNumber;
	private String name;
	private Date birthDay;
	private int gender;
	private String registerPlaceCode;
	private String registerPlaceName;
	private String cardIssueCode;
	private String cardIssueName;
	private Date validFrom;
	private Date validTo;
	private int status;
}
