package com.sqa.bhyt.dto.request.insurance;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyProfileRequest {
	private List<FamilyProfileInfo> members;
	private String typeCode;
	private String chargeUnitCode;
}
