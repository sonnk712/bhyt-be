package com.sqa.bhyt.service.admin;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.profile.ProfileDetail;
import com.sqa.bhyt.dto.request.profile.TypeProfileFilter;
import com.sqa.bhyt.dto.request.profile.PeriodProfileFilter;
import com.sqa.bhyt.dto.response.profile.ProfileDetailResponse;
import com.sqa.bhyt.dto.response.profile.ProfileInfoResponse;

public interface ProfileService {
	ServiceResponse<ProfileInfoResponse> filterByPeriod(PeriodProfileFilter request);
	
	ServiceResponse<ProfileInfoResponse> filterByType(TypeProfileFilter request);
	
	ServiceResponse<ProfileInfoResponse> filterByStatus(TypeProfileFilter request);
	
	ServiceResponse<ProfileDetailResponse> detail(ProfileDetail request);
	
	ServiceResponse<ProfileInfoResponse> getAll();
}
