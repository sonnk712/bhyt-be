package com.sqa.bhyt.dto.response.period;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodSearchResponse {
	private String id;
	private String code;
	private String name;
	private int month;
	private int status;
	private Date createdDate;
	private Date updatedDate;
}
