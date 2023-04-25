package com.sqa.bhyt.service.admin;

import java.util.List;

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

public interface PeriodService {
	ServiceResponse<PeriodInfoResponse> create(PeriodInfoRequest request);
	
	ServiceResponse<PeriodUpdateResponse> update(PeriodUpdateRequest request);
	
	ServiceResponse<Boolean> delete(DeleteRequest request);
	
	ServiceResponse<List<PeriodInfoResponse>> combobox();
	
	ServiceResponse<List<PeriodSearchResponse>> filter(PeriodTextSearch request);
	
	ServiceResponse<PeriodDetailResponse> getDetail(PeriodDetail request);
}
