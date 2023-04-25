package com.sqa.bhyt.service.client.impl;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sqa.bhyt.common.constants.Constants;
import com.sqa.bhyt.common.constants.MessageCode;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.common.security.jwt.JwtService;
import com.sqa.bhyt.common.ultis.DateUtils;
import com.sqa.bhyt.dto.request.mail.ActivateMailSender;
import com.sqa.bhyt.dto.request.otp.OTPInfoRequest;
import com.sqa.bhyt.dto.request.otp.VerifyOTPRequest;
import com.sqa.bhyt.dto.request.place.DistrictDTO;
import com.sqa.bhyt.dto.request.place.ProvinceDTO;
import com.sqa.bhyt.dto.request.place.WardDTO;
import com.sqa.bhyt.dto.request.user.ActivateInfo;
import com.sqa.bhyt.dto.request.user.VerifyInfo;
import com.sqa.bhyt.dto.response.otp.OTPInfoResponse;
import com.sqa.bhyt.dto.response.user.ActivateInfoResponse;
import com.sqa.bhyt.dto.response.user.CurrentUserDTO;
import com.sqa.bhyt.entity.Districts;
import com.sqa.bhyt.entity.Identity;
import com.sqa.bhyt.entity.Provinces;
import com.sqa.bhyt.entity.Role;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.entity.Wards;
import com.sqa.bhyt.repository.IdentityRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.service.admin.MailSenderService;
import com.sqa.bhyt.service.admin.OTPService;
import com.sqa.bhyt.service.client.AccountService;
import java.util.regex.Matcher;

@Service
public class AccountServiceImpl implements AccountService{
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	IdentityRepository identityRepository;

	@Autowired
	MailSenderService mailSender; 
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	JwtService jwtService;
	
	@Value("${urlImportToken}")
	private String verifyUrl;
	
	@Value("${expiredMins}")
	private String expiredMins;
	
