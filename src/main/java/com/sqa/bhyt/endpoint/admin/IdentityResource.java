package com.sqa.bhyt.endpoint.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.identity.IdentityByFamilyRequest;
import com.sqa.bhyt.dto.request.identity.IdentityInfoRequest;
import com.sqa.bhyt.dto.response.identity.IdentityInfoResponse;
import com.sqa.bhyt.service.admin.IdentityService;

@RestController
@RequestMapping("/api/v1/identity")
public class IdentityResource {
	@Autowired
	private IdentityService identityService; 
	
	@PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ServiceResponse<IdentityInfoResponse>> create(@Validated @RequestBody IdentityInfoRequest request){
		ServiceResponse<IdentityInfoResponse> result = identityService.create(request);
		
		return new ResponseEntity<ServiceResponse<IdentityInfoResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/family-create", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ServiceResponse<List<IdentityInfoResponse>>> familyCreate(@Validated @RequestBody IdentityByFamilyRequest request){
		ServiceResponse<List<IdentityInfoResponse>> result = identityService.familyCreate(request);
		
		return new ResponseEntity<ServiceResponse<List<IdentityInfoResponse>>>(result, HttpStatus.OK);
	}
	
}
