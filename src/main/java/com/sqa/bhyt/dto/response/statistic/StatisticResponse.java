package com.sqa.bhyt.dto.response.statistic;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticResponse {
	private Integer total;
	private List<StatusResponse> countStatus;
	private List<StatisticDataResponse> listData;
}
