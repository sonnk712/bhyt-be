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
import com.sqa.bhyt.dto.request.period.PeriodDetail;
import com.sqa.bhyt.dto.request.period.PeriodInfoRequest;
import com.sqa.bhyt.dto.request.period.PeriodTextSearch;
import com.sqa.bhyt.dto.request.period.PeriodUpdateRequest;
import com.sqa.bhyt.dto.response.period.PeriodDetailResponse;
import com.sqa.bhyt.dto.response.period.PeriodInfoResponse;
import com.sqa.bhyt.dto.response.period.PeriodSearchResponse;
import com.sqa.bhyt.dto.response.period.PeriodUpdateResponse;
import com.sqa.bhyt.service.admin.PeriodService;

@RestController
@RequestMapping("/api/v1/period")
public class PeriodResource {
	@Autowired
	private PeriodService seriodService;
	
	@PostMapping("/create")
	public ResponseEntity<ServiceResponse<PeriodInfoResponse>> create(@Validated @RequestBody PeriodInfoRequest request){
		ServiceResponse<PeriodInfoResponse> result = seriodService.create(request);
		
		return new ResponseEntity<ServiceResponse<PeriodInfoResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<ServiceResponse<PeriodUpdateResponse>> update(@Validated @RequestBody PeriodUpdateRequest request){
		ServiceResponse<PeriodUpdateResponse> result = seriodService.update(request);
		
		return new ResponseEntity<ServiceResponse<PeriodUpdateResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<ServiceResponse<Boolean>> delete(@Validated @RequestBody DeleteRequest request){
		ServiceResponse<Boolean> result = seriodService.delete(request);
		
		return new ResponseEntity<ServiceResponse<Boolean>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/combobox")
	public ResponseEntity<ServiceResponse<List<PeriodInfoResponse>>> getCombobox(){
		ServiceResponse<List<PeriodInfoResponse>> result = seriodService.combobox();
		
		return new ResponseEntity<ServiceResponse<List<PeriodInfoResponse>>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/filter")
	public ResponseEntity<ServiceResponse<List<PeriodSearchResponse>>> filter(@Validated @RequestBody PeriodTextSearch request){
		ServiceResponse<List<PeriodSearchResponse>> result = seriodService.filter(request);
		
		return new ResponseEntity<ServiceResponse<List<PeriodSearchResponse>>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/detail")
	public ResponseEntity<ServiceResponse<PeriodDetailResponse>> detail(@Validated @RequestBody PeriodDetail request){
		ServiceResponse<PeriodDetailResponse> result = seriodService.getDetail(request);
		
		return new ResponseEntity<ServiceResponse<PeriodDetailResponse>>(result, HttpStatus.OK);
	}
}
