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
import com.sqa.bhyt.dto.request.type.TypeInfoRequest;
import com.sqa.bhyt.dto.request.type.TypeUpdateRequest;
import com.sqa.bhyt.dto.response.type.TypeInfoResponse;
import com.sqa.bhyt.dto.response.type.TypeUpdateResponse;
import com.sqa.bhyt.entity.Type;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.repository.TypeRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.service.admin.TypeService;

@Service
public class TypeServiceImpl implements TypeService{
	private static final Logger logger = LoggerFactory.getLogger(TypeServiceImpl.class);
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ServiceResponse<TypeInfoResponse> create(TypeInfoRequest request) {
		try {
			if(!StringUtils.hasText(request.getCode())) {
				return new ServiceResponse<TypeInfoResponse>(MessageCode.CODE_NOT_EMPTY, MessageCode.CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<TypeInfoResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			
			if(typeRepository.existsByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED)) {
				return new ServiceResponse<TypeInfoResponse>(MessageCode.TYPE_EXISTED, MessageCode.TYPE_EXISTED_MESSAGE, null);
			}
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<TypeInfoResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Type type = new Type();
			type.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			type.setCode(request.getCode());
			type.setName(request.getName());
			type.setStatus(Constants.IS_ACTIVATE);
			type.setIsDeleted(Constants.NOT_DELETED);
			type.setCreatedDate(new Date());
			type.setCreatedUser(username);
			
			typeRepository.save(type);
			
			Optional<Type> lastCheck = typeRepository.findByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED);
			if(!lastCheck.isPresent()) {
				return new ServiceResponse<TypeInfoResponse>(MessageCode.CREATE_TYPE_FAILED, MessageCode.CREATE_TYPE_FAILED_MESSAGE, null);
			}
			
			TypeInfoResponse response = new TypeInfoResponse();
			response.setId(lastCheck.get().getId());
			response.setCode(lastCheck.get().getCode());
			response.setName(lastCheck.get().getName());
			
			
			return new ServiceResponse<TypeInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<TypeInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<TypeUpdateResponse> update(TypeUpdateRequest request) {
		try {
			if(!StringUtils.hasText(request.getId())) {
				return new ServiceResponse<TypeUpdateResponse>(MessageCode.TYPE_ID_NOT_EMPTY, MessageCode.TYPE_ID_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getCode())) {
				return new ServiceResponse<TypeUpdateResponse>(MessageCode.CODE_NOT_EMPTY, MessageCode.CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<TypeUpdateResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String getUser = authentication.getName();
			
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<TypeUpdateResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Optional<Type> type = typeRepository.findByIdAndIsDeleted(request.getId(), Constants.NOT_DELETED);
			if(!type.isPresent()) {
				return new ServiceResponse<TypeUpdateResponse>(MessageCode.TYPE_NOT_FOUND, MessageCode.TYPE_NOT_FOUND_MESSAGE, null);
			}
			
			if(!type.get().getCode().equals(request.getCode())) {
				if(typeRepository.existsByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED)) {
					return new ServiceResponse<TypeUpdateResponse>(MessageCode.TYPE_EXISTED, MessageCode.TYPE_EXISTED_MESSAGE, null);
				}
				else {
					type.get().setCode(request.getCode());
				}
			}
			
			if(!type.get().getName().equals(request.getName())) {
				type.get().setName(request.getName());
			}
			
			type.get().setUpdatedDate(new Date());
			type.get().setUpdatedUser(getUser);
			
			typeRepository.save(type.get());
			
			TypeUpdateResponse response = new TypeUpdateResponse();
			response.setId(type.get().getId());
			response.setCode(type.get().getCode());
			response.setName(type.get().getName());
			
			return new ServiceResponse<TypeUpdateResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<TypeUpdateResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<Boolean> delete(DeleteRequest request) {
		try {
			Set<Type> types = new HashSet<Type>();
			for(String id: request.getListId()) {
				Optional<Type> category = typeRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
				if(!category.isPresent()) {
					return new ServiceResponse<Boolean>(MessageCode.EXISTED_INVALID_TYPE, MessageCode.EXISTED_INVALID_TYPE_MESSAGE, false);
				}
				types.add(category.get());
			}
			
			for(Type i : types) {
				i.setIsDeleted(Constants.IS_DELETED);
			}
			typeRepository.saveAll(types);
			return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
		}
	}

	@Override
	public ServiceResponse<List<TypeInfoResponse>> combobox() {
		try {
			
			List<Type> types = typeRepository.findAll();
			List<TypeInfoResponse> response = new ArrayList<>();
			for(Type data : types) {
				TypeInfoResponse responseData = new TypeInfoResponse();
				responseData.setId(data.getId());
				responseData.setCode(data.getCode());
				responseData.setName(data.getName());
				response.add(responseData);
			}	
			return new ServiceResponse<List<TypeInfoResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<TypeInfoResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

}
