package com.sqa.bhyt.service.admin;

import com.sqa.bhyt.dto.request.mail.ActivateMailSender;
import com.sqa.bhyt.dto.request.mail.NotifyMailSender;
import com.sqa.bhyt.dto.request.mail.VerifyOTPMailSender;

public interface MailSenderService {
	boolean sendMailToVerifyOTP(VerifyOTPMailSender request);
	
	boolean sendMailToActivateAccount(ActivateMailSender request);
	
	boolean sendMailToNotify(NotifyMailSender request);
	
	boolean sendMailToRecoverPassword();
}
