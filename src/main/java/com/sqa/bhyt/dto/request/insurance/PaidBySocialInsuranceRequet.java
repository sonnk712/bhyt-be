package com.sqa.bhyt.dto.request.insurance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaidBySocialInsuranceRequet {
	private String cccd;
	private String periodCode;
	private String typeCode;
	//Mức bình quân tiền lương tháng đóng bảo hiểm thất nghiệp của 06 tháng liền kề trước khi thất nghiệp
	private double avgLast6MonthPaymentForUnemployeeInsuarance;
	private double avgMonthlyPaymentForSocialInsurance;
	private int numberOfYearPayingSocialInsurance;
}
