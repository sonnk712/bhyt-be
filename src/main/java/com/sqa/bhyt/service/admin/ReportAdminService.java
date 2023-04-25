package com.sqa.bhyt.service.admin;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.insurance.FilterByIdentityRequest;
import com.sqa.bhyt.dto.request.insurance.InsuranceInfoByPeriod;
import com.sqa.bhyt.dto.request.insurance.InsuranceInfoByPlace;
import com.sqa.bhyt.dto.request.statistic.RevenueStatistic;
import com.sqa.bhyt.dto.request.statistic.StatisticRequest;
import com.sqa.bhyt.dto.response.insurance.FilterByIdentityResponse;
import com.sqa.bhyt.dto.response.insurance.InsuranceInfoByPlaceResponse;
import com.sqa.bhyt.dto.response.statistic.StatisticResponse;

public interface ReportAdminService {
	ServiceResponse<List<InsuranceInfoByPlaceResponse>> notPaid(InsuranceInfoByPlace request);
	
	ServiceResponse<List<InsuranceInfoByPlaceResponse>> isPaid(InsuranceInfoByPlace request);
	
	ServiceResponse<FilterByIdentityResponse> revenueByPeriod(FilterByIdentityRequest request);
	
	ServiceResponse<FilterByIdentityResponse> revenueByPlace(FilterByIdentityRequest request);
	
	ServiceResponse<List<InsuranceInfoByPlaceResponse>> getInfoByPeriod(InsuranceInfoByPeriod request);
	
	ServiceResponse<StatisticResponse> statistic(StatisticRequest request, Pageable page);
	
	ServiceResponse<StatisticResponse> revenueStatistic(RevenueStatistic request, Pageable page);
	
}
