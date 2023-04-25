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
import com.sqa.bhyt.dto.request.agency.AgencyDetail;
import com.sqa.bhyt.dto.request.agency.AgencyFilter;
import com.sqa.bhyt.dto.request.agency.AgencyInfoRequest;
import com.sqa.bhyt.dto.request.agency.AgencyUpdateRequest;
import com.sqa.bhyt.dto.response.agency.AgencyDetailResponse;
import com.sqa.bhyt.dto.response.agency.AgencyFilterResponse;
import com.sqa.bhyt.dto.response.agency.AgencyInfoResponse;
import com.sqa.bhyt.dto.response.place.DistrictCombobox;
import com.sqa.bhyt.dto.response.place.ProvinceCombobox;
import com.sqa.bhyt.dto.response.place.WardCombobox;
import com.sqa.bhyt.entity.Agency;
import com.sqa.bhyt.entity.Districts;
import com.sqa.bhyt.entity.Provinces;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.entity.Wards;
import com.sqa.bhyt.repository.AgencyRepository;
import com.sqa.bhyt.repository.DistrictRepository;
import com.sqa.bhyt.repository.ProvinceRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.repository.WardsRepository;
import com.sqa.bhyt.service.admin.AgencyService;

@Service
public class AgencyServiceImpl implements AgencyService{
	private static final Logger logger = LoggerFactory.getLogger(AgencyServiceImpl.class);
	
