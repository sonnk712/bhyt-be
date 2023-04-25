package com.sqa.bhyt.dto.response.agency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyInfoResponse {
	private String code;
	private String name;
	private String receivingPlace;
	private String person;
	private String phoneNumber;
	private String address;
}
