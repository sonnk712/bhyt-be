package com.sqa.bhyt.dto.request.agency;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyFilter {
	private String textSearch;
	private String districtCode;
	private String wardCode;
}
