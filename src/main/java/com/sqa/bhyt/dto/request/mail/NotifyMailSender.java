package com.sqa.bhyt.dto.request.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyMailSender {
	private String id;
	private String username;
	private String email;
	private String subject;
	private String detail;
}
