package com.sqa.bhyt.service.admin;

import java.util.List;

import com.sqa.bhyt.common.dto.request.DeleteRequest;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.type.TypeInfoRequest;
import com.sqa.bhyt.dto.request.type.TypeUpdateRequest;
import com.sqa.bhyt.dto.response.type.TypeInfoResponse;
import com.sqa.bhyt.dto.response.type.TypeUpdateResponse;

public interface TypeService {
	ServiceResponse<TypeInfoResponse> create(TypeInfoRequest request);
	
	ServiceResponse<TypeUpdateResponse> update(TypeUpdateRequest request);
	
	ServiceResponse<Boolean> delete(DeleteRequest request);
	
	ServiceResponse<List<TypeInfoResponse>> combobox();
}
