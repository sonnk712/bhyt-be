package com.sqa.bhyt.endpoint.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.common.ultis.StringUtils;
import com.sqa.bhyt.dto.request.user.ActivateInfo;
import com.sqa.bhyt.dto.request.user.VerifyInfo;
import com.sqa.bhyt.dto.response.user.ActivateInfoResponse;
import com.sqa.bhyt.dto.response.user.CurrentUserDTO;
import com.sqa.bhyt.service.client.AccountService;

@RestController
@RequestMapping("/api/v1/user")
public class UserResource {
	
	@Autowired
	AccountService accountService; 
	
	@PostMapping(value = "/generate-otp", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ServiceResponse<ActivateInfoResponse>> generateOTP(@RequestBody ActivateInfo request){
		
		String transactionId = StringUtils.generateTransactionId();
		request.setTransactionId(transactionId);
		ServiceResponse<ActivateInfoResponse> result = accountService.activateAccount(request);
		
		return new ResponseEntity<ServiceResponse<ActivateInfoResponse>>(result, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/verify-otp", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ServiceResponse<Boolean>> verifyOTP(@RequestBody VerifyInfo request){
		ServiceResponse<Boolean> result = accountService.verifyOTP(request);
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/current-user")
	public ResponseEntity<ServiceResponse<CurrentUserDTO>> currentUser() {
		ServiceResponse<CurrentUserDTO> result = accountService.getCurrentUser();

	
		return new ResponseEntity<ServiceResponse<CurrentUserDTO>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/validate-email", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ServiceResponse<Boolean>> validateEmail(@RequestParam("email") String email) {
		ServiceResponse<Boolean> result = accountService.validateEmail(email);
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/validate-phone", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ServiceResponse<Boolean>> validatePhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
		ServiceResponse<Boolean> result = accountService.validatePhoneNumber(phoneNumber);
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/validate-username", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ServiceResponse<Boolean>> validateUsername(@RequestParam("username") String username) {
		ServiceResponse<Boolean> result = accountService.validateUsername(username);
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
	}
	
}
