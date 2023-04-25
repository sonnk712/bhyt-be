package com.sqa.bhyt.endpoint.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.insurance.FilterByIdentityInfoRequest;
import com.sqa.bhyt.dto.request.insurance.FilterByIdentityRequest;
import com.sqa.bhyt.dto.response.insurance.FilterByIdentityInfoResponse;
import com.sqa.bhyt.dto.response.insurance.FilterByIdentityResponse;

import com.sqa.bhyt.service.client.ReportService;

@RestController
@RequestMapping("/api/v1/statistic")
public class ReportResource {
	
	@Autowired
	ReportService reportService; 
	
	@PostMapping("/check-valid-insurance")
	public ResponseEntity<ServiceResponse<FilterByIdentityResponse>> checkValidInsuranceByCccd(@RequestBody FilterByIdentityRequest request){
		
		ServiceResponse<FilterByIdentityResponse> result = reportService.checkValidInsuranceByCccd(request);
		
		return new ResponseEntity<ServiceResponse<FilterByIdentityResponse>>(result, HttpStatus.OK);	
	}
	
	@PostMapping("/get-insurance-identity")
	public ResponseEntity<ServiceResponse<FilterByIdentityInfoResponse>> findIdentityNumberByInfo(@RequestBody FilterByIdentityInfoRequest request){
		
		ServiceResponse<FilterByIdentityInfoResponse> result = reportService.findIdentityNumberByInfo(request);
		
		return new ResponseEntity<ServiceResponse<FilterByIdentityInfoResponse>>(result, HttpStatus.OK);	
	}	
	
}