	@Autowired
	private AgencyRepository agencyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WardsRepository wardsRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Override
	public ServiceResponse<AgencyInfoResponse> create(AgencyInfoRequest request) {
		try {
			if(!StringUtils.hasText(request.getCode())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.CODE_NOT_EMPTY, MessageCode.CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getReceivingPlace())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.RECEIVING_PLACE_NOT_EMPTY, MessageCode.RECEIVING_PLACE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getPerson())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.PERSON_NOT_EMPTY, MessageCode.PERSON_NOT_EMPTY_MESSAGE, null);
			}
			
			String reg = "^(0|\\+84)(\\s|\\.)?(([1-9][2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
			boolean validPhoneNumeber = request.getPhoneNumber().matches(reg);
			
			if(!StringUtils.hasText(request.getPhoneNumber())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.PHONENUMBER_NOT_EMPTY, MessageCode.PHONENUMBER_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!validPhoneNumeber) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.PHONE_NUMBER_INVALID,
						MessageCode.PHONE_NUMBER_INVALID_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getAddress())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.ADDRESS_NOT_EMPTY, MessageCode.ADDRESS_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getDistrictCode())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.DISTRICT_CODE_NOT_EMPTY, MessageCode.DISTRICT_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getWardCode())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.WARDS_CODE_NOT_EMPTY, MessageCode.WARDS_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(agencyRepository.existsByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED)) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.AGENCY_EXISTED, MessageCode.AGENCY_EXISTED_MESSAGE, null);
			}
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Optional<Wards> ward = wardsRepository.findByCode(request.getWardCode());
			if(!ward.isPresent()) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.WARDS_NOT_FOUND, MessageCode.WARDS_NOT_FOUND_MESSAGE, null);
			}
			WardCombobox wardResponse = new WardCombobox();
			wardResponse.setCode(ward.get().getCode());
			wardResponse.setCodeName(ward.get().getCodeName());
			wardResponse.setName(ward.get().getName());
			wardResponse.setFullName(ward.get().getFullName());
			
			Optional<Districts> district = districtRepository.findByCode(request.getDistrictCode());
			if(!district.isPresent()) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.DISTRICT_NOT_FOUND, MessageCode.DISTRICT_NOT_FOUND_MESSAGE, null);
			}
			
			System.out.println(district.get().getCodeName());
			System.out.println(ward.get().getCodeName());
			DistrictCombobox districtResponse = new DistrictCombobox();
			districtResponse.setCode(district.get().getCode());
			districtResponse.setCodeName(district.get().getCodeName());
			districtResponse.setName(district.get().getName());
			districtResponse.setFullName(district.get().getFullName());
			
			
			Agency agency = new Agency();
			agency.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			agency.setCode(request.getCode());
			agency.setName(request.getName());
			agency.setReceivingPlace(request.getReceivingPlace());
			agency.setPerson(request.getPerson());
			agency.setPhoneNumber(request.getPhoneNumber());
			agency.setAddress(request.getAddress());
			agency.setWardAgency(ward.get());
			agency.setDistrictAgency(district.get());
			agency.setStatus(Constants.IS_ACTIVATE);
			agency.setIsDeleted(Constants.NOT_DELETED);
			agency.setCreatedDate(new Date());
			agency.setCreatedUser(username);
			
			agencyRepository.save(agency);
			
			Optional<Agency> lastCheck = agencyRepository.findByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED);
			if(!lastCheck.isPresent()) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.CREATE_PERIOD_FAILED, MessageCode.CREATE_PERIOD_FAILED_MESSAGE, null);
			}
			
			AgencyInfoResponse response = new AgencyInfoResponse();
			response.setCode(request.getCode());
			response.setName(request.getName());
			response.setReceivingPlace(request.getReceivingPlace());
			response.setPerson(request.getPerson());
			response.setPhoneNumber(request.getPhoneNumber());
			response.setAddress(request.getAddress());
			
			return new ServiceResponse<AgencyInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<AgencyInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<AgencyInfoResponse> update(AgencyUpdateRequest request) {
		try {
			
			if(!StringUtils.hasText(request.getId())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.IDENTITY_NOT_EMPTY, MessageCode.IDENTITY_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getCode())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.CODE_NOT_EMPTY, MessageCode.CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getName())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.NAME_NOT_EMPTY, MessageCode.NAME_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getReceivingPlace())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.RECEIVING_PLACE_NOT_EMPTY, MessageCode.RECEIVING_PLACE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getPerson())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.PERSON_NOT_EMPTY, MessageCode.PERSON_NOT_EMPTY_MESSAGE, null);
			}
			
			String reg = "^(0|\\+84)(\\s|\\.)?(([1-9][2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
			boolean validPhoneNumeber = request.getPhoneNumber().matches(reg);
			
			if(!StringUtils.hasText(request.getPhoneNumber())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.PHONENUMBER_NOT_EMPTY, MessageCode.PHONENUMBER_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!validPhoneNumeber) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.PHONE_NUMBER_INVALID,
						MessageCode.PHONE_NUMBER_INVALID_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getAddress())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.ADDRESS_NOT_EMPTY, MessageCode.ADDRESS_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getDistrictCode())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.DISTRICT_CODE_NOT_EMPTY, MessageCode.DISTRICT_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getWardCode())) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.WARDS_CODE_NOT_EMPTY, MessageCode.WARDS_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(agencyRepository.existsByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED)) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.PERIOD_EXISTED, MessageCode.PERIOD_EXISTED_MESSAGE, null);
			}
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Optional<Wards> ward = wardsRepository.findByCode(request.getWardCode());
			if(!ward.isPresent()) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.WARDS_NOT_FOUND, MessageCode.WARDS_NOT_FOUND_MESSAGE, null);
			}
			WardCombobox wardResponse = new WardCombobox();
			wardResponse.setCode(ward.get().getCode());
			wardResponse.setCodeName(ward.get().getCodeName());
			wardResponse.setName(ward.get().getName());
			wardResponse.setFullName(ward.get().getFullName());
			
			Optional<Districts> district = districtRepository.findByCode(request.getDistrictCode());
			if(!district.isPresent()) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.DISTRICT_NOT_FOUND, MessageCode.DISTRICT_NOT_FOUND_MESSAGE, null);
			}
			
			DistrictCombobox districtResponse = new DistrictCombobox();
			districtResponse.setCode(district.get().getCode());
			districtResponse.setCodeName(district.get().getCodeName());
			districtResponse.setName(district.get().getName());
			districtResponse.setFullName(district.get().getFullName());
			
			Optional<Agency> agency = agencyRepository.findByIdAndIsDeleted(request.getId(), Constants.NOT_DELETED);
			if(!agency.isPresent()) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.AGENCY_NOT_FOUND, MessageCode.AGENCY_NOT_FOUND_MESSAGE, null);
			}
			
			if(!agency.get().getCode().equals(request.getCode())) {
				if(agencyRepository.existsByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED)) {
					return new ServiceResponse<AgencyInfoResponse>(MessageCode.PERIOD_EXISTED, MessageCode.PERIOD_EXISTED_MESSAGE, null);
				}
				else {
					agency.get().setCode(request.getCode());
				}
			}
			
			if(!agency.get().getName().equals(request.getName())) {
				agency.get().setName(request.getName());
			}
			
			if(!agency.get().getReceivingPlace().equals(request.getReceivingPlace())) {
				agency.get().setReceivingPlace(request.getReceivingPlace());
			}
			
			if(!agency.get().getPerson().equals(request.getPerson())) {
				agency.get().setPerson(request.getPerson());
			}
			
			if(!agency.get().getPhoneNumber().equals(request.getPhoneNumber())) {
				agency.get().setPhoneNumber(request.getPhoneNumber());
			}
			
			if(!agency.get().getAddress().equals(request.getAddress())) {
				agency.get().setAddress(request.getAddress());
			}
			

			if(!ward.get().getCode().equals(request.getWardCode())) {
				agency.get().setWardAgency(ward.get());
			}
			
			if(!district.get().getCode().equals(request.getDistrictCode())) {
				agency.get().setDistrictAgency(district.get());
			}

			agency.get().setCreatedDate(new Date());
			agency.get().setCreatedUser(username);
			
			agencyRepository.save(agency.get());
			
			Optional<Agency> lastCheck = agencyRepository.findByCodeAndIsDeleted(request.getCode(), Constants.NOT_DELETED);
			if(!lastCheck.isPresent()) {
				return new ServiceResponse<AgencyInfoResponse>(MessageCode.CREATE_PERIOD_FAILED, MessageCode.CREATE_PERIOD_FAILED_MESSAGE, null);
			}
			
			AgencyInfoResponse response = new AgencyInfoResponse();
			response.setCode(request.getCode());
			response.setName(request.getName());
			response.setReceivingPlace(request.getReceivingPlace());
			response.setPerson(request.getPerson());
			response.setPhoneNumber(request.getPhoneNumber());
			response.setAddress(request.getAddress());
			
			return new ServiceResponse<AgencyInfoResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<AgencyInfoResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Override
	public ServiceResponse<Boolean> delete(DeleteRequest request) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<Boolean>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, false);
			}
			
			Set<Agency> agencies = new HashSet<Agency>();
			for(String id: request.getListId()) {
				Optional<Agency> agency = agencyRepository.findByIdAndIsDeleted(id, Constants.NOT_DELETED);
				if(!agency.isPresent()) {
					return new ServiceResponse<Boolean>(MessageCode.EXISTED_INVALID_AGENCY, MessageCode.EXISTED_INVALID_AGENCY_MESSAGE, false);
				}
				agencies.add(agency.get());
			}
			
			for(Agency i : agencies) {
				i.setIsDeleted(Constants.IS_DELETED);
			}
			agencyRepository.saveAll(agencies);
			return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
		}
	}

	@Override
	public ServiceResponse<List<AgencyFilterResponse>> filter(AgencyFilter request) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<List<AgencyFilterResponse>>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getDistrictCode())) {
				return new ServiceResponse<List<AgencyFilterResponse>>(MessageCode.DISTRICT_CODE_NOT_EMPTY, MessageCode.DISTRICT_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			Optional<Districts> district = districtRepository.findByCode(request.getDistrictCode());
			if(!district.isPresent()) {
				return new ServiceResponse<List<AgencyFilterResponse>>(MessageCode.DISTRICT_NOT_FOUND, MessageCode.DISTRICT_NOT_FOUND_MESSAGE, null);
			}
			
			List<AgencyFilterResponse> response = new ArrayList<>();
			List<Agency> agencies = new ArrayList<>();
			if(request.getWardCode().equals("000")) {
				agencies = agencyRepository.filterByDistrict(request.getTextSearch() ,district.get().getCode(), Constants.NOT_DELETED);
			}
			else {
				Optional<Wards> ward = wardsRepository.findByCode(request.getWardCode());
				if(!ward.isPresent()) {
					return new ServiceResponse<List<AgencyFilterResponse>>(MessageCode.WARDS_NOT_FOUND, MessageCode.WARDS_NOT_FOUND_MESSAGE, null);
				}
				agencies = agencyRepository.filterByDistrictAndWard(request.getTextSearch() ,district.get().getCode(),
						ward.get().getCode() ,Constants.NOT_DELETED);
			}
			
			for(Agency data : agencies) {
				AgencyFilterResponse dataResponse = new AgencyFilterResponse();
				dataResponse.setId(data.getId());
				dataResponse.setCode(data.getCode());
				dataResponse.setName(data.getName());
				dataResponse.setReceivingPlace(data.getReceivingPlace());
				dataResponse.setPerson(data.getPerson());
				dataResponse.setPhoneNumber(data.getPhoneNumber());
				dataResponse.setAddress(data.getAddress());
				dataResponse.setStatus(data.getStatus());
				response.add(dataResponse);
			}
			
			return new ServiceResponse<List<AgencyFilterResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<AgencyFilterResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}	
	}

	@Override
	public ServiceResponse<Boolean> combobox(DeleteRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResponse<AgencyDetailResponse> detail(AgencyDetail request) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<AgencyDetailResponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			Optional<Agency> agency = agencyRepository.findByIdAndIsDeleted(request.getId(), Constants.NOT_DELETED);
			if(!agency.isPresent()) {
				return new ServiceResponse<AgencyDetailResponse>(MessageCode.AGENCY_NOT_FOUND, MessageCode.AGENCY_NOT_FOUND_MESSAGE, null);
			}
			
			Wards ward = agency.get().getWardAgency();
			WardCombobox wardResponse = new WardCombobox();
			wardResponse.setCode(ward.getCode());
			wardResponse.setCodeName(ward.getCodeName());
			wardResponse.setName(ward.getName());
			wardResponse.setFullName(ward.getFullName());
			
			Districts district = agency.get().getDistrictAgency();
			DistrictCombobox districtResponse = new DistrictCombobox();
			districtResponse.setCode(district.getCode());
			districtResponse.setCodeName(district.getCodeName());
			districtResponse.setName(district.getName());
			districtResponse.setFullName(district.getFullName());
			
			Optional<Provinces> province = provinceRepository.findByDistrict(agency.get().getDistrictAgency().getCode());
			ProvinceCombobox provinceResponse = new ProvinceCombobox();
	
			provinceResponse.setCode(province.get().getCode());
			provinceResponse.setCodeName(province.get().getCodeName());
			provinceResponse.setName(province.get().getName());
			provinceResponse.setFullName(province.get().getFullName());
			
			AgencyDetailResponse response = new AgencyDetailResponse();
			response.setCode(agency.get().getCode());
			response.setName(agency.get().getName());
			response.setReceivingPlace(agency.get().getReceivingPlace());
			response.setPerson(agency.get().getPerson());
			response.setPhoneNumber(agency.get().getPhoneNumber());
			response.setAddress(agency.get().getAddress());
			response.setStatus(agency.get().getStatus());
			response.setCreatedDate(agency.get().getCreatedDate());
			response.setUpdatedDate(agency.get().getUpdatedDate());
			response.setDistrict(districtResponse);
			response.setWard(wardResponse);
			response.setProvince(provinceResponse);
			
			return new ServiceResponse<AgencyDetailResponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<AgencyDetailResponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}	
	}

	@Override
	public ServiceResponse<List<AgencyFilterResponse>> getAll() {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_DELETED);
			
			if(!currUser.isPresent()) {
				return new ServiceResponse<List<AgencyFilterResponse>>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
			}
			
			List<Agency> agencies = agencyRepository.findAll(Constants.NOT_DELETED);
			List<AgencyFilterResponse> response = new ArrayList<>();
			for(Agency data : agencies) {
				AgencyFilterResponse dataResponse = new AgencyFilterResponse();
				dataResponse.setId(data.getId());
				dataResponse.setCode(data.getCode());
				dataResponse.setName(data.getName());
				dataResponse.setReceivingPlace(data.getReceivingPlace());
				dataResponse.setPerson(data.getPerson());
				dataResponse.setPhoneNumber(data.getPhoneNumber());
				dataResponse.setAddress(data.getAddress());
				dataResponse.setStatus(data.getStatus());
				response.add(dataResponse);
			}
			
			return new ServiceResponse<List<AgencyFilterResponse>>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<AgencyFilterResponse>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}	
	}

}
