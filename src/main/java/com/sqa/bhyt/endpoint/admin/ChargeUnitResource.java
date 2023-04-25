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
import com.sqa.bhyt.dto.request.chargeunit.ChargeInfoRequest;
import com.sqa.bhyt.dto.request.chargeunit.ChargeUnitDetail;
import com.sqa.bhyt.dto.request.chargeunit.ChargeUnitTextSearch;
import com.sqa.bhyt.dto.request.chargeunit.ChargeUpdateRequest;
import com.sqa.bhyt.dto.response.chargeunit.ChargeInfoResponse;
import com.sqa.bhyt.dto.response.chargeunit.ChargeUnitDetailResponse;
import com.sqa.bhyt.dto.response.chargeunit.ChargeUnitInfoResponse;
import com.sqa.bhyt.dto.response.chargeunit.ChargeUnitSearchResponse;
import com.sqa.bhyt.dto.response.chargeunit.ChargeUpdateResponse;
import com.sqa.bhyt.service.admin.ChargeUnitService;

@RestController
@RequestMapping("/api/v1/charge-unit")
public class ChargeUnitResource {
	@Autowired
	private ChargeUnitService chargeUnitService;
	
	@PostMapping("/create")
	public ResponseEntity<ServiceResponse<ChargeInfoResponse>> create(@Validated @RequestBody ChargeInfoRequest request){
		ServiceResponse<ChargeInfoResponse> result = chargeUnitService.create(request);
		
		return new ResponseEntity<ServiceResponse<ChargeInfoResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<ServiceResponse<ChargeUpdateResponse>> update(@Validated @RequestBody ChargeUpdateRequest request){
		ServiceResponse<ChargeUpdateResponse> result = chargeUnitService.update(request);
		
		return new ResponseEntity<ServiceResponse<ChargeUpdateResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<ServiceResponse<Boolean>> delete(@Validated @RequestBody DeleteRequest request){
		ServiceResponse<Boolean> result = chargeUnitService.delete(request);
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/combobox")
	public ResponseEntity<ServiceResponse<List<ChargeUnitInfoResponse>>> getCombobox(){
		ServiceResponse<List<ChargeUnitInfoResponse>> result = chargeUnitService.combobox();
		
		return new ResponseEntity<ServiceResponse<List<ChargeUnitInfoResponse>>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/filter")
	public ResponseEntity<ServiceResponse<List<ChargeUnitSearchResponse>>> filter(@Validated @RequestBody ChargeUnitTextSearch request){
		ServiceResponse<List<ChargeUnitSearchResponse>> result = chargeUnitService.filter(request);
		
		return new ResponseEntity<ServiceResponse<List<ChargeUnitSearchResponse>>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/detail")
	public ResponseEntity<ServiceResponse<ChargeUnitDetailResponse>> detail(@Validated @RequestBody ChargeUnitDetail request){
		ServiceResponse<ChargeUnitDetailResponse> result = chargeUnitService.getDetail(request);
		
		return new ResponseEntity<ServiceResponse<ChargeUnitDetailResponse>>(result, HttpStatus.OK);
	}
}
