package com.sqa.bhyt.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivateInfoResponse {
	private String transactionId;
	private String otp;
	private int expiredMins;
	private int totpSize;
}
