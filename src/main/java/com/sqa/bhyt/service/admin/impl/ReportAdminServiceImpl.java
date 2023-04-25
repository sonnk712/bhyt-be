package com.sqa.bhyt.service.admin.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sqa.bhyt.common.constants.Constants;
import com.sqa.bhyt.common.constants.MessageCode;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.common.ultis.DateUtils;
import com.sqa.bhyt.dto.request.insurance.FilterByIdentityRequest;
import com.sqa.bhyt.dto.request.insurance.InsuranceInfoByPeriod;
import com.sqa.bhyt.dto.request.insurance.InsuranceInfoByPlace;
import com.sqa.bhyt.dto.request.statistic.RevenueStatistic;
import com.sqa.bhyt.dto.request.statistic.StatisticRequest;
import com.sqa.bhyt.dto.response.insurance.FilterByIdentityResponse;
import com.sqa.bhyt.dto.response.insurance.InsuranceInfoByPlaceResponse;
import com.sqa.bhyt.dto.response.statistic.StatisticDataResponse;
import com.sqa.bhyt.dto.response.statistic.StatisticResponse;
import com.sqa.bhyt.dto.response.statistic.StatusResponse;
import com.sqa.bhyt.entity.HealthInsurance;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.repository.HealthInsuranceRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.service.client.impl.ReportServiceImpl;

@Service
public class ReportAdminServiceImpl implements com.sqa.bhyt.service.admin.ReportAdminService{
	private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private HealthInsuranceRepository healthInsuranceRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ServiceResponse<List<InsuranceInfoByPlaceResponse>> notPaid(InsuranceInfoByPlace request) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getDistrictCodeName())) {
				return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.DISTRICT_CODE_NOT_EMPTY, MessageCode.DISTRICT_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			List<HealthInsurance> insurances = null;
					
			if(!StringUtils.hasText(request.getWardCodeName())) {
				insurances = healthInsuranceRepository.findByCardIssuerCodeAndIsDeletedAndStatus(request.getDistrictCodeName(), 
						Constants.NOT_DELETED, Constants.NOT_ACTIVATE);
				if(insurances.size() == 0) {
					return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.PERSONAL_INFORMATION_INVALID,
							MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
				}
				
			}
			else {
				insurances = healthInsuranceRepository.findByRegisterPlaceCodeAndCardIssuerCodeAndIsDeletedAndStatus(request.getWardCodeName(), 
						request.getDistrictCodeName(), Constants.NOT_DELETED, Constants.NOT_ACTIVATE);
				if(insurances.size() == 0) {
					return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.PERSONAL_INFORMATION_INVALID,
							MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
				}
			}
			
			List<InsuranceInfoByPlaceResponse> responses = new ArrayList<>();
			for(HealthInsurance i : insurances) {
				InsuranceInfoByPlaceResponse response = new InsuranceInfoByPlaceResponse();
				response.setIdentityNumber(i.getIdentityNumber());
				response.setName(i.getName());
				response.setBirthDay(i.getBirthday());
				response.setGender(i.getGender());
				response.setRegisterPlaceCode(i.getRegisterPlaceCode());
				response.setRegisterPlaceName(i.getRegisterPlaceName());
				response.setCardIssueCode(i.getCardIssuerCode());
				response.setCardIssueName(i.getCardIssuerName());
				response.setValidFrom(i.getValidTimeFrom());
				response.setValidTo(i.getValidTimeTo());
				response.setStatus(i.getStatus());
				
				responses.add(response);
			}
			
			return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, responses);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<List<InsuranceInfoByPlaceResponse>> isPaid(InsuranceInfoByPlace request) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getDistrictCodeName())) {
				return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.DISTRICT_CODE_NOT_EMPTY, MessageCode.DISTRICT_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			List<HealthInsurance> insurances = null;
					
			if(!StringUtils.hasText(request.getWardCodeName())) {
				insurances = healthInsuranceRepository.findByCardIssuerCodeAndIsDeletedAndStatus(request.getDistrictCodeName(), 
						Constants.NOT_DELETED, Constants.IS_ACTIVATE);
				if(insurances.size() == 0) {
					return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.PERSONAL_INFORMATION_INVALID,
							MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
				}
				
			}
			else {
				insurances = healthInsuranceRepository.findByRegisterPlaceCodeAndCardIssuerCodeAndIsDeletedAndStatus(request.getWardCodeName(), 
						request.getDistrictCodeName(), Constants.NOT_DELETED, Constants.IS_ACTIVATE);
				if(insurances.size() == 0) {
					return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.PERSONAL_INFORMATION_INVALID,
							MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, null);
				}
			}
			
			List<InsuranceInfoByPlaceResponse> responses = new ArrayList<>();
			for(HealthInsurance i : insurances) {
				InsuranceInfoByPlaceResponse response = new InsuranceInfoByPlaceResponse();
				response.setIdentityNumber(i.getIdentityNumber());
				response.setName(i.getName());
				response.setBirthDay(i.getBirthday());
				response.setGender(i.getGender());
				response.setRegisterPlaceCode(i.getRegisterPlaceCode());
				response.setRegisterPlaceName(i.getRegisterPlaceName());
				response.setCardIssueCode(i.getCardIssuerCode());
				response.setCardIssueName(i.getCardIssuerName());
				response.setValidFrom(i.getValidTimeFrom());
				response.setValidTo(i.getValidTimeTo());
				response.setStatus(i.getStatus());
				
				responses.add(response);
			}
			
			return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, responses);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<FilterByIdentityResponse> revenueByPeriod(FilterByIdentityRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<FilterByIdentityResponse> revenueByPlace(FilterByIdentityRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<List<InsuranceInfoByPlaceResponse>> getInfoByPeriod(InsuranceInfoByPeriod request) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(DateUtils.dateToStringStartWithDDMMYYY(request.getPaidFrom()))) {
				return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.PAID_TO_NOT_EMPTY, MessageCode.PAID_TO_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(DateUtils.dateToStringStartWithDDMMYYY(request.getPaidTo()))) {
				return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.PAID_TO_NOT_EMPTY, MessageCode.PAID_TO_NOT_EMPTY_MESSAGE, null);
			}
			
			List<HealthInsurance> insurances = healthInsuranceRepository.findbyPeriod(request.getPaidFrom(), request.getPaidTo(), Constants.NOT_DELETED);
			
			if(insurances.size() == 0) {
				return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.TRANSACION_IS_EMPTY_IN_PERIOD,
						MessageCode.TRANSACION_IS_EMPTY_IN_PERIOD_MESSAGE, null);
			}
			
			List<InsuranceInfoByPlaceResponse> responses = new ArrayList<>();
			for(HealthInsurance i : insurances) {
				InsuranceInfoByPlaceResponse response = new InsuranceInfoByPlaceResponse();
				response.setIdentityNumber(i.getIdentityNumber());
				response.setName(i.getName());
				response.setBirthDay(i.getBirthday());
				response.setGender(i.getGender());
				response.setRegisterPlaceCode(i.getRegisterPlaceCode());
				response.setRegisterPlaceName(i.getRegisterPlaceName());
				response.setCardIssueCode(i.getCardIssuerCode());
				response.setCardIssueName(i.getCardIssuerName());
				response.setValidFrom(i.getValidTimeFrom());
				response.setValidTo(i.getValidTimeTo());
				response.setStatus(i.getStatus());
				
				responses.add(response);
			}
			
			return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, responses);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<InsuranceInfoByPlaceResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<StatisticResponse> statistic(StatisticRequest request, Pageable pageable) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<StatisticResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Integer firstResult = (pageable.getPageNumber() * pageable.getPageSize());
			Integer maxResults = (pageable.getPageSize());
			
			// cộng 1 ngày vào endDate
			Date date = DateUtils.convertStringToDate(request.getEndDate());
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 1);
			Date endDate = c.getTime();
			List<HealthInsurance> listData = healthInsuranceRepository.statistic(request.getSearchText(), request.getStatus(), 
					Constants.NOT_DELETED, DateUtils.convertStringToDate(request.getStartDate()) , endDate, firstResult, maxResults);
			
