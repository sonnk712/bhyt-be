package com.sqa.bhyt.endpoint.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.profile.ProfileDetail;
import com.sqa.bhyt.dto.request.profile.TypeProfileFilter;
import com.sqa.bhyt.dto.request.profile.PeriodProfileFilter;
import com.sqa.bhyt.dto.response.profile.ProfileDetailResponse;
import com.sqa.bhyt.dto.response.profile.ProfileInfoResponse;
import com.sqa.bhyt.service.admin.ProfileService;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileResource {
	@Autowired
	private ProfileService profileService;
	
	@PostMapping("/filter-by-period")
	public ResponseEntity<ServiceResponse<ProfileInfoResponse>> filterByPeriod(@Validated @RequestBody PeriodProfileFilter request){
		ServiceResponse<ProfileInfoResponse> result = profileService.filterByPeriod(request);
		
		return new ResponseEntity<ServiceResponse<ProfileInfoResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/filter-by-type")
	public ResponseEntity<ServiceResponse<ProfileInfoResponse>> filterByType(@Validated @RequestBody TypeProfileFilter request){
		ServiceResponse<ProfileInfoResponse> result = profileService.filterByType(request);
		
		return new ResponseEntity<ServiceResponse<ProfileInfoResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/filter-by-status")
	public ResponseEntity<ServiceResponse<ProfileInfoResponse>> filterByStatus(@Validated @RequestBody TypeProfileFilter request){
		ServiceResponse<ProfileInfoResponse> result = profileService.filterByStatus(request);
		
		return new ResponseEntity<ServiceResponse<ProfileInfoResponse>>(result, HttpStatus.OK);
	}

	@PostMapping("/detail")
	public ResponseEntity<ServiceResponse<ProfileDetailResponse>> detail(@Validated @RequestBody ProfileDetail request){
		ServiceResponse<ProfileDetailResponse> result = profileService.detail(request);
		
		return new ResponseEntity<ServiceResponse<ProfileDetailResponse>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ServiceResponse<ProfileInfoResponse>> getAll(){
		ServiceResponse<ProfileInfoResponse> result = profileService.getAll();
		
		return new ResponseEntity<ServiceResponse<ProfileInfoResponse>>(result, HttpStatus.OK);
	}
}
