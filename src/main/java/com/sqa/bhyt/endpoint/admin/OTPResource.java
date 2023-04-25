package com.sqa.bhyt.endpoint.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.common.ultis.StringUtils;
import com.sqa.bhyt.dto.request.otp.OTPInfoRequest;
import com.sqa.bhyt.dto.request.otp.VerifyOTPRequest;
import com.sqa.bhyt.dto.response.otp.OTPInfoResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/otp")
public class OTPResource {
	
	@Autowired
	private com.sqa.bhyt.service.admin.OTPService OTPService;
	
	@PostMapping("/generate-otp")
	public ResponseEntity<ServiceResponse<OTPInfoResponse>> generateOTP(@Validated @RequestBody OTPInfoRequest request) {
		
		String transactionId = StringUtils.generateTransactionId();
		request.setTransactionId(transactionId);
		
		ServiceResponse<OTPInfoResponse> result = OTPService.generateOTP(request);
		
		return new ResponseEntity<ServiceResponse<OTPInfoResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/verify-otp")
	
	public ResponseEntity<ServiceResponse<Boolean>> verifyOTP(@Validated @RequestBody VerifyOTPRequest request) {
		ServiceResponse<Boolean> result = OTPService.verifyOTP(request);
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
	}
//	
//	@PostMapping("/clear-otp")
//	public ResponseEntity<ServiceResponse<Boolean>> clearOTP(@Validated @RequestBody ClearOTPRequest request) {
//		ServiceResponse<Boolean> result = OTPService.clearOTP(request);
//		
//		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
//	}

}
