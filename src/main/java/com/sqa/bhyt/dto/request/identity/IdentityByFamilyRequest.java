package com.sqa.bhyt.dto.request.identity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityByFamilyRequest {
	private List<IdentityByFamilyInfo> members;
	private String familyId;
	private String districtCode;
	private String provinceCode;
	private String wardsCode;
}
