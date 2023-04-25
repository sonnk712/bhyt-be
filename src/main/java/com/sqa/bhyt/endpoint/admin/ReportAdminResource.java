package com.sqa.bhyt.endpoint.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.insurance.InsuranceInfoByPeriod;
import com.sqa.bhyt.dto.request.insurance.InsuranceInfoByPlace;
import com.sqa.bhyt.dto.request.statistic.RevenueStatistic;
import com.sqa.bhyt.dto.request.statistic.StatisticRequest;
import com.sqa.bhyt.dto.response.insurance.InsuranceInfoByPlaceResponse;
import com.sqa.bhyt.dto.response.statistic.StatisticResponse;
import com.sqa.bhyt.service.admin.ReportAdminService;

@RestController
@RequestMapping("/api/v1/report-admin")
public class ReportAdminResource {
	@Autowired
	ReportAdminService reportAdminService; 
	
	@PostMapping("/not-paid")
	public ResponseEntity<ServiceResponse<List<InsuranceInfoByPlaceResponse>>> notPaid(@RequestBody InsuranceInfoByPlace request){
		
		ServiceResponse<List<InsuranceInfoByPlaceResponse>> result = reportAdminService.notPaid(request);
		
		return new ResponseEntity<ServiceResponse<List<InsuranceInfoByPlaceResponse>>>(result, HttpStatus.OK);	
	}
	
	@PostMapping("/is-paid")
	public ResponseEntity<ServiceResponse<List<InsuranceInfoByPlaceResponse>>> isPaid(@RequestBody InsuranceInfoByPlace request){
		
		ServiceResponse<List<InsuranceInfoByPlaceResponse>> result = reportAdminService.isPaid(request);
		
		return new ResponseEntity<ServiceResponse<List<InsuranceInfoByPlaceResponse>>>(result, HttpStatus.OK);	
	}
	
	@PostMapping("/info-by-period")
	public ResponseEntity<ServiceResponse<List<InsuranceInfoByPlaceResponse>>> getInfoByPeriod(@RequestBody InsuranceInfoByPeriod request){
		
		ServiceResponse<List<InsuranceInfoByPlaceResponse>> result = reportAdminService.getInfoByPeriod(request);
		
		return new ResponseEntity<ServiceResponse<List<InsuranceInfoByPlaceResponse>>>(result, HttpStatus.OK);	
	}
	
	@PostMapping("/")
	public ResponseEntity<ServiceResponse<StatisticResponse>> statistic(@RequestBody StatisticRequest request, Pageable page){
		
		ServiceResponse<StatisticResponse> result = reportAdminService.statistic(request, page);
		
		return new ResponseEntity<ServiceResponse<StatisticResponse>>(result, HttpStatus.OK);	
	}
	
	@PostMapping("/revenue-statistics")
	public ResponseEntity<ServiceResponse<StatisticResponse>> revenueStatistic(@RequestBody RevenueStatistic request, Pageable page){
		
		ServiceResponse<StatisticResponse> result = reportAdminService.revenueStatistic(request, page);
		
		return new ResponseEntity<ServiceResponse<StatisticResponse>>(result, HttpStatus.OK);	
	}
	
}
