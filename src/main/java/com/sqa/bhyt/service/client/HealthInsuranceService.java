package com.sqa.bhyt.service.client;


import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.insurance.FamilyProfileRequest;
import com.sqa.bhyt.dto.request.insurance.OrginizationProfileRequest;
import com.sqa.bhyt.dto.request.insurance.PaidBySocialInsuranceRequet;
import com.sqa.bhyt.dto.request.insurance.PaidByStateBudgetRequest;
import com.sqa.bhyt.dto.request.insurance.PaymentInfoRequest;
import com.sqa.bhyt.dto.request.insurance.PersonalProfileRequest;
import com.sqa.bhyt.dto.response.profile.ProfileReponse;

public interface HealthInsuranceService {
	ServiceResponse<ProfileReponse> createFamilyProfile(FamilyProfileRequest request);
	
	ServiceResponse<ProfileReponse> createPersonalProfile(PersonalProfileRequest request);
	
	ServiceResponse<ProfileReponse> createOrganizationProfile(OrginizationProfileRequest request);
	
	ServiceResponse<ProfileReponse> createBySocialInsuranceProfile(PaidBySocialInsuranceRequet request);
	
	ServiceResponse<ProfileReponse> createByStateBudgetProfile(PaidByStateBudgetRequest request);
	
	ServiceResponse<Boolean> payment(PaymentInfoRequest request);
	
	boolean extendInsurance();
	
	boolean getTransactionHistory();
}
