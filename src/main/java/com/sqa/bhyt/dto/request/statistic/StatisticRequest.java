package com.sqa.bhyt.dto.request.statistic;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticRequest {
	private String searchText;
	private List<Integer> status;
	private String startDate;
	private String endDate;
}	