	@Override
	public ServiceResponse<String> recoverPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ServiceResponse<ActivateInfoResponse> activateAccount(ActivateInfo request) {
		try {		
			OTPInfoRequest otpInfo = new OTPInfoRequest();
			otpInfo.setUsername(request.getUsername());
			otpInfo.setTransactionId(request.getTransactionId());
			
			ServiceResponse<OTPInfoResponse> otp = otpService.generateOTP(otpInfo);
			
			if(otp.getData() != null) {
				String token = jwtService.generateSerialNumberToken(request.getTransactionId());
				
				ActivateMailSender activateInfo = new ActivateMailSender(); 
				activateInfo.setUsername(request.getUsername());
//				activateInfo.setGmail(currUser.get().getGmail());
				activateInfo.setGmail(request.getEmail());
				activateInfo.setOtp(otp.getData().getOtp());
				activateInfo.setVerifyUrl(verifyUrl + token);
				activateInfo.setExpiredMins(Integer.parseInt(expiredMins));
				boolean send =  mailSender.sendMailToActivateAccount(activateInfo);
				
				if(!send) {
					return new ServiceResponse<ActivateInfoResponse>(MessageCode.EMAIL_SENDING_FAILED, MessageCode.EMAIL_SENDING_FAILED_MESSAGE, null);
				}
				
			}
			else {
				return new ServiceResponse<ActivateInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
			}
			
			ActivateInfoResponse response = new ActivateInfoResponse();
			response.setOtp(otp.getData().getOtp());
			response.setTransactionId(otp.getData().getTransactionId());
			response.setExpiredMins(otp.getData().getExpiredMins());
			response.setTotpSize(otp.getData().getTotpSize());
			
			return new ServiceResponse<ActivateInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ActivateInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<Boolean> verifyOTP(VerifyInfo request) {
		try {
			
			int check = jwtService.validateTokenSerialNumber(request.getToken());

			String transactionId = jwtService.getSerialNumberFromToken(request.getToken());

			if(!StringUtils.hasText(transactionId)) {
				return new ServiceResponse<Boolean>(MessageCode.TOKEN_INVALID, MessageCode.TOKEN_INVALID_MESSAGE, false);
			}
			if(check == 1) {
				
				if(!StringUtils.hasText(request.getSubmittedOtp())) {
					return new ServiceResponse<Boolean>(MessageCode.OTP_NOT_EMPTY, MessageCode.OTP_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<Boolean>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(request.getBirthday())) {
					return new ServiceResponse<Boolean>(MessageCode.DATE_NOT_EMPTY, MessageCode.DATE_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(request.getCountry())) {
					return new ServiceResponse<Boolean>(MessageCode.NATIONALITY_NOT_EMPTY, MessageCode.NATIONALITY_NOT_EMPTY_MESSGAE, null);
				}
				
				
				if(!StringUtils.hasText(request.getCurrentAddress())) {
					return new ServiceResponse<Boolean>(MessageCode.ORIGIN_PLACE_NOT_EMPTY, MessageCode.ORIGIN_PLACE_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(request.getPermanentAddress())) {
					return new ServiceResponse<Boolean>(MessageCode.RESIDENCE_PLACE_NOT_EMPTY, MessageCode.RESIDENCE_PLACE_NOT_EMPTY_MESSAGE, null);
				}
				

				if(!StringUtils.hasText(request.getIssuerBy())) {
					return new ServiceResponse<Boolean>(MessageCode.ISSUER_NOT_EMPTY, MessageCode.ISSUER_NOT_EMPTY_MESSAGE, null);
				}
				

				if(!StringUtils.hasText(request.getIssuerDate())) {
					return new ServiceResponse<Boolean>(MessageCode.ISSUER_DATE_NOT_EMPTY, MessageCode.ISSUER_DATE_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(request.getPermanentAddress())) {
					return new ServiceResponse<Boolean>(MessageCode.RESIDENCE_PLACE_NOT_EMPTY, MessageCode.RESIDENCE_PLACE_NOT_EMPTY_MESSAGE, null);
				}
				
				
				Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(request.getIdentityNumber(), Constants.NOT_DELETED);
				if(!identity.isPresent()) {
					return new ServiceResponse<Boolean>(MessageCode.CCCD_IS_INVALID, MessageCode.CCCD_IS_INVALID_MESSAGE, null);
				}
				
				if(!identity.get().getName().equalsIgnoreCase(request.getName())) {
					return new ServiceResponse<Boolean>(MessageCode.INCORRECT_CREDENTIALS, MessageCode.INCORRECT_CREDENTIALS_MESSAGE, null);
				}
				
			
				if(identity.get().getGender() != request.getGender()) {
					return new ServiceResponse<Boolean>(MessageCode.INCORRECT_CREDENTIALS, MessageCode.INCORRECT_CREDENTIALS_MESSAGE, null);
				}
				
				if(!identity.get().getNationality().equalsIgnoreCase(request.getCountry())) {
					return new ServiceResponse<Boolean>(MessageCode.INCORRECT_CREDENTIALS, MessageCode.INCORRECT_CREDENTIALS_MESSAGE, null);
				}
			
				
				VerifyOTPRequest verifyOTP = new VerifyOTPRequest();
				
				verifyOTP.setTransactionId(transactionId);
				verifyOTP.setUsername(request.getIdentityNumber());
				verifyOTP.setSubmittedOtp(request.getSubmittedOtp());
				verifyOTP.setExpiredMins(null);
				
				ServiceResponse<Boolean> verify = otpService.verifyOTP(verifyOTP);
				
				Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(request.getIdentityNumber(), Constants.NOT_DELETED);
				if(!currUser.isPresent()) {
					return new ServiceResponse<Boolean>(MessageCode.INCORRECT_CREDENTIALS, MessageCode.INCORRECT_CREDENTIALS_MESSAGE, null);
				}
				
				
				if(verify.getData() == true) {
					currUser.get().setStatus(Constants.IS_ACTIVATE);
					currUser.get().setUpdatedDate(new Date());
					currUser.get().setUpdatedUser(request.getIdentityNumber());
					currUser.get().setName(request.getName());
					currUser.get().setBirthday(DateUtils.convertStringToDate(request.getBirthday()));
					currUser.get().setGender(request.getGender());
					currUser.get().setNationality(request.getCountry());
					currUser.get().setPlaceOfOrigin(request.getCurrentAddress());
					currUser.get().setPlaceOfResidence(request.getPermanentAddress());
					currUser.get().setIssuerBy(request.getIssuerBy());
					currUser.get().setIssuerDate(DateUtils.convertStringToDate(request.getIssuerDate()));
					
					userRepository.save(currUser.get());
					
					return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
				}
				else {
					return new ServiceResponse<Boolean>(verify.getCode(), verify.getMessage(), false);
				}
			}
			else {
				return new ServiceResponse<Boolean>(MessageCode.VALIDATE_TIME_HAS_EXPIRED, MessageCode.VALIDATE_TIME_HAS_EXPIRED_MESSAGE,
						false);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
		}
	}

	@Override
	public ServiceResponse<CurrentUserDTO> getCurrentUser() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			if(!currUser.isPresent()) {
				return new ServiceResponse<CurrentUserDTO>(MessageCode.PERSONAL_INFORMATION_INVALID, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			if(currUser.get().getStatus() == Constants.NOT_ACTIVATE) {
				return new ServiceResponse<CurrentUserDTO>(MessageCode.ACCOUT_NOT_ACTIVATE, 
						MessageCode.ACCOUT_NOT_ACTIVATE_MESSAGE, null);
			}
			
			Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(username, Constants.NOT_DELETED);
			System.out.println(identity.get().getName());
			
			CurrentUserDTO response = new CurrentUserDTO();
			response.setId(currUser.get().getId());
			response.setIdentityNumber(currUser.get().getIdentityUser().getCccd());
			response.setName(currUser.get().getName());
			response.setUsername(currUser.get().getUsername());
			response.setPassword(currUser.get().getPassword());
			response.setBirthday(DateUtils.dateToStringStartWithDDMMYYY(currUser.get().getBirthday()));
			response.setEmail(currUser.get().getGmail());
			response.setGender(currUser.get().getGender());
			
			response.setPhoneNumber(currUser.get().getPhoneNumber());
			Provinces province = currUser.get().getIdentityUser().getProvinceIdentity();
			Districts district = currUser.get().getIdentityUser().getDistrictIdentity();
			Wards ward = currUser.get().getIdentityUser().getWardIdentity();
			response.setProvince(new ProvinceDTO(province.getCode(), province.getCodeName(), province.getFullName()));
			response.setDistrict(new DistrictDTO(district.getCode(), district.getCodeName(), district.getFullName()));
			response.setWard(new WardDTO(ward.getCode(), ward.getCodeName(), ward.getFullName()));
			response.setCountry(currUser.get().getNationality());
			response.setPlaceOfOrigin(currUser.get().getPlaceOfOrigin());
			response.setPlaceOfResidence(currUser.get().getPlaceOfResidence());
			response.setIdentityNumber(currUser.get().getPersionalIdentification());
			response.setStatus(currUser.get().getStatus());
			Set<Role> roles = currUser.get().getRoles();
			response.setRoles(roles);
			
			return new ServiceResponse<CurrentUserDTO>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<CurrentUserDTO>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<Boolean> validateEmail(String email) {
		try {
			String emailRegex = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
			Pattern mailPattern = Pattern.compile(emailRegex);
			Matcher emailMatcher = mailPattern.matcher(email);
			if (!emailMatcher.matches()) {
				return new ServiceResponse<Boolean>(MessageCode.EMAIL_INVALID, MessageCode.EMAIL_INVALID_MESSAGE,
						false);
			}
			boolean existsByEmail = userRepository.existsByGmailAndIsDeleted(email, Constants.NOT_DELETED);
			if (existsByEmail) {
				return new ServiceResponse<Boolean>(MessageCode.EMAIL_EXISTS,
						MessageCode.EMAIL_EXISTS_MESSAGE, false);
			}
//			ServiceResponse<Boolean> existsByEmailIdp = authenticationService.existsEmail(email);
//			if (existsByEmailIdp.getData()) {
//				return new ServiceResponse<Boolean>(MessageCode.EMAIL_ALREADY_EXISTS,
//						MessageCode.EMAIL_ALREADY_EXISTS_MESSAGE, null, false);
//			}
			return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE , true);
		} catch (Exception e) {
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE , false);
		}
	}
	
	@Override
	public ServiceResponse<Boolean> validateUsername(String username) {
		try {
			boolean existsByUsername = userRepository.existsByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			if (existsByUsername) {
				return new ServiceResponse<Boolean>(MessageCode.ACCOUNT_EXISTS,
						MessageCode.ACCOUNT_EXISTS_MESSAGE, false);
			}
			String regexUsername = "^\\d{12}$";
			boolean validUsername = username.matches(regexUsername);
			if(!validUsername) {
				return new ServiceResponse<Boolean>(MessageCode.USERNAME_INVALID,
						MessageCode.USERNAME_INVALID_MESSAGE, false);
			}
			
			return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
		} catch (Exception e) {
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
		}
	}
	
	@Override
	public ServiceResponse<Boolean> validatePhoneNumber(String phoneNumber) {
		try {
			String phoneRegex = "((\\+)84[3|5|7|8|9]|0[3|5|7|8|9])+([0-9]{8})\\b";
			Pattern phoneNumberPattern = Pattern.compile(phoneRegex);
			Matcher phoneMatcher = phoneNumberPattern.matcher(phoneNumber);
			if (!phoneMatcher.matches()) {
				return new ServiceResponse<Boolean>(MessageCode.PHONE_NUMBER_INVALID,
						MessageCode.PHONE_NUMBER_INVALID_MESSAGE, false);
			}
			boolean existsByPhoneNumber = userRepository.existsByPhoneNumberAndIsDeleted(phoneNumber,
					Constants.NOT_DELETED);
			if (existsByPhoneNumber) {
				return new ServiceResponse<Boolean>(MessageCode.PHONE_NUMBER_ALREADY_EXISTS,
						MessageCode.PHONE_NUMBER_ALREADY_EXISTS_MESSAGE, false);
			}
			return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
		} catch (Exception e) {
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
		}
	}
	
}
