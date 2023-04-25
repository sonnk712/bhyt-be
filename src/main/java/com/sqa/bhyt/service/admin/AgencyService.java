package com.sqa.bhyt.service.admin;

import java.util.List;

import com.sqa.bhyt.common.dto.request.DeleteRequest;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.agency.AgencyDetail;
import com.sqa.bhyt.dto.request.agency.AgencyFilter;
import com.sqa.bhyt.dto.request.agency.AgencyInfoRequest;
import com.sqa.bhyt.dto.request.agency.AgencyUpdateRequest;
import com.sqa.bhyt.dto.response.agency.AgencyDetailResponse;
import com.sqa.bhyt.dto.response.agency.AgencyFilterResponse;
import com.sqa.bhyt.dto.response.agency.AgencyInfoResponse;

public interface AgencyService {
	
	ServiceResponse<AgencyInfoResponse> create(AgencyInfoRequest request);
	
	ServiceResponse<AgencyInfoResponse> update(AgencyUpdateRequest request);
	
	ServiceResponse<Boolean> delete(DeleteRequest request);
	
	ServiceResponse<List<AgencyFilterResponse>> filter(AgencyFilter request);
	
	ServiceResponse<Boolean> combobox(DeleteRequest request);
	
	ServiceResponse<AgencyDetailResponse> detail(AgencyDetail request);
	
	ServiceResponse<List<AgencyFilterResponse>> getAll();
}
