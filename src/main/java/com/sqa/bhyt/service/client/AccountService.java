package com.sqa.bhyt.service.client;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.user.ActivateInfo;
import com.sqa.bhyt.dto.request.user.VerifyInfo;
import com.sqa.bhyt.dto.response.user.ActivateInfoResponse;
import com.sqa.bhyt.dto.response.user.CurrentUserDTO;

public interface AccountService {
	
//	ServiceResponse<UserInfoResponse> create(UserInfoDTO req);
//	
//	ServiceResponse<UserInfoResponse> update(UserInfoDTO req);
//	
//	ServiceResponse<Boolean> delete(DeleteRequest listId);
//	
//	ServiceResponse<List<UserFilterResponse>> filter(FilterRequest reqFilter);
	
	ServiceResponse<ActivateInfoResponse> activateAccount(ActivateInfo request);
	
	ServiceResponse<Boolean> verifyOTP(VerifyInfo request);
	
	ServiceResponse<String> recoverPassword();
	
	ServiceResponse<CurrentUserDTO> getCurrentUser();
	
	ServiceResponse<Boolean> validateEmail(String email);
	
	public ServiceResponse<Boolean> validateUsername(String username);
	
	public ServiceResponse<Boolean> validatePhoneNumber(String phoneNumber);
}
