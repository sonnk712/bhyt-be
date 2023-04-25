package com.sqa.bhyt.dto.request.insurance;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterByIdentityInfoRequest {
	private String provinceCode;
	private String districtCode;
	private String wardCode;
	private String cccd;
	private String name;
	private Date birthDay;
}
