package com.sqa.bhyt.endpoint.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.insurance.FamilyProfileRequest;
import com.sqa.bhyt.dto.request.insurance.OrginizationProfileRequest;
import com.sqa.bhyt.dto.request.insurance.PaidBySocialInsuranceRequet;
import com.sqa.bhyt.dto.request.insurance.PaidByStateBudgetRequest;
import com.sqa.bhyt.dto.request.insurance.PaymentInfoRequest;
import com.sqa.bhyt.dto.request.insurance.PersonalProfileRequest;
import com.sqa.bhyt.dto.response.profile.ProfileReponse;
import com.sqa.bhyt.service.client.HealthInsuranceService;
@RestController
@RequestMapping("/api/v1/health-insurance")
public class HealthInsuranceResource {
	
	@Autowired 
	private HealthInsuranceService healthInsuranceService;
	
	@PostMapping("/family-register")
	public ResponseEntity<ServiceResponse<ProfileReponse>> familyRegister(@Validated @RequestBody FamilyProfileRequest request){
		ServiceResponse<ProfileReponse> result = healthInsuranceService.createFamilyProfile(request);
		
		return new ResponseEntity<ServiceResponse<ProfileReponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/personal-register")
	public ResponseEntity<ServiceResponse<ProfileReponse>> personalRegister(@Validated @RequestBody PersonalProfileRequest request){
		ServiceResponse<ProfileReponse> result = healthInsuranceService.createPersonalProfile(request);
		
		return new ResponseEntity<ServiceResponse<ProfileReponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/organization-register")
	public ResponseEntity<ServiceResponse<ProfileReponse>> orginizationRegister(@Validated @RequestBody OrginizationProfileRequest request){
		ServiceResponse<ProfileReponse> result = healthInsuranceService.createOrganizationProfile(request);
		
		return new ResponseEntity<ServiceResponse<ProfileReponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/paid-by-social-insurance")
	public ResponseEntity<ServiceResponse<ProfileReponse>> paidBySocialInsurance(@Validated @RequestBody PaidBySocialInsuranceRequet request){
		ServiceResponse<ProfileReponse> result = healthInsuranceService.createBySocialInsuranceProfile(request);
		
		return new ResponseEntity<ServiceResponse<ProfileReponse>>(result, HttpStatus.OK);
	}

	@PostMapping("/paid-by-state-budget")
	public ResponseEntity<ServiceResponse<ProfileReponse>> createByStateBudgetProfile(@Validated @RequestBody PaidByStateBudgetRequest request){
		ServiceResponse<ProfileReponse> result = healthInsuranceService.createByStateBudgetProfile(request);
		
		return new ResponseEntity<ServiceResponse<ProfileReponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/payment")
	public ResponseEntity<ServiceResponse<Boolean>> payment(@Validated @RequestBody PaymentInfoRequest request){
		ServiceResponse<Boolean> result = healthInsuranceService.payment(request);
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
	}
}
