package com.sqa.bhyt.dto.request.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPInfoRequest {
	private String username;
	private String transactionId;
}
