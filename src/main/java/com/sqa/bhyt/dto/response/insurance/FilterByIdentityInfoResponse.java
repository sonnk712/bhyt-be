package com.sqa.bhyt.dto.response.insurance;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilterByIdentityInfoResponse {
	private String insuranceId;
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
