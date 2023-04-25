package com.sqa.bhyt.dto.request.agency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyUpdateRequest {
	private String id;
	private String code;
	private String name;
	private String receivingPlace;
	private String person;
	private String phoneNumber;
	private String address;
	private String districtCode;
	private String wardCode;
}
