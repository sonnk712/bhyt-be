package com.sqa.bhyt.endpoint.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.dto.request.mail.ActivateMailSender;
import com.sqa.bhyt.dto.request.mail.NotifyMailSender;
import com.sqa.bhyt.dto.request.mail.VerifyOTPMailSender;
import com.sqa.bhyt.service.admin.MailSenderService;

@RestController
@RequestMapping("/api/v1/mail-sender")
public class MailResource {
	
	@Autowired
	private MailSenderService mailSenderService; 

	@PostMapping("/verify-otp")
	public ResponseEntity<Boolean> verifyOTP(@Validated @RequestBody VerifyOTPMailSender request) {
		Boolean result = mailSenderService.sendMailToVerifyOTP(request);
		
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PostMapping("/activate-account")
	public ResponseEntity<Boolean> activateAccount(@Validated @RequestBody ActivateMailSender request) {
		Boolean result = mailSenderService.sendMailToActivateAccount(request);
		
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PostMapping("/notify")
	public ResponseEntity<Boolean> notify(@Validated @RequestBody NotifyMailSender request) {
		Boolean result = mailSenderService.sendMailToNotify(request);
		
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
}
