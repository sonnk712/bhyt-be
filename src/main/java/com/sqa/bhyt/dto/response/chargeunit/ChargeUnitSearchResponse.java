package com.sqa.bhyt.dto.response.chargeunit;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeUnitSearchResponse {
	private String id;
	private String code;
	private String name;
	private double cost;
	private int status;
	private Date createdDate;
	private Date updatedDate;
}
