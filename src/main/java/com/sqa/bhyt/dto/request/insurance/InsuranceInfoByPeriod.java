package com.sqa.bhyt.dto.request.insurance;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceInfoByPeriod {
	private Date paidFrom;
	private Date paidTo;
}
