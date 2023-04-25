package com.sqa.bhyt.dto.request.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOTPMailSender {
	private String receiverEmailID;
	private String username;
	private String otp;
	private String transactionId;
	private Long expiredMins;
}
