package com.sqa.bhyt.dto.response.chargeunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeUnitInfoResponse {
	private String id;
	private String code;
	private String name;
	private double cost;
}
