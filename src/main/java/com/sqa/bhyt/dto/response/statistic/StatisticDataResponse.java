package com.sqa.bhyt.dto.response.statistic;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDataResponse {
	private String identityId;
	private String name;
	private String issuerName;
	private Integer status;
	private Date validFrom;
	private Date validTo;
	private Date updatedDate;
}