//			if(listData.size() == 0) {
//				return new ServiceResponse<StatisticResponse>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
//			}
			
			StatisticResponse response = new StatisticResponse();
			List<StatisticDataResponse> listDataResponse = new ArrayList<>();
			for(HealthInsurance data : listData) {
				StatisticDataResponse dataResponse = new StatisticDataResponse();
				dataResponse.setIdentityId(data.getIdentityNumber());
				dataResponse.setName(data.getName());
				dataResponse.setIssuerName(data.getCardIssuerName());
				dataResponse.setUpdatedDate(data.getUpdatedDate());
				dataResponse.setValidFrom(data.getValidTimeFrom());
				dataResponse.setValidTo(data.getValidTimeTo());
				dataResponse.setStatus(data.getStatus());
				
				listDataResponse.add(dataResponse);
			}
			
			Integer totalCount = 0;
			
			List<StatusResponse> countStatus = new ArrayList<>();
			for(Integer i : request.getStatus()) {
				StatusResponse dataResponse = new StatusResponse();
				Integer cnt = healthInsuranceRepository.count(request.getSearchText(), i, 
						Constants.NOT_DELETED, DateUtils.convertStringToDate(request.getStartDate()) , endDate);
				dataResponse.setCount(cnt);
				dataResponse.setStatus(i);
				totalCount += cnt;
				countStatus.add(dataResponse);
			}
			
			listDataResponse.sort(
					(StatisticDataResponse h1, StatisticDataResponse h2) -> h1.getStatus().compareTo(h2.getStatus()));
	
			response.setTotal(totalCount);
			response.setCountStatus(countStatus);
			response.setListData(listDataResponse);
			
			
			
			return new ServiceResponse<StatisticResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<StatisticResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<StatisticResponse> revenueStatistic(RevenueStatistic request, Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
