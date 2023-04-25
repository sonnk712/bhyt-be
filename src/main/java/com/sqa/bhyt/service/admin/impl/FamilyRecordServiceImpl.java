package com.sqa.bhyt.service.admin.impl;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sqa.bhyt.common.constants.Constants;
import com.sqa.bhyt.common.constants.MessageCode;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.common.ultis.StringUtils;
import com.sqa.bhyt.entity.FamilyRecord;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.repository.FamilyRecordRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.service.admin.FamilyRecordService;

@Service
public class FamilyRecordServiceImpl implements FamilyRecordService{
	private static final Logger logger = LoggerFactory.getLogger(FamilyRecordServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FamilyRecordRepository familyRecordRepository;
	
	@Override
	public ServiceResponse<Boolean> create() {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String getUser = authentication.getName();
			
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
			if(!currUser.isPresent()) {
				return new ServiceResponse<Boolean>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, false);
			}
			
			FamilyRecord family = new FamilyRecord();
			family.setId(StringUtils.generateTransactionId());
			family.setNumberOfMembers(Constants.IS_EMPTY);
			family.setStatus(Constants.IS_ACTIVATE);
			family.setIsDeleted(Constants.NOT_DELETED);
			family.setCreatedDate(new Date());
			family.setCreatedUser(getUser);
			
			
			familyRecordRepository.save(family);
			return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
		}
	}

}
