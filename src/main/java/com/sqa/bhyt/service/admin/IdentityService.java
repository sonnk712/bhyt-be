package com.sqa.bhyt.service.admin;

import java.util.List;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.identity.IdentityByFamilyRequest;
import com.sqa.bhyt.dto.request.identity.IdentityInfoRequest;
import com.sqa.bhyt.dto.response.identity.IdentityInfoResponse;

public interface IdentityService {
	ServiceResponse<IdentityInfoResponse> create(IdentityInfoRequest request);
	
	ServiceResponse<List<IdentityInfoResponse>> familyCreate(IdentityByFamilyRequest request);
}
