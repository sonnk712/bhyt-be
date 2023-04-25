package com.sqa.bhyt.common.authentication.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sqa.bhyt.common.authentication.dto.request.LoginRequest;
import com.sqa.bhyt.common.authentication.dto.request.SignupRequest;
import com.sqa.bhyt.common.authentication.dto.response.LoginResponse;
import com.sqa.bhyt.common.authentication.dto.response.SignupResponse;
import com.sqa.bhyt.common.authentication.service.AuthenticationService;
import com.sqa.bhyt.common.constants.Constants;
import com.sqa.bhyt.common.constants.MessageCode;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.common.security.jwt.JwtUtils;
import com.sqa.bhyt.common.security.service.UserDetailsImpl;
import com.sqa.bhyt.common.ultis.StringUtils;
import com.sqa.bhyt.dto.request.user.ActivateInfo;
import com.sqa.bhyt.dto.response.user.ActivateInfoResponse;
import com.sqa.bhyt.entity.Identity;
import com.sqa.bhyt.entity.Role;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.repository.IdentityRepository;
import com.sqa.bhyt.repository.RoleRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.service.client.AccountService;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Autowired
	private UserRepository
	userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private IdentityRepository identityRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AccountService accountService;
	
	@Value("${bezkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	@Value("${bezkoder.app.jwtrefreshTokenExpirationMs}")
	private int jwtRefreshExpirationMs;

	@Transactional
	@Override
	public ServiceResponse<SignupResponse> register(SignupRequest signUpRequest) {
		try {
			
			if(!org.springframework.util.StringUtils.hasText(signUpRequest.getEmail())) {
				return new ServiceResponse<SignupResponse>(MessageCode.EMAIL_NOT_EMPTY,
						MessageCode.EMAIL_NOT_EMPTY_MESSAGE, null);
			}
			
			boolean validEmail = EmailValidator.getInstance().isValid(signUpRequest.getEmail());
			
			if(!validEmail) {
				return new ServiceResponse<SignupResponse>(MessageCode.EMAIL_INVALID,
						MessageCode.EMAIL_INVALID_MESSAGE, null);
			}
			
			if(!org.springframework.util.StringUtils.hasText(signUpRequest.getPhoneNumber())) {
				return new ServiceResponse<SignupResponse>(MessageCode.PHONENUMBER_NOT_EMPTY,
						MessageCode.PHONENUMBER_NOT_EMPTY_MESSAGE, null);
			}
			
			
			String regPhoneumber = "^(0|\\+84)(\\s|\\.)?(([1-9][2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
			boolean validPhoneNumeber = signUpRequest.getPhoneNumber().matches(regPhoneumber);
			
			if(!validPhoneNumeber) {
				return new ServiceResponse<SignupResponse>(MessageCode.PHONE_NUMBER_INVALID,
						MessageCode.PHONE_NUMBER_INVALID_MESSAGE, null);
			}
			
			if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
				return new ServiceResponse<SignupResponse>(MessageCode.PASSWORD_NOT_MATCH,
						MessageCode.PASSWORD_NOT_MATCH_MESSAGE, null);
			}
				
			if (!org.springframework.util.StringUtils.hasText(signUpRequest.getUsername().trim())) {
				return new ServiceResponse<SignupResponse>(MessageCode.USERNAME_NOT_EMPTY,
						MessageCode.USERNAME_NOT_EMPTY_MESSAGE, null);
			}
			
			String regexUsername = "^\\d{12}$";
			boolean validUsername = signUpRequest.getUsername().matches(regexUsername);
			if(!validUsername) {
				return new ServiceResponse<SignupResponse>(MessageCode.USERNAME_INVALID,
						MessageCode.USERNAME_INVALID_MESSAGE, null);
			}

			if (!org.springframework.util.StringUtils.hasText(signUpRequest.getPassword().trim())) {
				return new ServiceResponse<SignupResponse>(MessageCode.PASSWORD_NOT_EMPTY,
						MessageCode.PASSWORD_NOT_EMPTY_MESSAGE, null);
			}
			if(signUpRequest.getPassword().length() < 8) {
				return new ServiceResponse<SignupResponse>(MessageCode.PASSWORD_AT_LEAST_8_CHARACTERS,
						MessageCode.PASSWORD_AT_LEAST_8_CHARACTERS_MESSAGE, null);
			}
			
			String regexPassword = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
			boolean validPassword = signUpRequest.getPassword().matches(regexPassword);
			if(!validPassword) {
				return new ServiceResponse<SignupResponse>(MessageCode.PASSWORD_INVALID,
						MessageCode.PASSWORD_INVALID_MEESGAE, null);
			}

			if (userRepository.existsByUsernameAndIsDeleted(signUpRequest.getUsername(), Constants.NOT_DELETED)) {
				return new ServiceResponse<SignupResponse>(MessageCode.ACCOUNT_EXISTS,
						MessageCode.ACCOUNT_EXISTS_MESSAGE, null);
			}
			
			if(!identityRepository.existsByCccdAndIsDeleted(signUpRequest.getUsername(), Constants.NOT_DELETED)) {
				return new ServiceResponse<SignupResponse>(MessageCode.CCCD_IS_INVALID,
						MessageCode.CCCD_IS_INVALID_MESSAGE, null);
			}

			if (userRepository.existsByGmailAndIsDeleted(signUpRequest.getEmail(), Constants.NOT_DELETED)) {
				return new ServiceResponse<SignupResponse>(MessageCode.EMAIL_EXISTS, MessageCode.EMAIL_EXISTS_MESSAGE,
						null);
			}

	
			Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(signUpRequest.getUsername(), Constants.NOT_DELETED);
			if(!identity.isPresent()) {
				return new ServiceResponse<SignupResponse>(MessageCode.PERSONAL_INFORMATION_INVALID, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			// Create new user's account

			User user = new User();
			user.setId(StringUtils.generateTransactionId());
			user.setUsername(signUpRequest.getUsername());
			user.setPassword(encoder.encode(signUpRequest.getPassword()));
			user.setGmail(signUpRequest.getEmail());
			user.setPhoneNumber(signUpRequest.getPhoneNumber());
			user.setCreatedDate(new Date());
			user.setCreatedUser(signUpRequest.getUsername());
			user.setStatus(Constants.NOT_ACTIVATE);
			user.setIsDeleted(Constants.NOT_DELETED);
			
			user.setIdentityUser(identity.get());
			
			Set<Role> roles = new HashSet<>();
			Optional<Role> userRole = roleRepository.findByCode(Constants.ROLE_USER);
			if(!userRole.isPresent()) {
				return new ServiceResponse<SignupResponse>(MessageCode.ROLE_NOT_FOUND, MessageCode.ROLE_NOT_FOUND_MESSAGE, null);
			}
			roles.add(userRole.get());
			user.setRoles(roles);

			
			String transactionId = StringUtils.generateRandomNumber();
			ActivateInfo activateInfo = new ActivateInfo();
			activateInfo.setUsername(signUpRequest.getUsername());
			activateInfo.setTransactionId(transactionId);
			activateInfo.setEmail(signUpRequest.getEmail());
			ServiceResponse<ActivateInfoResponse> activateAccount = accountService.activateAccount(activateInfo);
			
			if(activateAccount.getData() == null) {
				return new ServiceResponse<SignupResponse>(activateAccount.getCode(), activateAccount.getMessage(),
						null);
			}
			
			userRepository.save(user);
			SignupResponse signupResponse = new SignupResponse();
			signupResponse.setUsername(user.getUsername());
			signupResponse.setGmail(user.getGmail());
			signupResponse.setPhoneNumber(user.getUsername());
			
			return new ServiceResponse<SignupResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE,
					signupResponse);

		} catch (Exception e) {
			log.error(e.getMessage());
			return new ServiceResponse<SignupResponse>(MessageCode.FAILED,
					MessageCode.FAILED_MESSAGE + ": " + e.getMessage(), null);
		}
	}

	@Override
	public ServiceResponse<LoginResponse> login(LoginRequest loginRequest) {

		try {

			if (!org.springframework.util.StringUtils.hasText(loginRequest.getUsername().trim())) {
				return new ServiceResponse<LoginResponse>(MessageCode.USERNAME_NOT_EMPTY,
						MessageCode.USERNAME_NOT_EMPTY_MESSAGE, null);
			}
			
//			String regex = "^\\d{12}$";
//			boolean usernameValid = loginRequest.getUsername().matches(regex);
//			if(!usernameValid) {
//				return new ServiceResponse<LoginResponse>(MessageCode.USERNAME_INVALID,
//						MessageCode.USERNAME_INVALID_MESSAGE, null);
//			}
			if (!org.springframework.util.StringUtils.hasText(loginRequest.getPassword().trim())) {
				return new ServiceResponse<LoginResponse>(MessageCode.PASSWORD_NOT_EMPTY,
						MessageCode.PASSWORD_NOT_EMPTY_MESSAGE, null);
			}

			Optional<User> user = userRepository.findByUsernameAndIsDeleted(loginRequest.getUsername(),
					Constants.NOT_DELETED);
			if (!user.isPresent()) {
				return new ServiceResponse<LoginResponse>(MessageCode.LOGIN_FAILED,
						MessageCode.LOGIN_FAILED_MESSAGE, null);
			}
			
			
			if(!encoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
				return new ServiceResponse<LoginResponse>(MessageCode.LOGIN_FAILED,
						MessageCode.LOGIN_FAILED_MESSAGE, null);
			}

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String token = jwtUtils.generateTokenFromUsername(authentication);
			String refreshToken = jwtUtils.generateTokenFromUsername(authentication);
			LoginResponse authenResponse = new LoginResponse();

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			authenResponse.setUsername(userDetails.getUsername());
			authenResponse.setGmail(userDetails.getGmail());
			authenResponse.setAccess_token(token);
			authenResponse.setRefresh_token(refreshToken);
			authenResponse.setExpires_in(jwtExpirationMs / 1000);
			authenResponse.setRefresh_expires_in(jwtRefreshExpirationMs / 1000);

			return new ServiceResponse<LoginResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, authenResponse);

		} catch (Exception e) {
			log.error(e.getMessage());
			return new ServiceResponse<LoginResponse>(MessageCode.FAILED,
					MessageCode.FAILED_MESSAGE + ": " + e.getMessage(), null);
		}
	}
	
	@Override
	public ServiceResponse<LoginResponse> loginAdmin(LoginRequest loginRequest) {

		try {

			if (!org.springframework.util.StringUtils.hasText(loginRequest.getUsername().trim())) {
				return new ServiceResponse<LoginResponse>(MessageCode.USERNAME_NOT_EMPTY,
						MessageCode.USERNAME_NOT_EMPTY_MESSAGE, null);
			}

			if (!org.springframework.util.StringUtils.hasText(loginRequest.getPassword().trim())) {
				return new ServiceResponse<LoginResponse>(MessageCode.PASSWORD_NOT_EMPTY,
						MessageCode.PASSWORD_NOT_EMPTY_MESSAGE, null);
			}

			Optional<User> user = userRepository.findByUsernameAndIsDeleted(loginRequest.getUsername(),
					Constants.NOT_DELETED);
			if (!user.isPresent()) {
				return new ServiceResponse<LoginResponse>(MessageCode.ACCOUNT_NOT_EXISTS,
						MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			
			if(!encoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
				return new ServiceResponse<LoginResponse>(MessageCode.LOGIN_FAILED,
						MessageCode.LOGIN_FAILED_MESSAGE, null);
			}

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String token = jwtUtils.generateTokenFromUsername(authentication);
			String refreshToken = jwtUtils.generateTokenFromUsername(authentication);
			LoginResponse authenResponse = new LoginResponse();

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			authenResponse.setUsername(userDetails.getUsername());
			authenResponse.setGmail(userDetails.getGmail());
			authenResponse.setAccess_token(token);
			authenResponse.setRefresh_token(refreshToken);
			authenResponse.setExpires_in(jwtExpirationMs / 1000);
			authenResponse.setRefresh_expires_in(jwtRefreshExpirationMs / 1000);

			return new ServiceResponse<LoginResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, authenResponse);

		} catch (Exception e) {
			log.error(e.getMessage());
			return new ServiceResponse<LoginResponse>(MessageCode.FAILED,
					MessageCode.FAILED_MESSAGE + ": " + e.getMessage(), null);
		}
	}

}
