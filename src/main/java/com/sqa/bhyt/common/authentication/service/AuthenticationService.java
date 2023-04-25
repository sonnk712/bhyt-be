package com.sqa.bhyt.common.authentication.service;

import com.sqa.bhyt.common.authentication.dto.request.LoginRequest;
import com.sqa.bhyt.common.authentication.dto.request.SignupRequest;
import com.sqa.bhyt.common.authentication.dto.response.LoginResponse;
import com.sqa.bhyt.common.authentication.dto.response.SignupResponse;
import com.sqa.bhyt.common.dto.response.ServiceResponse;

public interface AuthenticationService{
	
	ServiceResponse<SignupResponse> register(SignupRequest signUpRequest);
	
	ServiceResponse<LoginResponse> login(LoginRequest loginRequest);
	
	ServiceResponse<LoginResponse> loginAdmin(LoginRequest loginRequest);
}
