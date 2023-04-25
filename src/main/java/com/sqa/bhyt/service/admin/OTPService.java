package com.sqa.bhyt.service.admin;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.otp.OTPInfoRequest;
import com.sqa.bhyt.dto.request.otp.VerifyOTPRequest;
import com.sqa.bhyt.dto.response.otp.OTPInfoResponse;

public interface OTPService {
	
	ServiceResponse<OTPInfoResponse> generateOTP(OTPInfoRequest request);
	
	ServiceResponse<Boolean> verifyOTP(VerifyOTPRequest request);
	
//	ServiceResponse<OTPInfoResponse> generateOTPUsername(String username, String transactionId);
	
//	ServiceResponse<Boolean> clearOTP(ClearOTPRequest request);
}
