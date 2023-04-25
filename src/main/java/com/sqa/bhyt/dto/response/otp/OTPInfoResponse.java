package com.sqa.bhyt.dto.response.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTPInfoResponse {
	private String transactionId;
	private String otp;
	private int expiredMins;
	private int totpSize;
}
