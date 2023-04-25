package com.sqa.bhyt.dto.request.insurance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor	
public class OrginizationProfileInfo {
	private String cccd;
	private String periodCode;
	private String workType;
	private double salary;
	private int isRepresent;
}
