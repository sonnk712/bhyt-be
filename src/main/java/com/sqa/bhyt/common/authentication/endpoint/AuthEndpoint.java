package com.sqa.bhyt.common.authentication.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.authentication.dto.request.LoginRequest;
import com.sqa.bhyt.common.authentication.dto.request.SignupRequest;
import com.sqa.bhyt.common.authentication.dto.response.LoginResponse;
import com.sqa.bhyt.common.authentication.dto.response.SignupResponse;
import com.sqa.bhyt.common.authentication.service.AuthenticationService;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.common.security.jwt.JwtUtils;


//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthEndpoint {

	@Autowired
	private AuthenticationService userService;

	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<ServiceResponse<LoginResponse>> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
		ServiceResponse<LoginResponse> result = userService.login(loginRequest);
		
		return new ResponseEntity<ServiceResponse<LoginResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/admin-login")
	public ResponseEntity<ServiceResponse<LoginResponse>> authenticateAdmin(@Validated @RequestBody LoginRequest loginRequest) {
		ServiceResponse<LoginResponse> result = userService.loginAdmin(loginRequest);
		
		return new ResponseEntity<ServiceResponse<LoginResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<ServiceResponse<SignupResponse>> registerUser(@RequestBody SignupRequest signUpRequest) {
		ServiceResponse<SignupResponse> result = userService.register(signUpRequest);
		
		return new ResponseEntity<ServiceResponse<SignupResponse>>(result, HttpStatus.OK);
	}

}
