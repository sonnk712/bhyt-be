package com.sqa.bhyt.dto.response.period;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodUpdateResponse {
	private String id;
	private String code;
	private String name;
	private int month;
	private int status;
}
