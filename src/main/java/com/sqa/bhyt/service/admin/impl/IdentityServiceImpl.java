package com.sqa.bhyt.service.admin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sqa.bhyt.common.constants.Constants;
import com.sqa.bhyt.common.constants.MessageCode;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.common.ultis.DateUtils;
import com.sqa.bhyt.dto.request.identity.IdentityByFamilyInfo;
import com.sqa.bhyt.dto.request.identity.IdentityByFamilyRequest;
import com.sqa.bhyt.dto.request.identity.IdentityInfoRequest;
import com.sqa.bhyt.dto.response.identity.IdentityInfoResponse;
import com.sqa.bhyt.entity.Districts;
import com.sqa.bhyt.entity.FamilyRecord;
import com.sqa.bhyt.entity.Identity;
import com.sqa.bhyt.entity.Provinces;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.entity.Wards;
import com.sqa.bhyt.repository.DistrictRepository;
import com.sqa.bhyt.repository.FamilyRecordRepository;
import com.sqa.bhyt.repository.IdentityRepository;
import com.sqa.bhyt.repository.ProvinceRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.repository.WardsRepository;
import com.sqa.bhyt.service.admin.IdentityService;

@Service
public class IdentityServiceImpl implements IdentityService{
	private static final Logger logger = LoggerFactory.getLogger(IdentityServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IdentityRepository identityRepository;
	
	@Autowired
	private FamilyRecordRepository familyRecordRepository;
	
	@Autowired
	private WardsRepository wardsRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Override
	public ServiceResponse<IdentityInfoResponse> create(IdentityInfoRequest request) {
		try {
			if(!StringUtils.hasText(request.getCCCD())) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.CCCD_NOT_EMPTY, MessageCode.CCCD_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(DateUtils.dateToStringStartWithDDMMYYY(request.getBirthDay()))) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.BIRTHDAY_NOT_EMPTY, MessageCode.BIRTHDAY_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getNationality())) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.NATIONALITY_NOT_EMPTY, MessageCode.NATIONALITY_NOT_EMPTY_MESSGAE, null);
			}
			
			if(!StringUtils.hasText(request.getPlaceOfOrigin())) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.ORIGIN_PLACE_NOT_EMPTY, MessageCode.ORIGIN_PLACE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getPlaceOfResidence())) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.RESIDENCE_PLACE_NOT_EMPTY, MessageCode.RESIDENCE_PLACE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getPersionalIdentification())) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.PERSIONAL_IDENTITY_NOT_EMPTY, MessageCode.PERSIONAL_IDENTITY_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getWardsCode())) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.WARDS_CODE_NOT_EMPTY, MessageCode.WARDS_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getDistrictCode())) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.DISTRICT_CODE_NOT_EMPTY, MessageCode.DISTRICT_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getProvinceCode())) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.PROVINE_CODE_NOT_EMPTY, MessageCode.PROVINE_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(identityRepository.existsByCccdAndIsDeleted(request.getCCCD(), Constants.NOT_DELETED)) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.PERSON_EXISTED, MessageCode.PERSON_EXISTED_MESSAGE, null);
			}
			

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String getUser = authentication.getName();
			
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Optional<Wards> wards = wardsRepository.findByCode(request.getWardsCode());
			if(!wards.isPresent()) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.WARDS_NOT_FOUND, MessageCode.WARDS_NOT_FOUND_MESSAGE, null);
			}
			
			Optional<Districts> district = districtRepository.findByCode(request.getDistrictCode());
			if(!district.isPresent()) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.DISTRICT_NOT_FOUND, MessageCode.DISTRICT_NOT_FOUND_MESSAGE, null);
			}
			
			Optional<Provinces> province = provinceRepository.findByCode(request.getProvinceCode());
			if(!province.isPresent()) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.PROVINE_NOT_FOUND, MessageCode.PROVINE_NOT_FOUND_MESSAGE, null);
			}
			
			Optional<FamilyRecord> family = familyRecordRepository.findByIdAndIsDeleted(request.getFamilyRecordId(), Constants.NOT_DELETED);
			if(!family.isPresent()) {
				return new ServiceResponse<IdentityInfoResponse>(MessageCode.FAMILY_NOT_FOUND, MessageCode.FAMILY_NOT_FOUND_MESSAGE, null);
			}
			
			
			Identity identity = new Identity();
			identity.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			identity.setCccd(request.getCCCD());
			identity.setName(request.getName());
			identity.setBirthday(request.getBirthDay());
			identity.setGender(request.getGender());
			identity.setNationality(request.getNationality());
			identity.setPlaceOfOrigin(request.getPlaceOfOrigin());
			identity.setPlaceOfResidence(request.getPlaceOfResidence());
			identity.setPersionalIdentification(request.getPersionalIdentification());
			identity.setHouseHold(request.getIsHousehold());
			identity.setCreatedDate(new Date());
			identity.setCreatedUser(getUser);
			identity.setStatus(Constants.IS_ACTIVATE);
			identity.setIsDeleted(Constants.NOT_DELETED);
			identity.setWardIdentity(wards.get());
			identity.setDistrictIdentity(district.get());
			identity.setProvinceIdentity(province.get());
			
			family.get().setNumberOfMembers(family.get().getNumberOfMembers() + 1);
			family.get().setUpdatedDate(new Date());
			family.get().setCreatedUser(getUser);
			familyRecordRepository.save(family.get());
			
			identity.setFamily(family.get());
			identityRepository.save(identity);
			
			IdentityInfoResponse response = new IdentityInfoResponse();
			response.setCCCD(identity.getCccd());
			response.setName(identity.getName());
			response.setBirthDay(identity.getBirthday());
			response.setGender(identity.getGender());
			response.setNationality(identity.getNationality());
			response.setPlaceOfOrigin(identity.getPlaceOfOrigin());
			response.setPlaceOfResidence(identity.getPlaceOfResidence());
			response.setPersionalIdentification(identity.getPersionalIdentification());
			response.setFamilyRecordId(identity.getFamily().getId());
			
			return new ServiceResponse<IdentityInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<IdentityInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
		
	}

	@Transactional
	@Override
	public ServiceResponse<List<IdentityInfoResponse>> familyCreate(IdentityByFamilyRequest request) {
		try {
			if(!StringUtils.hasText(request.getWardsCode())) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.WARDS_CODE_NOT_EMPTY, MessageCode.WARDS_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getDistrictCode())) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.DISTRICT_CODE_NOT_EMPTY, MessageCode.DISTRICT_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getProvinceCode())) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.PROVINE_CODE_NOT_EMPTY, MessageCode.PROVINE_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			Optional<Wards> wards = wardsRepository.findByCode(request.getWardsCode());
			if(!wards.isPresent()) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.WARDS_NOT_FOUND, MessageCode.WARDS_NOT_FOUND_MESSAGE, null);
			}
			
			Optional<Districts> district = districtRepository.findByCode(request.getDistrictCode());
			if(!district.isPresent()) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.DISTRICT_NOT_FOUND, MessageCode.DISTRICT_NOT_FOUND_MESSAGE, null);
			}
			
			Optional<Provinces> province = provinceRepository.findByCode(request.getProvinceCode());
			if(!province.isPresent()) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.PROVINE_NOT_FOUND, MessageCode.PROVINE_NOT_FOUND_MESSAGE, null);
			}
			
			int householdCount = 0;
			for(IdentityByFamilyInfo i : request.getMembers()) {
				if(i.getIsHousehold()) {
					householdCount++;
				}
			}
			
			if(householdCount == 0) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.HOUSEHOLD_NOT_EMPTY, MessageCode.HOUSEHOLD_NOT_EMPTY_MESSAGE, null);
			}
			
			if(householdCount > 1) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.HOUSEHOLD_INVALID, MessageCode.HOUSEHOLD_INVALID_MESSAGE, null);
			}
			
			
			if(request.getMembers().size() == 0) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String getUser = authentication.getName();
			
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Optional<FamilyRecord> family = familyRecordRepository.findByIdAndIsDeleted(request.getFamilyId(), Constants.NOT_DELETED);
			if(!family.isPresent()) {
				return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.FAMILY_NOT_FOUND, MessageCode.FAMILY_NOT_FOUND_MESSAGE, null);
			}
			
			List<Identity> list = new ArrayList<>();
			List<IdentityInfoResponse> responses = new ArrayList<>();
			
			
			for(IdentityByFamilyInfo i : request.getMembers()) {
				if(!StringUtils.hasText(i.getCCCD())) {
					return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.CCCD_NOT_EMPTY, MessageCode.CCCD_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(i.getName())) {
					return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(DateUtils.dateToStringStartWithDDMMYYY(i.getBirthDay()))) {
					return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.BIRTHDAY_NOT_EMPTY, MessageCode.BIRTHDAY_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(i.getNationality())) {
					return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.NATIONALITY_NOT_EMPTY, MessageCode.NATIONALITY_NOT_EMPTY_MESSGAE, null);
				}
				
				if(!StringUtils.hasText(i.getPlaceOfOrigin())) {
					return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.ORIGIN_PLACE_NOT_EMPTY, MessageCode.ORIGIN_PLACE_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(i.getPlaceOfResidence())) {
					return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.RESIDENCE_PLACE_NOT_EMPTY, MessageCode.RESIDENCE_PLACE_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(i.getPersionalIdentification())) {
					return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.PERSIONAL_IDENTITY_NOT_EMPTY, MessageCode.PERSIONAL_IDENTITY_NOT_EMPTY_MESSAGE, null);
				}
					
				if(identityRepository.existsByCccdAndIsDeleted(i.getCCCD(), Constants.NOT_DELETED)) {
					return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.PERSON_EXISTED, MessageCode.PERSON_EXISTED_MESSAGE, null);
				}
				
	
				Identity identity = new Identity();
				identity.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
				identity.setCccd(i.getCCCD());
				identity.setName(i.getName());
				identity.setBirthday(i.getBirthDay());
				identity.setGender(i.getGender());
				identity.setNationality(i.getNationality());
				identity.setPlaceOfOrigin(i.getPlaceOfOrigin());
				identity.setPlaceOfResidence(i.getPlaceOfResidence());
				identity.setPersionalIdentification(i.getPersionalIdentification());
				identity.setHouseHold(i.getIsHousehold());
				identity.setCreatedDate(new Date());
				identity.setCreatedUser(getUser);
				identity.setStatus(Constants.IS_ACTIVATE);
				identity.setIsDeleted(Constants.NOT_DELETED);
				identity.setWardIdentity(wards.get());
				identity.setDistrictIdentity(district.get());
				identity.setProvinceIdentity(province.get());
				
				family.get().setNumberOfMembers(family.get().getNumberOfMembers() + 1);
				family.get().setUpdatedDate(new Date());
				family.get().setCreatedUser(getUser);
//				familyRecordRepository.save(family.get());
				
				identity.setFamily(family.get());
				
				list.add(identity);
				
				IdentityInfoResponse response = new IdentityInfoResponse();
				response.setCCCD(identity.getCccd());
				response.setName(identity.getName());
				response.setBirthDay(identity.getBirthday());
				response.setGender(identity.getGender());
				response.setNationality(identity.getNationality());
				response.setPlaceOfOrigin(identity.getPlaceOfOrigin());
				response.setPlaceOfResidence(identity.getPlaceOfResidence());
				response.setPersionalIdentification(identity.getPersionalIdentification());
				response.setFamilyRecordId(identity.getFamily().getId());
				response.setHousehold(identity.isHouseHold());
				responses.add(response);
			}
			familyRecordRepository.save(family.get());
			identityRepository.saveAll(list);
			return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, responses);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<IdentityInfoResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}


	
}
