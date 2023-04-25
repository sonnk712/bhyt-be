package com.sqa.bhyt.service.client;

import java.util.List;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.insurance.FilterByIdentityInfoRequest;
import com.sqa.bhyt.dto.request.insurance.FilterByIdentityRequest;
import com.sqa.bhyt.dto.request.insurance.InsuranceByFamily;
import com.sqa.bhyt.dto.response.insurance.FilterByIdentityInfoResponse;
import com.sqa.bhyt.dto.response.insurance.FilterByIdentityResponse;
import com.sqa.bhyt.dto.response.insurance.InsuranceByFamilyResponse;

public interface ReportService {
	ServiceResponse<FilterByIdentityResponse> checkValidInsuranceByCccd(FilterByIdentityRequest request);
	
	ServiceResponse<FilterByIdentityInfoResponse> findIdentityNumberByInfo(FilterByIdentityInfoRequest request);
	
	ServiceResponse<List<InsuranceByFamilyResponse>> findInsuranceByFamily(InsuranceByFamily request);
	
	
}
