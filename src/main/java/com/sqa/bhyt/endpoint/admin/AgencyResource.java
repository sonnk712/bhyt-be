package com.sqa.bhyt.endpoint.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.dto.request.DeleteRequest;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.agency.AgencyDetail;
import com.sqa.bhyt.dto.request.agency.AgencyFilter;
import com.sqa.bhyt.dto.request.agency.AgencyInfoRequest;
import com.sqa.bhyt.dto.request.agency.AgencyUpdateRequest;
import com.sqa.bhyt.dto.response.agency.AgencyDetailResponse;
import com.sqa.bhyt.dto.response.agency.AgencyFilterResponse;
import com.sqa.bhyt.dto.response.agency.AgencyInfoResponse;
import com.sqa.bhyt.service.admin.AgencyService;

@RestController
@RequestMapping("/api/v1/agency")
public class AgencyResource {
	@Autowired
	private AgencyService agencyService; 
	
	@PostMapping("/create")
	public ResponseEntity<ServiceResponse<AgencyInfoResponse>> create(@Validated @RequestBody AgencyInfoRequest request){
		ServiceResponse<AgencyInfoResponse> result = agencyService.create(request);
		
		return new ResponseEntity<ServiceResponse<AgencyInfoResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<ServiceResponse<AgencyInfoResponse>> update(@Validated @RequestBody AgencyUpdateRequest request){
		ServiceResponse<AgencyInfoResponse> result = agencyService.update(request);
		
		return new ResponseEntity<ServiceResponse<AgencyInfoResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<ServiceResponse<Boolean>> delete(@Validated @RequestBody DeleteRequest request){
		ServiceResponse<Boolean> result = agencyService.delete(request);
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/filter")
	public ResponseEntity<ServiceResponse<List<AgencyFilterResponse>>> filter(@Validated @RequestBody AgencyFilter request){
		ServiceResponse<List<AgencyFilterResponse>> result = agencyService.filter(request);
		
		return new ResponseEntity<ServiceResponse<List<AgencyFilterResponse>>>(result, HttpStatus.OK);
	}
	

	@PostMapping("/detail")
	public ResponseEntity<ServiceResponse<AgencyDetailResponse>> detail(@Validated @RequestBody AgencyDetail request){
		ServiceResponse<AgencyDetailResponse> result = agencyService.detail(request);
		
		return new ResponseEntity<ServiceResponse<AgencyDetailResponse>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ServiceResponse<List<AgencyFilterResponse>>> getAll(){
		ServiceResponse<List<AgencyFilterResponse>> result = agencyService.getAll();
		
		return new ResponseEntity<ServiceResponse<List<AgencyFilterResponse>>>(result, HttpStatus.OK);
	}
}
