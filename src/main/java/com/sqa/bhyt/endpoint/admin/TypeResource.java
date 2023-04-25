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
import com.sqa.bhyt.dto.request.type.TypeInfoRequest;
import com.sqa.bhyt.dto.request.type.TypeUpdateRequest;
import com.sqa.bhyt.dto.response.type.TypeInfoResponse;
import com.sqa.bhyt.dto.response.type.TypeUpdateResponse;
import com.sqa.bhyt.service.admin.TypeService;

@RestController
@RequestMapping("/api/v1/type")
public class TypeResource {
	@Autowired
	private TypeService typeService;
	
	@PostMapping("/create")
	public ResponseEntity<ServiceResponse<TypeInfoResponse>> create(@Validated @RequestBody TypeInfoRequest request){
		ServiceResponse<TypeInfoResponse> result = typeService.create(request);
		
		return new ResponseEntity<ServiceResponse<TypeInfoResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<ServiceResponse<TypeUpdateResponse>> update(@Validated @RequestBody TypeUpdateRequest request){
		ServiceResponse<TypeUpdateResponse> result = typeService.update(request);
		
		return new ResponseEntity<ServiceResponse<TypeUpdateResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<ServiceResponse<Boolean>> delete(@Validated @RequestBody DeleteRequest request){
		ServiceResponse<Boolean> result = typeService.delete(request);
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/combobox")
	public ResponseEntity<ServiceResponse<List<TypeInfoResponse>>> getCombobox(){
		ServiceResponse<List<TypeInfoResponse>> result = typeService.combobox();
		
		return new ResponseEntity<ServiceResponse<List<TypeInfoResponse>>>(result, HttpStatus.OK);
	}
}
