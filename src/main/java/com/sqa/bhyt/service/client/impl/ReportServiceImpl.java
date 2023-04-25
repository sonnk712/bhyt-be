package com.sqa.bhyt.service.client.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sqa.bhyt.common.constants.Constants;
import com.sqa.bhyt.common.constants.MessageCode;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.common.ultis.DateUtils;
import com.sqa.bhyt.dto.request.insurance.FilterByIdentityInfoRequest;
import com.sqa.bhyt.dto.request.insurance.FilterByIdentityRequest;
import com.sqa.bhyt.dto.request.insurance.InsuranceByFamily;
import com.sqa.bhyt.dto.response.insurance.FilterByIdentityInfoResponse;
import com.sqa.bhyt.dto.response.insurance.FilterByIdentityResponse;
import com.sqa.bhyt.dto.response.insurance.InsuranceByFamilyResponse;
import com.sqa.bhyt.entity.HealthInsurance;
import com.sqa.bhyt.entity.Identity;
import com.sqa.bhyt.repository.HealthInsuranceRepository;
import com.sqa.bhyt.repository.IdentityRepository;
import com.sqa.bhyt.service.client.ReportService;

@Service
public class ReportServiceImpl implements ReportService{
	private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private HealthInsuranceRepository healthInsuranceRepository;
	
	@Autowired
	private IdentityRepository identityRepository;
	
