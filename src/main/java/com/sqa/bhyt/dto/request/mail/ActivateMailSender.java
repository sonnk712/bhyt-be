package com.sqa.bhyt.dto.request.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivateMailSender {
	private String username;
	private String gmail;
	private String otp;
	private String verifyUrl;
	private int expiredMins;
}
