package com.sqa.bhyt.dto.response.insurance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceInfoResponse {
	private String identityNumber;
	private String cccd;
	private String name;
	private String cardIssuerCode;
	private String cardIssuerName;
	private String registerPlaceCode;
	private String registerPlaceName;
	private int status;
}