	@Override
	public ServiceResponse<FilterByIdentityResponse> checkValidInsuranceByCccd(FilterByIdentityRequest request) {
		
		try {
			
			if(!StringUtils.hasText(request.getIdentityNumber())) {
				return new ServiceResponse<FilterByIdentityResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<FilterByIdentityResponse>(MessageCode.INSURANCE_IDENTITY_NOT_EMPTY, 
						MessageCode.INSURANCE_IDENTITY_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(DateUtils.dateToStringStartWithDDMMYYY(request.getBirthDay()))) {
				return new ServiceResponse<FilterByIdentityResponse>(MessageCode.DATE_NOT_EMPTY, MessageCode.DATE_NOT_EMPTY_MESSAGE, null);
			}
			
			Optional<HealthInsurance> insurance = healthInsuranceRepository.findByIdentityNumberAndIsDeleted(request.getIdentityNumber(), Constants.NOT_DELETED);
			if(!insurance.isPresent()) {
				return new ServiceResponse<FilterByIdentityResponse>(MessageCode.PERSONAL_INFORMATION_INVALID,
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			FilterByIdentityResponse response = new FilterByIdentityResponse();
			response.setIdentityNumber(insurance.get().getIdentityNumber());
			response.setName(insurance.get().getName());
			response.setBirthDay(insurance.get().getBirthday());
			response.setGender(insurance.get().getGender());
			response.setRegisterPlaceCode(insurance.get().getRegisterPlaceCode());
			response.setRegisterPlaceName(insurance.get().getRegisterPlaceName());
			response.setCardIssueCode(insurance.get().getCardIssuerCode());
			response.setCardIssueName(insurance.get().getCardIssuerName());
			response.setValidFrom(insurance.get().getValidTimeFrom());
			response.setValidTo(insurance.get().getValidTimeTo());
			response.setStatus(insurance.get().getStatus());
			
			return new ServiceResponse<FilterByIdentityResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<FilterByIdentityResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<FilterByIdentityInfoResponse> findIdentityNumberByInfo(FilterByIdentityInfoRequest request) {
		try {
			if(!StringUtils.hasText(request.getProvinceCode())) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.PROVINE_CODE_NOT_EMPTY, MessageCode.PROVINE_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getDistrictCode())) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.DISTRICT_CODE_NOT_EMPTY, MessageCode.DISTRICT_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getWardCode())) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.WARDS_CODE_NOT_EMPTY, MessageCode.WARDS_CODE_NOT_EMPTY_MESSAGE, null);
			}
			

			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getCccd())) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.CCCD_NOT_EMPTY, 
						MessageCode.CCCD_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(DateUtils.dateToStringStartWithDDMMYYY(request.getBirthDay()))) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.DATE_NOT_EMPTY, MessageCode.DATE_NOT_EMPTY_MESSAGE, null);
			}
			
			Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(request.getCccd(), Constants.NOT_DELETED); 
			if(!identity.isPresent()) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.PERSONAL_INFORMATION_INVALID, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			if(!identity.get().getProvinceIdentity().getCode().equals(request.getProvinceCode())) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.PERSONAL_INFORMATION_INVALID, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			if(!identity.get().getDistrictIdentity().getCode().equals(request.getDistrictCode())) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.PERSONAL_INFORMATION_INVALID, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			if(!identity.get().getWardIdentity().getCode().equals(request.getWardCode())) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.PERSONAL_INFORMATION_INVALID, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			if(!identity.get().getName().equals(request.getName())) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.PERSONAL_INFORMATION_INVALID, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			String birthDayReqStr = DateUtils.dateToStringStartWithDDMMYYY(request.getBirthDay());
			String birthDayStr = DateUtils.dateToStringStartWithDDMMYYY(identity.get().getBirthday());
			if(!birthDayReqStr.equals(birthDayStr)) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.WARDS_NOT_FOUND, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			Optional<HealthInsurance> insurance = healthInsuranceRepository.findByCccdAndIsDeleted(identity.get().getCccd(), Constants.NOT_DELETED);
			if(!insurance.isPresent()) {
				return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.PERSONAL_INFORMATION_INVALID, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			
			FilterByIdentityInfoResponse response = new FilterByIdentityInfoResponse();
			response.setInsuranceId(insurance.get().getIdentityNumber());
			response.setName(insurance.get().getName());
			response.setBirthDay(insurance.get().getBirthday());
			response.setGender(insurance.get().getGender());
			response.setRegisterPlaceCode(insurance.get().getRegisterPlaceCode());
			response.setRegisterPlaceName(insurance.get().getRegisterPlaceName());
			response.setCardIssueCode(insurance.get().getCardIssuerCode());
			response.setCardIssueName(insurance.get().getCardIssuerName());
			response.setValidFrom(insurance.get().getValidTimeFrom());
			response.setValidTo(insurance.get().getValidTimeTo());
			response.setStatus(insurance.get().getStatus());
			
			return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<FilterByIdentityInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<List<InsuranceByFamilyResponse>> findInsuranceByFamily(InsuranceByFamily request) {
		try {
			if(!StringUtils.hasText(request.getCccd())) {
				return new ServiceResponse<List<InsuranceByFamilyResponse>>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<List<InsuranceByFamilyResponse>>(MessageCode.INSURANCE_IDENTITY_NOT_EMPTY, 
						MessageCode.INSURANCE_IDENTITY_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(DateUtils.dateToStringStartWithDDMMYYY(request.getBirthDay()))) {
				return new ServiceResponse<List<InsuranceByFamilyResponse>>(MessageCode.DATE_NOT_EMPTY, MessageCode.DATE_NOT_EMPTY_MESSAGE, null);
			}
			
			Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(request.getCccd(), Constants.NOT_DELETED); 
			if(!identity.isPresent()) {
				return new ServiceResponse<List<InsuranceByFamilyResponse>>(MessageCode.PERSONAL_INFORMATION_INVALID, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
			}
			
			List<Identity> identities = identityRepository.findByFamilyAndIsDeleted(identity.get().getFamily().getId(), Constants.NOT_DELETED);
			List<InsuranceByFamilyResponse> responses = new ArrayList<>();
			for(Identity i : identities) {
				Optional<HealthInsurance> insurance = healthInsuranceRepository.findByCccdAndIsDeleted(i.getCccd(), Constants.NOT_DELETED);
				if(!insurance.isPresent()) {
					continue;
				}
				InsuranceByFamilyResponse response = new InsuranceByFamilyResponse();
				response.setInsuranceId(insurance.get().getIdentityNumber());
				response.setName(insurance.get().getName());
				response.setBirthDay(insurance.get().getBirthday());
				response.setGender(insurance.get().getGender());
				response.setRegisterPlaceCode(insurance.get().getRegisterPlaceCode());
				response.setRegisterPlaceName(insurance.get().getRegisterPlaceName());
				response.setCardIssueCode(insurance.get().getCardIssuerCode());
				response.setCardIssueName(insurance.get().getCardIssuerName());
				response.setValidFrom(insurance.get().getValidTimeFrom());
				response.setValidTo(insurance.get().getValidTimeTo());
				response.setStatus(insurance.get().getStatus());
				responses.add(response);
			}
			
			return new ServiceResponse<List<InsuranceByFamilyResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, responses);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<InsuranceByFamilyResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	

}
