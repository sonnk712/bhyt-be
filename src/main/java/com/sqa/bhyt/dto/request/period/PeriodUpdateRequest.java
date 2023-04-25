package com.sqa.bhyt.dto.request.period;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodUpdateRequest {
	private String id;
	private String code;
	private String name;
	private int month;
	private int status;
}
