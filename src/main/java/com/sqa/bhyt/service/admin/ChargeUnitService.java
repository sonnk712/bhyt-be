package com.sqa.bhyt.service.admin;

import java.util.List;

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

public interface ChargeUnitService {
	ServiceResponse<ChargeInfoResponse> create(ChargeInfoRequest request);
	
	ServiceResponse<ChargeUpdateResponse> update(ChargeUpdateRequest request);
	
	ServiceResponse<Boolean> delete(DeleteRequest request);
	
	ServiceResponse<List<ChargeUnitInfoResponse>> combobox();
	
	ServiceResponse<List<ChargeUnitSearchResponse>> filter(ChargeUnitTextSearch request);
	
	ServiceResponse<ChargeUnitDetailResponse> getDetail(ChargeUnitDetail request);
}
