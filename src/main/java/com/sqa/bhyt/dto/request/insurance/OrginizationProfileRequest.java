package com.sqa.bhyt.dto.request.insurance;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrginizationProfileRequest {
	private List<OrginizationProfileInfo> members;
	private String typeCode;
}
