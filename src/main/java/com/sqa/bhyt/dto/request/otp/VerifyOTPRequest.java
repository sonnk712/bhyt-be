package com.sqa.bhyt.dto.request.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOTPRequest {
	private String submittedOtp;
	private String username;
	private String transactionId;
	private Long expiredMins;
}
