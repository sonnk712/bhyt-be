package com.sqa.bhyt.dto.request.chargeunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeUpdateRequest {
	private String id;
	private String code;
	private String name;
	private double cost;
	private int status;
}
