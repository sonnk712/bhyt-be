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
import com.sqa.bhyt.dto.request.chargeunit.ChargeInfoRequest;
import com.sqa.bhyt.dto.request.chargeunit.ChargeUnitDetail;
import com.sqa.bhyt.dto.request.chargeunit.ChargeUnitTextSearch;
import com.sqa.bhyt.dto.request.chargeunit.ChargeUpdateRequest;
import com.sqa.bhyt.dto.response.chargeunit.ChargeInfoResponse;
import com.sqa.bhyt.dto.response.chargeunit.ChargeUnitDetailResponse;
import com.sqa.bhyt.dto.response.chargeunit.ChargeUnitInfoResponse;
import com.sqa.bhyt.dto.response.chargeunit.ChargeUnitSearchResponse;
import com.sqa.bhyt.dto.response.chargeunit.ChargeUpdateResponse;
import com.sqa.bhyt.entity.ChargeUnit;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.repository.ChargeUnitRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.service.admin.ChargeUnitService;

@Service
public class ChargeUnitServiceImpl implements ChargeUnitService{
	private static final Logger logger = LoggerFactory.getLogger(ChargeUnitServiceImpl.class);
	
	@Autowired
	private ChargeUnitRepository chargeUnitRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ServiceResponse<ChargeInfoResponse> create(ChargeInfoRequest request) {
		try {
			
			if(request.getCost() < 0) {
				return new ServiceResponse<ChargeInfoResponse>(MessageCode.COST_INVALID, MessageCode.COST_INVALID_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getCode())) {
				return new ServiceResponse<ChargeInfoResponse>(MessageCode.CODE_NOT_EMPTY, MessageCode.CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<ChargeInfoResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			
			if(chargeUnitRepository.existsByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED)) {
				return new ServiceResponse<ChargeInfoResponse>(MessageCode.CHARGE_UNIT_EXISTED, MessageCode.CHARGE_UNIT_EXISTED_MESSAGE, null);
			}
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<ChargeInfoResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			ChargeUnit chargeUnit = new ChargeUnit();
			chargeUnit.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			chargeUnit.setCode(request.getCode());
			chargeUnit.setName(request.getName());
			chargeUnit.setCost(request.getCost());
			chargeUnit.setStatus(Constants.IS_ACTIVATE);
			chargeUnit.setIsDeleted(Constants.NOT_DELETED);
			chargeUnit.setCreatedDate(new Date());
			chargeUnit.setCreatedUser(username);
			
			chargeUnitRepository.save(chargeUnit);
			
			Optional<ChargeUnit> lastCheck = chargeUnitRepository.findByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED);
			if(!lastCheck.isPresent()) {
				return new ServiceResponse<ChargeInfoResponse>(MessageCode.CREATE_CHARGE_UNIT_FAILED, MessageCode.CREATE_CHARGE_UNIT_FAILED_MESSAGE, null);
			}
			
			ChargeInfoResponse response = new ChargeInfoResponse();
			response.setId(lastCheck.get().getId());
			response.setCode(lastCheck.get().getCode());
			response.setName(lastCheck.get().getName());
			response.setCost(lastCheck.get().getCost());
			
			return new ServiceResponse<ChargeInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ChargeInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<ChargeUpdateResponse> update(ChargeUpdateRequest request) {
		try {
			
			if(request.getCost() < 0) {
				return new ServiceResponse<ChargeUpdateResponse>(MessageCode.COST_INVALID, MessageCode.COST_INVALID_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getId())) {
				return new ServiceResponse<ChargeUpdateResponse>(MessageCode.CHARGE_UNIT_ID_NOT_EMPTY, MessageCode.CHARGE_UNIT_ID_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getCode())) {
				return new ServiceResponse<ChargeUpdateResponse>(MessageCode.CODE_NOT_EMPTY, MessageCode.CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<ChargeUpdateResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String getUser = authentication.getName();
			
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<ChargeUpdateResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Optional<ChargeUnit> chargeUnit = chargeUnitRepository.findByIdAndIsDeleted(request.getId(), Constants.NOT_DELETED);
			if(!chargeUnit.isPresent()) {
				return new ServiceResponse<ChargeUpdateResponse>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
			}
			
			if(!chargeUnit.get().getCode().equals(request.getCode())) {
				if(chargeUnitRepository.existsByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED)) {
					return new ServiceResponse<ChargeUpdateResponse>(MessageCode.CHARGE_UNIT_EXISTED, MessageCode.CHARGE_UNIT_EXISTED_MESSAGE, null);
				}
				else {
					chargeUnit.get().setCode(request.getCode());
				}
			}
			
			if(!chargeUnit.get().getName().equals(request.getName())) {
				chargeUnit.get().setName(request.getName());
			}
			
			if(chargeUnit.get().getCost() != request.getCost()) {
				chargeUnit.get().setCost(request.getCost());
			}
			
			chargeUnit.get().setUpdatedDate(new Date());
			chargeUnit.get().setUpdatedUser(getUser);
			
			chargeUnitRepository.save(chargeUnit.get());
			
			ChargeUpdateResponse response = new ChargeUpdateResponse();
			response.setId(chargeUnit.get().getId());
			response.setCode(chargeUnit.get().getCode());
			response.setName(chargeUnit.get().getName());
			response.setCost(chargeUnit.get().getCost());
			
			return new ServiceResponse<ChargeUpdateResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ChargeUpdateResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<Boolean> delete(DeleteRequest request) {
		try {
			Set<ChargeUnit> chargeUnits = new HashSet<ChargeUnit>();
			for(String id: request.getListId()) {
				Optional<ChargeUnit> chargeUnit = chargeUnitRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
				if(!chargeUnit.isPresent()) {
					return new ServiceResponse<Boolean>(MessageCode.EXISTED_INVALID_CHARGE_UNIT, MessageCode.EXISTED_INVALID_CHARGE_UNIT_MESSAGE, false);
				}
				chargeUnits.add(chargeUnit.get());
			}
			
			for(ChargeUnit i : chargeUnits) {
				i.setIsDeleted(Constants.IS_DELETED);
			}
			chargeUnitRepository.saveAll(chargeUnits);
			return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
		}
	}

	@Override
	public ServiceResponse<List<ChargeUnitInfoResponse>> combobox() {
		try {
			
			List<ChargeUnit> chargeUnits = chargeUnitRepository.findAll();
			List<ChargeUnitInfoResponse> response = new ArrayList<>();
			for(ChargeUnit data : chargeUnits) {
				ChargeUnitInfoResponse responseData = new ChargeUnitInfoResponse();
				responseData.setId(data.getId());
				responseData.setCode(data.getCode());
				responseData.setName(data.getName());
				responseData.setCost(data.getCost());
				response.add(responseData);
			}	
			return new ServiceResponse<List<ChargeUnitInfoResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<ChargeUnitInfoResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<List<ChargeUnitSearchResponse>> filter(ChargeUnitTextSearch request) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<List<ChargeUnitSearchResponse>>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			List<ChargeUnit> chargeUnits = chargeUnitRepository.filter(request.getTextSearch(), Constants.NOT_DELETED);
			if(chargeUnits.size() == 0) {
				return new ServiceResponse<List<ChargeUnitSearchResponse>>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			List<ChargeUnitSearchResponse> response = new ArrayList<>();
			for(ChargeUnit data : chargeUnits) {
				ChargeUnitSearchResponse responseData = new ChargeUnitSearchResponse();
				responseData.setId(data.getId());
				responseData.setCode(data.getCode());
				responseData.setName(data.getName());
				responseData.setCost(data.getCost());
				responseData.setStatus(data.getStatus());
				responseData.setCreatedDate(data.getCreatedDate());
				responseData.setUpdatedDate(data.getUpdatedDate());
				
				response.add(responseData);
			}	
			return new ServiceResponse<List<ChargeUnitSearchResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<ChargeUnitSearchResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<ChargeUnitDetailResponse> getDetail(ChargeUnitDetail request) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<ChargeUnitDetailResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Optional<ChargeUnit> chargeUnit = chargeUnitRepository.findByIdAndIsDeleted(request.getId(), Constants.NOT_DELETED);
			if(!chargeUnit.isPresent()) {
				return new ServiceResponse<ChargeUnitDetailResponse>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
			}
			
			ChargeUnitDetailResponse response = new ChargeUnitDetailResponse();
			response.setId(chargeUnit.get().getId());
			response.setCode(chargeUnit.get().getCode());
			response.setName(chargeUnit.get().getName());
			response.setCost(chargeUnit.get().getCost());
			response.setStatus(chargeUnit.get().getStatus());
			response.setCreatedDate(chargeUnit.get().getCreatedDate());
			response.setUpdatedDate(chargeUnit.get().getUpdatedDate());
			
			return new ServiceResponse<ChargeUnitDetailResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ChargeUnitDetailResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

}
