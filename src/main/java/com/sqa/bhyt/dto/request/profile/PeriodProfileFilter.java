package com.sqa.bhyt.dto.request.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodProfileFilter {
	private String textSearch;
	private Integer month;
}
