package com.sqa.bhyt.endpoint.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.service.admin.FamilyRecordService;


@RestController
@RequestMapping("/api/v1/family-record")
public class FamilyRecordResource {
	@Autowired
	private FamilyRecordService familyRecordService;
	
	@PostMapping("/create")
	public ResponseEntity<ServiceResponse<Boolean>> create(){
		ServiceResponse<Boolean> result = familyRecordService.create();
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
	}
}
