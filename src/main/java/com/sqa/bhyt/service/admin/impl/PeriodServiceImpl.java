package com.sqa.bhyt.service.admin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sqa.bhyt.common.constants.Constants;
import com.sqa.bhyt.common.constants.MessageCode;
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
import com.sqa.bhyt.entity.Period;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.repository.PeriodRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.service.admin.PeriodService;

@Service
public class PeriodServiceImpl implements PeriodService{
	private static final Logger logger = LoggerFactory.getLogger(PeriodServiceImpl.class);
	
	@Autowired
	private PeriodRepository periodRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ServiceResponse<PeriodInfoResponse> create(PeriodInfoRequest request) {
		try {
			if(request.getMonth() < 1 || request.getMonth() > 12) {
				return new ServiceResponse<PeriodInfoResponse>(MessageCode.MONTH_INVALID, MessageCode.MONTH_INVALID_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getCode())) {
				return new ServiceResponse<PeriodInfoResponse>(MessageCode.CODE_NOT_EMPTY, MessageCode.CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<PeriodInfoResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			
			if(periodRepository.existsByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED)) {
				return new ServiceResponse<PeriodInfoResponse>(MessageCode.PERIOD_EXISTED, MessageCode.PERIOD_EXISTED_MESSAGE, null);
			}
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<PeriodInfoResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Period period = new Period();
			period.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			period.setCode(request.getCode());
			period.setName(request.getName());
			period.setMonth(request.getMonth());
			period.setStatus(request.getStatus());
			period.setIsDeleted(Constants.NOT_DELETED);
			period.setCreatedDate(new Date());
			period.setCreatedUser(username);
			
			periodRepository.save(period);
			
			Optional<Period> lastCheck = periodRepository.findByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED);
			if(!lastCheck.isPresent()) {
				return new ServiceResponse<PeriodInfoResponse>(MessageCode.CREATE_PERIOD_FAILED, MessageCode.CREATE_PERIOD_FAILED_MESSAGE, null);
			}
			
			PeriodInfoResponse response = new PeriodInfoResponse();
			response.setId(lastCheck.get().getId());
			response.setCode(lastCheck.get().getCode());
			response.setName(lastCheck.get().getName());
			response.setMonth(lastCheck.get().getMonth());
			
			return new ServiceResponse<PeriodInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<PeriodInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<PeriodUpdateResponse> update(PeriodUpdateRequest request) {
		try {
			if(!StringUtils.hasText(request.getId())) {
				return new ServiceResponse<PeriodUpdateResponse>(MessageCode.PERIOD_ID_NOT_EMPTY, MessageCode.PERIOD_ID_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getCode())) {
				return new ServiceResponse<PeriodUpdateResponse>(MessageCode.CODE_NOT_EMPTY, MessageCode.CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<PeriodUpdateResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String getUser = authentication.getName();
			
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<PeriodUpdateResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Optional<Period> period = periodRepository.findByIdAndIsDeleted(request.getId(), Constants.NOT_DELETED);
			if(!period.isPresent()) {
				return new ServiceResponse<PeriodUpdateResponse>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
			}
			
			if(!period.get().getCode().equals(request.getCode())) {
				if(periodRepository.existsByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED)) {
					return new ServiceResponse<PeriodUpdateResponse>(MessageCode.PERIOD_EXISTED, MessageCode.PERIOD_EXISTED_MESSAGE, null);
				}
				else {
					period.get().setCode(request.getCode());
				}
			}
			
			if(!period.get().getName().equals(request.getName())) {
				period.get().setName(request.getName());
			}
			
			if(period.get().getMonth() != request.getMonth()) {
				period.get().setMonth(request.getMonth());
			}
			
			if(period.get().getStatus() != request.getStatus()) {
				period.get().setStatus(request.getStatus());
			}
			
			period.get().setUpdatedDate(new Date());
			period.get().setUpdatedUser(getUser);
			
			periodRepository.save(period.get());
			
			PeriodUpdateResponse response = new PeriodUpdateResponse();
			response.setId(period.get().getId());
			response.setCode(period.get().getCode());
			response.setName(period.get().getName());
			response.setMonth(period.get().getMonth());
			response.setStatus(period.get().getStatus());
			
			return new ServiceResponse<PeriodUpdateResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<PeriodUpdateResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<Boolean> delete(DeleteRequest request) {
		try {
			Set<Period> periods = new HashSet<Period>();
			for(String id: request.getListId()) {
				Optional<Period> period = periodRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
				if(!period.isPresent()) {
					return new ServiceResponse<Boolean>(MessageCode.EXISTED_INVALID_PERIOD, MessageCode.EXISTED_INVALID_PERIOD_MESSAGE, false);
				}
				periods.add(period.get());
			}
			
			for(Period i : periods) {
				i.setIsDeleted(Constants.IS_DELETED);
			}
			periodRepository.saveAll(periods);
			return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
		}
	}

	@Override
	public ServiceResponse<List<PeriodInfoResponse>> combobox() {
		try {
			
			List<Period> periods = periodRepository.findAll();
			List<PeriodInfoResponse> response = new ArrayList<>();
			for(Period data : periods) {
				PeriodInfoResponse responseData = new PeriodInfoResponse();
				responseData.setId(data.getId());
				responseData.setCode(data.getCode());
				responseData.setName(data.getName());
				responseData.setMonth(data.getMonth());
				response.add(responseData);
			}	
			return new ServiceResponse<List<PeriodInfoResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<PeriodInfoResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<List<PeriodSearchResponse>> filter(PeriodTextSearch request) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<List<PeriodSearchResponse>>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			List<Period> periods = periodRepository.filter(request.getTextSearch(), Constants.NOT_DELETED);
			if(periods.size() == 0) {
				return new ServiceResponse<List<PeriodSearchResponse>>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			List<PeriodSearchResponse> response = new ArrayList<>();
			for(Period data : periods) {
				PeriodSearchResponse responseData = new PeriodSearchResponse();
				responseData.setId(data.getId());
				responseData.setCode(data.getCode());
				responseData.setName(data.getName());
				responseData.setMonth(data.getMonth());
				responseData.setStatus(data.getStatus());
				responseData.setCreatedDate(data.getCreatedDate());
				responseData.setUpdatedDate(data.getUpdatedDate());
				
				response.add(responseData);
			}	
			return new ServiceResponse<List<PeriodSearchResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<PeriodSearchResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<PeriodDetailResponse> getDetail(PeriodDetail request) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<PeriodDetailResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Optional<Period> period = periodRepository.findByIdAndIsDeleted(request.getId(), Constants.NOT_DELETED);
			if(!period.isPresent()) {
				return new ServiceResponse<PeriodDetailResponse>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
			}
			
			PeriodDetailResponse response = new PeriodDetailResponse();
			response.setId(period.get().getId());
			response.setCode(period.get().getCode());
			response.setName(period.get().getName());
			response.setMonth(period.get().getMonth());
			response.setStatus(period.get().getStatus());
			response.setCreatedDate(period.get().getCreatedDate());
			response.setUpdatedDate(period.get().getUpdatedDate());
			
			return new ServiceResponse<PeriodDetailResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<PeriodDetailResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

}
