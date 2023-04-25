package com.sqa.bhyt.dto.request.insurance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceInfoByPlace {
	private String districtCodeName;
	private String wardCodeName;
}
