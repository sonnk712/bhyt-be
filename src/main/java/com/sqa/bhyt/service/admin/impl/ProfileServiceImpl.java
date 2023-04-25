package com.sqa.bhyt.service.admin.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import com.sqa.bhyt.dto.request.profile.ProfileDetail;
import com.sqa.bhyt.dto.request.profile.TypeProfileFilter;
import com.sqa.bhyt.dto.request.profile.PeriodProfileFilter;
import com.sqa.bhyt.dto.response.profile.ProfileDetailResponse;
import com.sqa.bhyt.dto.response.profile.ProfileFilterResponse;
import com.sqa.bhyt.dto.response.profile.ProfileInfoResponse;
import com.sqa.bhyt.dto.response.type.TypeCombobox;
import com.sqa.bhyt.entity.Profile;
import com.sqa.bhyt.entity.Type;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.repository.ProfileRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.service.admin.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService{
	private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	

	@Override
	public ServiceResponse<ProfileInfoResponse> filterByPeriod(PeriodProfileFilter request) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<ProfileInfoResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Date to = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.add(Calendar.MONTH, -request.getMonth());
			Date from = calendar.getTime();
			
			List<Profile> profiles = profileRepository.fitlerByPeriod(request.getTextSearch(), from, to);
			
			if(profiles.size() == 0) {
				return new ServiceResponse<ProfileInfoResponse>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			double totalCost = 0.0;
			List<ProfileFilterResponse> profileInfo = new ArrayList<>();
			for(Profile data : profiles) {
				ProfileFilterResponse dataResponse = new ProfileFilterResponse();
				
				Type type = data.getType();
				TypeCombobox typeData = new TypeCombobox();
				typeData.setId(type.getId());
				typeData.setCode(type.getCode());
				typeData.setName(type.getName());
				
				dataResponse.setContact(currUser.get().getPhoneNumber());
				dataResponse.setCccd(username);
				dataResponse.setId(data.getId());
				dataResponse.setName(data.getName());
				dataResponse.setCost(data.getCost());
				dataResponse.setType(typeData);
				dataResponse.setStatus(data.getStatus());
				dataResponse.setPaidDate(data.getUpdatedDate());
				if(data.getStatus() == Constants.PAID) {
					totalCost += data.getCost();
				}
				profileInfo.add(dataResponse);
			}
			ProfileInfoResponse response = new ProfileInfoResponse();
			response.setProfileInfo(profileInfo);
			response.setTotalCost(totalCost);
			
			return new ServiceResponse<ProfileInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ProfileInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	
	@Override
	public ServiceResponse<ProfileInfoResponse> filterByType(TypeProfileFilter request) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<ProfileInfoResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			
			
			List<Profile> profiles = profileRepository.fitlerByType(request.getTextSearch(), request.getId());
			
			if(profiles.size() == 0) {
				return new ServiceResponse<ProfileInfoResponse>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			double totalCost = 0.0;
			List<ProfileFilterResponse> profileInfo = new ArrayList<>();
			for(Profile data : profiles) {
				ProfileFilterResponse dataResponse = new ProfileFilterResponse();
				
				Type type = data.getType();
				TypeCombobox typeData = new TypeCombobox();
				typeData.setId(type.getId());
				typeData.setCode(type.getCode());
				typeData.setName(type.getName());
				
				dataResponse.setContact(currUser.get().getPhoneNumber());
				dataResponse.setCccd(username);
				dataResponse.setId(data.getId());
				dataResponse.setName(data.getName());
				dataResponse.setCost(data.getCost());
				dataResponse.setType(typeData);
				dataResponse.setStatus(data.getStatus());
				dataResponse.setPaidDate(data.getUpdatedDate());
				if(data.getStatus() == Constants.PAID) {
					totalCost += data.getCost();
				}
				profileInfo.add(dataResponse);
			}
			
			ProfileInfoResponse response = new ProfileInfoResponse();
			response.setProfileInfo(profileInfo);
			response.setTotalCost(totalCost);
			
			
			return new ServiceResponse<ProfileInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ProfileInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}
	
	@Override
	public ServiceResponse<ProfileDetailResponse> detail(ProfileDetail request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<ProfileInfoResponse> getAll() {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<ProfileInfoResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Double totalCost = 0.0;
			List<Profile> profiles = profileRepository.findAll();
			List<ProfileFilterResponse> profileInfo = new ArrayList<>();
			for(Profile data : profiles) {
				ProfileFilterResponse dataResponse = new ProfileFilterResponse();
				
				Type type = data.getType();
				TypeCombobox typeData = new TypeCombobox();
				typeData.setId(type.getId());
				typeData.setCode(type.getCode());
				typeData.setName(type.getName());
				
				dataResponse.setContact(currUser.get().getPhoneNumber());
				dataResponse.setCccd(username);
				dataResponse.setId(data.getId());
				dataResponse.setName(data.getName());
				dataResponse.setCost(data.getCost());
				dataResponse.setType(typeData);
				dataResponse.setStatus(data.getStatus());
				dataResponse.setPaidDate(data.getUpdatedDate());
				if(data.getStatus() == Constants.PAID) {
					totalCost += data.getCost();
				}
				profileInfo.add(dataResponse);
			}
			ProfileInfoResponse response = new ProfileInfoResponse();
			response.setProfileInfo(profileInfo);
			response.setTotalCost(totalCost);
			
			return new ServiceResponse<ProfileInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ProfileInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}	
	}


	@Override
	public ServiceResponse<ProfileInfoResponse> filterByStatus(TypeProfileFilter request) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<ProfileInfoResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			
			
			List<Profile> profiles = profileRepository.fitlerByStatus(request.getTextSearch(), request.getId());
			
			if(profiles.size() == 0) {
				return new ServiceResponse<ProfileInfoResponse>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			double totalCost = 0.0;
			List<ProfileFilterResponse> profileInfo = new ArrayList<>();
			for(Profile data : profiles) {
				ProfileFilterResponse dataResponse = new ProfileFilterResponse();
				
				Type type = data.getType();
				TypeCombobox typeData = new TypeCombobox();
				typeData.setId(type.getId());
				typeData.setCode(type.getCode());
				typeData.setName(type.getName());
				
				dataResponse.setContact(currUser.get().getPhoneNumber());
				dataResponse.setCccd(username);
				dataResponse.setId(data.getId());
				dataResponse.setName(data.getName());
				dataResponse.setCost(data.getCost());
				dataResponse.setType(typeData);
				dataResponse.setStatus(data.getStatus());
				dataResponse.setPaidDate(data.getUpdatedDate());
				if(data.getStatus() == Constants.PAID) {
					totalCost += data.getCost();
				}
				profileInfo.add(dataResponse);
			}
			
			ProfileInfoResponse response = new ProfileInfoResponse();
			response.setProfileInfo(profileInfo);
			response.setTotalCost(totalCost);
			
			
			return new ServiceResponse<ProfileInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ProfileInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}
}
