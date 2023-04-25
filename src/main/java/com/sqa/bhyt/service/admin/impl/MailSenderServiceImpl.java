package com.sqa.bhyt.service.admin.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.sqa.bhyt.dto.request.mail.ActivateMailSender;
import com.sqa.bhyt.dto.request.mail.NotifyMailSender;
import com.sqa.bhyt.dto.request.mail.VerifyOTPMailSender;
import com.sqa.bhyt.service.admin.MailSenderService;

@Service
public class MailSenderServiceImpl implements MailSenderService{

	private static final Logger logger = LoggerFactory.getLogger(MailSenderServiceImpl.class);
	
	@Value("${username}")
	private String from;
	
	@Value("${password}")
    private String password;
	
	@Value("${host}")
    private String host;
	
	@Value("${port}")
	private String port;
	
	@Autowired
	ResourceLoader resourceLoader;
    
	@Override
	public boolean sendMailToVerifyOTP(VerifyOTPMailSender request) {
		try {		
			System.out.println(from);
			
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(host);
			mailSender.setPort(Integer.parseInt(port));
			mailSender.setUsername("sonnk712@gmail.com");
			mailSender.setPassword(password);
			
			Properties properties = new Properties();
			
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.ssl.trust", "*");
			
			mailSender.setJavaMailProperties(properties);
		

			String to = request.getReceiverEmailID(); 

            MimeMessage message = mailSender.createMimeMessage();
            
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            
            System.out.println(from);
            System.out.println(to);
            
            helper.setSubject("Bảo hiểm y tế Viêt Nam: Xác minh thông tin cá nhân của bạn!");
			helper.setFrom(from);
			helper.setTo(to);
            
			Resource resource = resourceLoader.getResource("classpath:templates/send-mail-to-verify-OTP.html");
			
			InputStream inputStream = resource.getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);
			
			String mailContent = data.replace("{{VERIFICATION_CODE}}", request.getOtp())
					.replace("{{USERNAME}}", request.getUsername())
					.replace("{{EXPIRED_MINUTES}}", Long.toString(request.getExpiredMins()));
			
			helper.setText(mailContent, true);
			mailSender.send(message);
            logger.info("Send mail generate otp success");
//	            System.out.println("Send mail generate otp success");
            return true;
//	        	logger.error(e.getMessage());
//	        	return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
			
		} catch (MessagingException  e) {
			logger.error(e.getMessage());
			return false;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean sendMailToActivateAccount(ActivateMailSender request) {
		try {	
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(host);
			mailSender.setPort(Integer.parseInt(port));
			mailSender.setUsername("sonnk712@gmail.com");
			mailSender.setPassword(password);
			
			Properties properties = new Properties();
			
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.ssl.trust", "*");
			
			mailSender.setJavaMailProperties(properties);
		

			String to = request.getGmail();   

            MimeMessage message = mailSender.createMimeMessage();
            
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setSubject("Bảo hiểm y tế Viêt Nam: Xác minh thông tin cá nhân của bạn!");
			helper.setFrom(from);
			helper.setTo(to);
			
			System.out.println(from);
			System.out.println(to);
            
			Resource resource = resourceLoader.getResource("classpath:templates/send-mail-to-activate-account.html");
			
			InputStream inputStream = resource.getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);
			
			String mailContent = data.replace("{{ USER_NAME }}", request.getUsername())
					.replace("{{ EXPIRED_MINS }}", Integer.toString(request.getExpiredMins()))
					.replace("{{ URL }}", request.getVerifyUrl())
					.replace("{{ OTP }}", request.getOtp());
			
			helper.setText(mailContent, true);
			mailSender.send(message);
            logger.info("Send mail to verify account success");
            return true;
			
		} catch (MessagingException  e) {
			logger.error(e.getMessage());
			return false;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean sendMailToNotify(NotifyMailSender request) {
		try {	
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(host);
			mailSender.setPort(Integer.parseInt(port));
			mailSender.setUsername("sonnk712@gmail.com");
			mailSender.setPassword(password);
			
			Properties properties = new Properties();
			
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.ssl.trust", "*");
			
			mailSender.setJavaMailProperties(properties);
		

			String to = request.getEmail();   

            MimeMessage message = mailSender.createMimeMessage();
            
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setSubject("Ecommece shop: Notify about" + request.getSubject() + "!");
			helper.setFrom(from);
			helper.setTo(to);
            
			Resource resource = resourceLoader.getResource("classpath:templates/send-mail-to-notify.html");
			
			InputStream inputStream = resource.getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);
			
			String mailContent = data.replace("{{ USERNAME }}", request.getUsername())
					.replace("{{ SUBJECT }}", request.getSubject())
					.replace("{{ DETAIL }}", request.getDetail());
			
			helper.setText(mailContent, true);
			mailSender.send(message);
            logger.info("Send mail to verify account success");
//	            System.out.println("Send mail generate otp success");
            
            return true;
			
		} catch (MessagingException  e) {
			logger.error(e.getMessage());
			return false;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean sendMailToRecoverPassword() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
