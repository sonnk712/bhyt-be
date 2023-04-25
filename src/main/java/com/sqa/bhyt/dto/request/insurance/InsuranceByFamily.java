package com.sqa.bhyt.dto.request.insurance;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InsuranceByFamily {
	private String cccd;
	private String name;
	private Date birthDay;
}
