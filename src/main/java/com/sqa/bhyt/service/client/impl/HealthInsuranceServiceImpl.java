package com.sqa.bhyt.service.client.impl;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.sqa.bhyt.dto.request.insurance.FamilyProfileInfo;
import com.sqa.bhyt.dto.request.insurance.FamilyProfileRequest;
import com.sqa.bhyt.dto.request.insurance.OrginizationProfileInfo;
import com.sqa.bhyt.dto.request.insurance.OrginizationProfileRequest;
import com.sqa.bhyt.dto.request.insurance.PaidBySocialInsuranceRequet;
import com.sqa.bhyt.dto.request.insurance.PaidByStateBudgetRequest;
import com.sqa.bhyt.dto.request.insurance.PaymentInfoRequest;
import com.sqa.bhyt.dto.request.insurance.PersonalProfileRequest;
import com.sqa.bhyt.dto.response.profile.ProfileInformationInfo;
import com.sqa.bhyt.dto.response.profile.ProfileReponse;
import com.sqa.bhyt.entity.ChargeUnit;
import com.sqa.bhyt.entity.HealthInsurance;
import com.sqa.bhyt.entity.Identity;
import com.sqa.bhyt.entity.Period;
import com.sqa.bhyt.entity.Profile;
import com.sqa.bhyt.entity.ProfileInformation;
import com.sqa.bhyt.entity.Type;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.repository.ChargeUnitRepository;
import com.sqa.bhyt.repository.DistrictRepository;
import com.sqa.bhyt.repository.HealthInsuranceRepository;
import com.sqa.bhyt.repository.IdentityRepository;
import com.sqa.bhyt.repository.PeriodRepository;
import com.sqa.bhyt.repository.ProfileInfoRepository;
import com.sqa.bhyt.repository.ProfileRepository;
import com.sqa.bhyt.repository.TypeRepository;
import com.sqa.bhyt.repository.UserRepository;
import com.sqa.bhyt.repository.WardsRepository;
import com.sqa.bhyt.service.client.HealthInsuranceService;

@Service
public class HealthInsuranceServiceImpl implements HealthInsuranceService{
	private static final Logger logger = LoggerFactory.getLogger(HealthInsuranceServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HealthInsuranceRepository healthInsuranceRepository;
	
	@Autowired
	IdentityRepository identityRepository;
	
	@Autowired
	TypeRepository typeRepository;
	
	@Autowired
	PeriodRepository periodRepository;
	
	@Autowired
	ChargeUnitRepository chargeUnitRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	ProfileInfoRepository profileInfoRepository;
	
	@Autowired
	DistrictRepository districtRepository;
	
	@Autowired
	WardsRepository wardsRepository;
	
	@Transactional
	@Override
	public ServiceResponse<ProfileReponse> createFamilyProfile(FamilyProfileRequest request) {
		try {
			
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			String getUser = authentication.getName();
//			
//			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
//			if(!currUser.isPresent()) {
//				return new ServiceResponse<ProfileReponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
//			}
//			
//			if(currUser.get().getStatus() == Constants.NOT_ACTIVATE) {
//				return new ServiceResponse<ProfileReponse>(MessageCode.ACCOUNT_IS_NOT_VERIFY, MessageCode.ACCOUNT_IS_NOT_VERIFY_MESSAGE, null);
//			}
			
			
			
			if(!StringUtils.hasText(request.getTypeCode())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.TYPE_ID_NOT_EMPTY, MessageCode.TYPE_ID_NOT_EMPTY_MESSAGE, null);
			}		
			
			if(!StringUtils.hasText(request.getChargeUnitCode())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.CHARGE_UNIT_ID_NOT_EMPTY, MessageCode.CHARGE_UNIT_ID_NOT_EMPTY_MESSAGE, null);
			}
			
			if(request.getMembers().size() == Constants.IS_EMPTY) {
				return new ServiceResponse<ProfileReponse>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			//Kiểm tra xem đầu vào có 2 thành viên nào trùng không
			for(FamilyProfileInfo i : request.getMembers()) {
				int cntDuplicated = 0;
				for(FamilyProfileInfo j : request.getMembers()) {
					if(i.getCccd().equals(j.getCccd())){
						cntDuplicated++;
					}
				}
				if(cntDuplicated != 1) {
					return new ServiceResponse<ProfileReponse>(MessageCode.EXISTED_MEMBER_IS_DUPLICATED, MessageCode.EXISTED_MEMBER_IS_DUPLICATED_MESSAGE, null);
				}
			}
			
			Optional<Type> type = typeRepository.findByCodeAndIsDeleted(request.getTypeCode(), Constants.NOT_DELETED);
			if(!type.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.TYPE_NOT_FOUND, MessageCode.TYPE_NOT_FOUND_MESSAGE, null);
			}
			
			if(type.get().getCode().equals(Constants.FAMILY_TYPE)) {
				//Nếu đóng theo hộ gia đình
				
				FamilyProfileInfo householdRequest = request.getMembers().get(Constants.FIRST_ITEM);
				
				//Người đầu trong danh sách là chủ hộ
				Optional<Identity> household = identityRepository.findByCccdAndIsHouseHoldAndIsDeleted(householdRequest.getCccd(), 
						Constants.IS_HOUSEHOLD, Constants.NOT_DELETED);
				if(!household.isPresent()) {
					return new ServiceResponse<ProfileReponse>(MessageCode.HOUSEHOLD_NOT_FOUND, MessageCode.HOUSEHOLD_NOT_FOUND_MESSAGE, null);
				}
				
				//Lấy danh sách gia đình từ mã sổ hộ khẩu
				List<Identity> identities = identityRepository.findByFamilyAndIsDeleted(household.get().getFamily().getId(), Constants.NOT_DELETED);
				
				
				//Kiểm tra các thành viên trong request có đúng mã không
				int checkMembers = 0;
				for(FamilyProfileInfo member : request.getMembers()) {
					for(Identity i : identities) {
						if(member.getCccd().equals(i.getCccd())) {
							checkMembers++;
							continue;
						}
					}
				}
				
				
				if(checkMembers != request.getMembers().size()) {
					return new ServiceResponse<ProfileReponse>(MessageCode.EXISTED_MEMBER_INVALID, MessageCode.EXISTED_MEMBER_INVALID_MESSAGE, null);
				}
				
				
				
				
				//Kiểm tra xem thành viên đăng ký trong gia đình đã có bảo hiểm y tế chưa
				//Với người đúng đầu trong danh sách (chủ hộ) thì luôn phải ở trong danh sách - làm người đại diện
				for(FamilyProfileInfo member : request.getMembers()) {
					if(!member.getCccd().equals(householdRequest.getCccd())) {
						if(healthInsuranceRepository.existsByCCCDAndIsDeleted(member.getCccd(), Constants.NOT_DELETED) == 1) {
							return new ServiceResponse<ProfileReponse>(MessageCode.INSURANCE_MEMBER_EXISTED, MessageCode.
									INSURANCE_MEMBER_EXISTED_MESSAGE, null);
						}
					}	
				}
				
				//TODO: Trường hợp chủ hộ đăng ký thêm cho thành viên khác (Gia đình đã đăng ký 1 lần trước đó)
				//Kiểm tra chủ hộ đã có bảo hiểm chưa
				Integer checkHouseholdExisted =  healthInsuranceRepository.existsByCCCDAndIsDeleted(household.get().getCccd(), Constants.NOT_DELETED);
				
				Optional<ChargeUnit> salary = chargeUnitRepository.findByCodeAndIsDeleted(request.getChargeUnitCode(), Constants.NOT_DELETED);
				if(!salary.isPresent()) {
					return new ServiceResponse<ProfileReponse>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
				}
				ServiceResponse<Double> totalCost = null;
				//Nếu chưa tiếp tục đăng kí
				//Nếu đã có bảo hiểm rồi, kiểm tra month tương ứng với chủ hộ, nếu vẫn chọn số tháng đăng ký , báo lỗi
				if(checkHouseholdExisted == 1) {
					//TODO: Trường hợp chủ hộ đăng ký thêm thành viên mới
					if(!householdRequest.getPeriodCode().equals(Constants.NOT_SELECTED) || !StringUtils.hasText(householdRequest.getPeriodCode())) {
						return new ServiceResponse<ProfileReponse>(MessageCode.INSURANCE_HOUSEHOLD_EXISTED, MessageCode.
								INSURANCE_HOUSEHOLD_EXISTED_MESSAGE, null);
					}		
	
					totalCost = moreFamilyMembersCharge(request.getMembers(), salary.get().getCost());
					if(totalCost.getData() == null) {
						return new ServiceResponse<ProfileReponse>(MessageCode.CANT_CHARGE_COST, MessageCode.CANT_CHARGE_COST_MESSAGE, null);
					}
				}
				else if(checkHouseholdExisted == 0) {
					//TODO: Trường hợp chủ hộ đăng ký lần đầu cho cả gia đình
					totalCost = familyCharge(request.getMembers(), salary.get().getCost());					
					if(totalCost.getData() == null) {
						return new ServiceResponse<ProfileReponse>(totalCost.getCode(), totalCost.getMessage(), null);
					}
				}
				
				System.out.println(totalCost);
				Profile profile = new Profile();
				
				profile.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
				profile.setCost(totalCost.getData());
				profile.setType(type.get());
				profile.setStatus(Constants.IS_ACTIVATE);
				profile.setIsDeleted(Constants.NOT_DELETED);
				profile.setCreatedDate(new Date());
				profile.setCreatedUser("001201012182");
				
				ProfileReponse response = new ProfileReponse();
				response.setCost(profile.getCost());
				response.setPaidBy("001201012182");
				response.setStatus(Constants.NOT_ACTIVATE);
				
				List<ProfileInformation> members = new ArrayList<>();
				List<HealthInsurance> healthInsurances = new ArrayList<>();
				List<ProfileInformationInfo> profileResponse = new ArrayList<>();
				for (FamilyProfileInfo i : request.getMembers()) {
					
					
					if(!StringUtils.hasText(i.getCccd())) {
						return new ServiceResponse<ProfileReponse>(MessageCode.CCCD_NOT_EMPTY, MessageCode.CCCD_NOT_EMPTY_MESSAGE, null);
					}
					

					if(!StringUtils.hasText(i.getPeriodCode())) {
						return new ServiceResponse<ProfileReponse>(MessageCode.PERIOD_ID_NOT_EMPTY, MessageCode.PERIOD_ID_NOT_EMPTY_MESSAGE, null);
					}
					
					if(checkHouseholdExisted == 1) {
						if(i.getCccd() == householdRequest.getCccd()) {
							continue;
						}	
					}
					
					Optional<Period> period = periodRepository.findByCodeAndIsDeleted(i.getPeriodCode(), Constants.NOT_ACTIVATE);
					
					Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(i.getCccd(), Constants.NOT_DELETED);
					ProfileInformation member = new ProfileInformation();
					member.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
					member.setCccd(identity.get().getCccd());
					member.setName(identity.get().getName());
					member.setBirthDay(identity.get().getBirthday());
					member.setGender(identity.get().getGender());
					member.setStatus(Constants.IS_ACTIVATE);
					member.setIsDeleted(Constants.NOT_DELETED);
					member.setPersonalTypeCode(member.getPersonalTypeCode());
					member.setPersonalTypeName(member.getPersonalTypeName());
					member.setCreatedDate(new Date());
					member.setCreatedUser("001201012182");
					member.setPeriod(period.get());
					
					if(identity.get().isHouseHold()) {
						member.setIsRepresent(Constants.TRUE);
					}				
					member.setProfile(profile);
					
					ProfileInformationInfo profileInfoData = new ProfileInformationInfo();
					profileInfoData.setCccd(identity.get().getCccd());
					profileInfoData.setName(identity.get().getName());
					profileInfoData.setGender(identity.get().getGender());
					profileInfoData.setPersonalTypeCode(Constants.FAMILY_TYPE_CODE);
					profileInfoData.setPersonalTypeName(Constants.FAMILY_TYPE_NAME);
					profileInfoData.setCreatedDate(new Date());
					profileInfoData.setCreatedUser("001201012182");
					profileInfoData.setPeriod(period.get().getMonth());
					
					if(identity.get().isHouseHold()) {
						profileInfoData.setIsRepresent(Constants.TRUE);
					}		
					
					profileResponse.add(profileInfoData);
					
					
					HealthInsurance insurance = new HealthInsurance();
					insurance.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
					
					String object = Constants.INSURANCE_FAMILY_TYPE;
					String benefit = Constants.INSURANCE_BENEFIT_FAMILY_TYPE;
					String districtId = identity.get().getDistrictIdentity().getCode();
					String socialInsuranceId = com.sqa.bhyt.common.ultis.StringUtils.generateRandomNumber();
					
					insurance.setIdentityNumber(object + benefit + districtId + socialInsuranceId);
					insurance.setName(identity.get().getName());
					insurance.setBirthday(identity.get().getBirthday());
					insurance.setGender(identity.get().getGender());						
					insurance.setRegisterPlaceCode(identity.get().getWardIdentity().getCodeName());
					insurance.setRegisterPlaceName(identity.get().getWardIdentity().getFullName());
					
//					Date validFrom = new Date();
//					insurance.setValidTimeFrom(validFrom);
//					Calendar calendar = Calendar.getInstance();
//					calendar.setTime(validFrom);
//					calendar.add(Calendar.MONTH, period.get().getMonth());
//					Date validTo = calendar.getTime();
//					insurance.setValidTimeTo(validTo);
					
					insurance.setNumberOfPeriod(period.get().getMonth());
					insurance.setCardIssuerCode(identity.get().getDistrictIdentity().getCodeName());
					insurance.setCardIssuerName(identity.get().getDistrictIdentity().getFullName());
					insurance.setStatus(Constants.NOT_ACTIVATE);
					insurance.setIsDeleted(Constants.NOT_DELETED);
					insurance.setCreatedDate(new Date());
					insurance.setCreatedUser("001201012182");
					insurance.setIdentity(identity.get());
							
					members.add(member);
					healthInsurances.add(insurance);
				}
				
				healthInsuranceRepository.saveAll(healthInsurances);
				profileRepository.save(profile);
				profileInfoRepository.saveAll(members);		
				
				response.setMembers(profileResponse);
				
				return new ServiceResponse<ProfileReponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			}
			else {
				return new ServiceResponse<ProfileReponse>(MessageCode.WRONG_TYPE, MessageCode.WRONG_TYPE_MESSAGE, null);
			}
	
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ProfileReponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Transactional
	@Override
	public ServiceResponse<ProfileReponse> createPersonalProfile(PersonalProfileRequest request) {
		try {
			
			if(!StringUtils.hasText(request.getCccd())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.CCCD_NOT_EMPTY, MessageCode.CCCD_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getPeriodCode())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.PERIOD_CODE_NOT_EMPTY, MessageCode.PERIOD_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getTypeCode())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.TYPE_ID_NOT_EMPTY, MessageCode.TYPE_ID_NOT_EMPTY_MESSAGE, null);
			}
			
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			String getUser = authentication.getName();
//			
//			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
//			if(!currUser.isPresent()) {
//				return new ServiceResponse<ProfileReponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
//			}
//			
//			if(currUser.get().getStatus() == Constants.NOT_ACTIVATE) {
//				return new ServiceResponse<ProfileReponse>(MessageCode.ACCOUNT_IS_NOT_VERIFY, MessageCode.ACCOUNT_IS_NOT_VERIFY_MESSAGE, null);
//			}
			
			Optional<Type> type = typeRepository.findByCodeAndIsDeleted(request.getTypeCode(), Constants.NOT_DELETED);
			if(!type.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.TYPE_NOT_FOUND, MessageCode.TYPE_NOT_FOUND_MESSAGE, null);
			}
			
			if(healthInsuranceRepository.existsByCCCDAndIsDeleted(request.getCccd(), Constants.NOT_DELETED) == 1) {
				return new ServiceResponse<ProfileReponse>(MessageCode.INSURANCE_MEMBER_EXISTED, MessageCode.
						INSURANCE_MEMBER_EXISTED_MESSAGE, null);
			}
			
			Optional<Period> period = periodRepository.findByCodeAndIsDeleted(request.getPeriodCode(), Constants.NOT_DELETED);
			if(!period.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
			}
			
			Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(request.getCccd(), Constants.NOT_DELETED); 
			if(!identity.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.PERSONAL_INFORMATION_INVALID, MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, 
						null);
			}
			
			Optional<ChargeUnit> baseSalary = chargeUnitRepository.findByCodeAndIsDeleted(Constants.BASE_SALARY, Constants.NOT_DELETED);
			if(!baseSalary.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
			}
			
			
			double totalCost = personalCharge(type.get().getCode(),  period.get().getMonth(), baseSalary.get().getCost());
			if(totalCost == -1) {
				return new ServiceResponse<ProfileReponse>(MessageCode.CANT_CHARGE_COST, MessageCode.CANT_CHARGE_COST_MESSAGE, null);
			}
			
			System.out.println(totalCost);
			
			
			Profile profile = new Profile();
			profile.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			profile.setCost(totalCost);
			profile.setType(type.get());
			profile.setStatus(Constants.IS_ACTIVATE);
			profile.setIsDeleted(Constants.NOT_DELETED);
			profile.setCreatedDate(new Date());
			profile.setCreatedUser("001201012182");
			
			ProfileReponse response = new ProfileReponse();
			response.setCost(profile.getCost());
			response.setPaidBy("001201012182");
			response.setStatus(Constants.NOT_ACTIVATE);

			List<ProfileInformationInfo> profileResponse = new ArrayList<>();
			ProfileInformation member = new ProfileInformation();
			member.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			member.setCccd(identity.get().getCccd());
			member.setName(identity.get().getName());
			member.setBirthDay(identity.get().getBirthday());
			member.setGender(identity.get().getGender());
			member.setStatus(Constants.NOT_ACTIVATE);
			member.setIsDeleted(Constants.NOT_DELETED);
			member.setPeriod(period.get());
			member.setPersonalTypeCode(Constants.BY_STATE_BUDGET_SUPORT_PAID_CODE);
			member.setPersonalTypeName(Constants.BY_STATE_BUDGET_PAID_NAME);
			member.setCreatedDate(new Date());
			member.setCreatedUser("001201012182");	
			member.setIsRepresent(Constants.TRUE);
			
			member.setProfile(profile);
			
			
			ProfileInformationInfo profileInfoData = new ProfileInformationInfo();
			profileInfoData.setCccd(identity.get().getCccd());
			profileInfoData.setName(identity.get().getName());
			profileInfoData.setGender(identity.get().getGender());
			profileInfoData.setPersonalTypeCode(member.getPersonalTypeCode());
			profileInfoData.setPersonalTypeName(member.getPersonalTypeName());
			profileInfoData.setCreatedDate(new Date());
			profileInfoData.setCreatedUser("001201012182");
			profileInfoData.setPeriod(period.get().getMonth());
			profileResponse.add(profileInfoData);
			
			HealthInsurance insurance = new HealthInsurance();
			insurance.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			
			String object = "";
			String benefit = "";
			if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_SUPPORT_FOR_NEAR_POOR)) {
				object = Constants.INSURANCE_PERSONAL_NEAR_POOR_TYPE;
				benefit = Constants.INSURANCE_BENEFIT_PERSONAL_NEAR_POOR_TYPE;
			}
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_SUPPORT_FOR_STUDENT)) {
				Calendar now = Calendar.getInstance();
				Calendar calDayOfbirth = Calendar.getInstance();
				calDayOfbirth.setTime(identity.get().getBirthday());
				
				int age = now.get(Calendar.YEAR) - calDayOfbirth.get(Calendar.YEAR);
				if(now.get(Calendar.MONTH) < calDayOfbirth.get(Calendar.MONTH)) {
					age--;
				}else if(now.get(Calendar.MONTH) == calDayOfbirth.get(Calendar.MONTH)
						&& now.get(Calendar.DAY_OF_MONTH) == calDayOfbirth.get(Calendar.DAY_OF_MONTH)) {
					age--;
				}
				
				if(age < 18) {
					object = Constants.INSURANCE_PERSONAL_STUDENT_TYPE;
					benefit = Constants.INSURANCE_BENEFIT_PERSONAL_STUDENT_TYPE;
				}
				else {
					object = Constants.INSURANCE_PERSONAL_COLLEGE_STUDENT_TYPE;
					benefit = Constants.INSURANCE_BENEFIT_PERSONAL_COLLEGE_STUDENT_TYPE;
				}
				
			}
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_SUPPORT_FOR_LOW_INCOME)) {
				object = Constants.INSURANCE_PERSONAL_LOW_INCOME;
				benefit = Constants.INSURANCE_BENEFIT_PERSONAL_LOW_INCOME;
			}		
			
			String districtId = identity.get().getDistrictIdentity().getCode();
			String socialInsuranceId = com.sqa.bhyt.common.ultis.StringUtils.generateRandomNumber();
			
			insurance.setIdentityNumber(object + benefit + districtId + socialInsuranceId);
			insurance.setName(identity.get().getName());
			insurance.setBirthday(identity.get().getBirthday());
			insurance.setGender(identity.get().getGender());						
			insurance.setRegisterPlaceCode(identity.get().getWardIdentity().getCodeName());
			insurance.setRegisterPlaceName(identity.get().getWardIdentity().getFullName());
			insurance.setNumberOfPeriod(period.get().getMonth());
			insurance.setCardIssuerCode(identity.get().getDistrictIdentity().getCodeName());
			insurance.setCardIssuerName(identity.get().getDistrictIdentity().getFullName());
			insurance.setStatus(Constants.NOT_ACTIVATE);
			insurance.setIsDeleted(Constants.NOT_DELETED);
			insurance.setCreatedDate(new Date());
			insurance.setCreatedUser("001201012182");
			insurance.setIdentity(identity.get());
			
			healthInsuranceRepository.save(insurance);
			profileRepository.save(profile);
			profileInfoRepository.save(member);	
			response.setMembers(profileResponse);	
			
			return new ServiceResponse<ProfileReponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ProfileReponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Transactional
	@Override
	public ServiceResponse<ProfileReponse> createOrganizationProfile(OrginizationProfileRequest request) {
		try {
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			String getUser = authentication.getName();
//			
//			//Kiêm tra đăng nhập
//			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
//			if(!currUser.isPresent()) {
//				return new ServiceResponse<ProfileReponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
//			}
//			
//			if(currUser.get().getStatus() == Constants.NOT_ACTIVATE) {
//				return new ServiceResponse<ProfileReponse>(MessageCode.ACCOUNT_IS_NOT_VERIFY, MessageCode.ACCOUNT_IS_NOT_VERIFY_MESSAGE, null);
//			}
			
			//Kiểm tra danh sách đầu vào rỗng
			if(request.getMembers().size() == Constants.IS_EMPTY) {
				return new ServiceResponse<ProfileReponse>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			// Kiểm tra loại đóng rỗng
			if(!StringUtils.hasText(request.getTypeCode())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.TYPE_CODE_NOT_EMPTY, MessageCode.TYPE_CODE_NOT_EMPTY_MESSAGE, null);
			}		
			
			for(OrginizationProfileInfo i : request.getMembers()) {
				int cntDuplicated = 0;
				for(OrginizationProfileInfo j : request.getMembers()) {
					if(i.getCccd().equals(j.getCccd())){
						cntDuplicated++;
					}
				}
				if(cntDuplicated != 1) {
					return new ServiceResponse<ProfileReponse>(MessageCode.EXISTED_MEMBER_IS_DUPLICATED, MessageCode.EXISTED_MEMBER_IS_DUPLICATED_MESSAGE, null);
				}
			}
			
			// Kiểm tra loại đóng hợp lệ
			Optional<Type> type = typeRepository.findByCodeAndIsDeleted(request.getTypeCode(), Constants.NOT_DELETED);
			if(!type.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.TYPE_NOT_FOUND, MessageCode.TYPE_NOT_FOUND_MESSAGE, null);
			}
			
			//Kiểm tra thành viên công ty đã có bảo hiểm chưa
			for(OrginizationProfileInfo member : request.getMembers()) {
				if(healthInsuranceRepository.existsByCCCDAndIsDeleted(member.getCccd(), Constants.NOT_DELETED) == 1) {
					return new ServiceResponse<ProfileReponse>(MessageCode.INSURANCE_MEMBER_EXISTED, MessageCode.
							INSURANCE_MEMBER_EXISTED_MESSAGE, null);
				}
			}
			
			ServiceResponse<Double> totalCost = null;
			
			//Kiểm tra có đúng kiểu đóng theo tổ chức không
			if((type.get().getCode().equals(Constants.ORGANIZATION_TYPE_FOR_WORKER)
					|| type.get().getCode().equals(Constants.ORGANIZATION_TYPE_FOR_OFFICER))) {
				// Tính tiền
				totalCost = OrganizationCharge(request.getMembers());		
			}
			else if(type.get().getCode().equals(Constants.ORGANIZATION_TYPE_FOR_NONE_PROFESSIONAL_WORK)) {
				Optional<ChargeUnit> baseSalary = chargeUnitRepository.findByCodeAndIsDeleted(Constants.BASE_SALARY, 
						Constants.NOT_DELETED);
				if(!baseSalary.isPresent()) {
					return new ServiceResponse<ProfileReponse>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
				}
				
				totalCost = OrganizationChargeForType3(request.getMembers(), baseSalary.get().getCost());
			}
			
			if(totalCost.getData() == null) {
				return new ServiceResponse<ProfileReponse>(totalCost.getCode(), totalCost.getMessage(), null);
			}
			System.out.println(totalCost);
			Profile profile = new Profile();
			profile.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			profile.setCost(totalCost.getData());
			profile.setType(type.get());
			profile.setStatus(Constants.NOT_ACTIVATE);
			profile.setIsDeleted(Constants.NOT_DELETED);
			profile.setCreatedDate(new Date());
			profile.setCreatedUser("001201012182");

			ProfileReponse response = new ProfileReponse();
			response.setCost(profile.getCost());
			response.setPaidBy("001201012182");
			response.setStatus(Constants.NOT_ACTIVATE);
			
			List<ProfileInformationInfo> profileResponse = new ArrayList<>();
			List<HealthInsurance> healthInsurances = new ArrayList<>();
			List<ProfileInformation> members = new ArrayList<>();
			for(OrginizationProfileInfo i : request.getMembers()) {
				
				Optional<Period> period = periodRepository.findByCodeAndIsDeleted(i.getPeriodCode(), Constants.NOT_DELETED);
				if(!period.isPresent()) {
					return new ServiceResponse<ProfileReponse>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
				}
				
				Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(i.getCccd(), Constants.NOT_DELETED);
				ProfileInformation member = new ProfileInformation();
				member.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
				member.setCccd(identity.get().getCccd());
				member.setName(identity.get().getName());
				member.setBirthDay(identity.get().getBirthday());
				member.setGender(identity.get().getGender());
				member.setStatus(Constants.NOT_ACTIVATE);
				member.setIsDeleted(Constants.NOT_DELETED);
				member.setPersonalTypeCode(Constants.ORGANIZATION_CODE);
				member.setPersonalTypeName(Constants.ORGANIZATION_NAME);
				member.setCreatedDate(new Date());
				member.setCreatedUser("001201012182");
				member.setPeriod(period.get());
				
				if(identity.get().isHouseHold()) {
					member.setIsRepresent(Constants.TRUE);
				}
				
				member.setProfile(profile);
				members.add(member);
				

				ProfileInformationInfo profileInfoData = new ProfileInformationInfo();
				profileInfoData.setCccd(identity.get().getCccd());
				profileInfoData.setName(identity.get().getName());
				profileInfoData.setGender(identity.get().getGender());
				profileInfoData.setPersonalTypeCode(member.getPersonalTypeCode());
				profileInfoData.setPersonalTypeName(member.getPersonalTypeName());
				profileInfoData.setCreatedDate(new Date());
				profileInfoData.setCreatedUser("001201012182");
				profileInfoData.setPeriod(period.get().getMonth());
				profileResponse.add(profileInfoData);
				
				HealthInsurance insurance = new HealthInsurance();
				insurance.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
				String object = "";
				String benefit = "";
				if(type.get().getCode().equals(Constants.ORGANIZATION_TYPE_FOR_WORKER)) {
					object = Constants.INSURANCE_ORGANIZATION_TYPE_FOR_WORKER;
					benefit = Constants.INSURANCE_BENEFIT_ORGANIZATION_TYPE_FOR_WORKER;
				}
				
				if(type.get().getCode().equals(Constants.ORGANIZATION_TYPE_FOR_OFFICER)) {
					object = Constants.INSURANCE_ORGANIZATION_TYPE_FOR_OFFICER;
					benefit = Constants.INSURANCE_BENEFIT_ORGANIZATION_TYPE_FOR_OFFICER;
				}
				
				if(type.get().getCode().equals(Constants.ORGANIZATION_TYPE_FOR_NONE_PROFESSIONAL_WORK)) {
					object = Constants.INSURANCE_ORGANIZATION_TYPE_FOR_NONE_PROFESSIONAL_WORK;
					benefit = Constants.INSURANCE_BENEFIT_ORGANIZATION_TYPE_FOR_NONE_PROFESSIONAL_WORK;
				}
				String districtId = identity.get().getDistrictIdentity().getCode();
				String socialInsuranceId = com.sqa.bhyt.common.ultis.StringUtils.generateRandomNumber();
				
				insurance.setIdentityNumber(object + benefit + districtId + socialInsuranceId);
				insurance.setName(identity.get().getName());
				insurance.setBirthday(identity.get().getBirthday());
				insurance.setGender(identity.get().getGender());						
				insurance.setRegisterPlaceCode(identity.get().getWardIdentity().getCodeName());
				insurance.setRegisterPlaceName(identity.get().getWardIdentity().getFullName());
				
				Date validFrom = new Date();
				insurance.setValidTimeFrom(validFrom);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(validFrom);
				calendar.add(Calendar.MONTH, period.get().getMonth());
				Date validTo = calendar.getTime();
				insurance.setValidTimeTo(validTo);
				
				insurance.setNumberOfPeriod(period.get().getMonth());
				insurance.setCardIssuerCode(identity.get().getDistrictIdentity().getCodeName());
				insurance.setCardIssuerName(identity.get().getDistrictIdentity().getFullName());
				insurance.setStatus(Constants.NOT_ACTIVATE);
				insurance.setIsDeleted(Constants.NOT_DELETED);
				insurance.setCreatedDate(new Date());
				insurance.setCreatedUser("001201012182");
				insurance.setIdentity(identity.get());
				
				members.add(member);
				healthInsurances.add(insurance);
				
			}
			
			healthInsuranceRepository.saveAll(healthInsurances);
			profileRepository.save(profile);
			profileInfoRepository.saveAll(members);	
			
			response.setMembers(profileResponse);
			
			return new ServiceResponse<ProfileReponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ProfileReponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}
	
	
	@Override
	public ServiceResponse<ProfileReponse> createBySocialInsuranceProfile(PaidBySocialInsuranceRequet request) {
		try {
			if(!StringUtils.hasText(request.getCccd())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.CCCD_NOT_EMPTY, MessageCode.CCCD_NOT_EMPTY_MESSAGE, null);
			}
			
			
			if(!StringUtils.hasText(request.getTypeCode())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.TYPE_ID_NOT_EMPTY, MessageCode.TYPE_ID_NOT_EMPTY_MESSAGE, null);
			}
//			
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			String getUser = authentication.getName();
//			
//			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
//			if(!currUser.isPresent()) {
//				return new ServiceResponse<ProfileReponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
//			}
//			
//			if(currUser.get().getStatus() == Constants.NOT_ACTIVATE) {
//				return new ServiceResponse<ProfileReponse>(MessageCode.ACCOUNT_IS_NOT_VERIFY, MessageCode.ACCOUNT_IS_NOT_VERIFY_MESSAGE, null);
//			}
//			
			Optional<Type> type = typeRepository.findByCodeAndIsDeleted(request.getTypeCode(), Constants.NOT_DELETED);
			if(!type.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.TYPE_NOT_FOUND, MessageCode.TYPE_NOT_FOUND_MESSAGE, null);
			}
			
			Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(request.getCccd(), Constants.NOT_DELETED); 
			if(!identity.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.PERSONAL_INFORMATION_INVALID, MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, 
						null);
			}
			
			Optional<Period> period = periodRepository.findByCodeAndIsDeleted(request.getPeriodCode(), Constants.NOT_DELETED);
			if(!period.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
			}
			
			if(healthInsuranceRepository.existsByCCCDAndIsDeleted(request.getCccd(), Constants.NOT_DELETED) == 1) {
				return new ServiceResponse<ProfileReponse>(MessageCode.INSURANCE_MEMBER_EXISTED, MessageCode.
						INSURANCE_MEMBER_EXISTED_MESSAGE, null);
			}
			
			
			ServiceResponse<Double> totalCost = null;
			if((type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_RETIRED))) {
				if(request.getAvgMonthlyPaymentForSocialInsurance() <= 0) {
					return new ServiceResponse<ProfileReponse>(MessageCode.AVG_PAID_FOR_UNEMPLOYEE_SOCIAL_INSURANCE_INVALID, 
							MessageCode.AVG_PAID_FOR_UNEMPLOYEE_SOCIAL_INSURANCE_INVALID_MESSAGE, null);
				}
				
				if(request.getNumberOfYearPayingSocialInsurance() <= 0) {
					return new ServiceResponse<ProfileReponse>(MessageCode.NUMBER_OF_YEAR_PAYING_SOCIAL_INSURANCE_INVALID, 
							MessageCode.NUMBER_OF_YEAR_PAYING_SOCIAL_INSURANCE_INVALID_MESSAGE, null);
				}
				
				totalCost = bySocialInsurancePaidChargeForRetired(identity.get().getBirthday(), identity.get().getGender(), period.get().getMonth(), 
						request.getAvgMonthlyPaymentForSocialInsurance(), request.getNumberOfYearPayingSocialInsurance());
				
			}
			else if(type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_LABOR_ACCIDENT)
				|| type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_SICK_PERSON)
				|| type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_RUBBER_WORKER)
				|| type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_80_YEARS_OLD_PERSON)
				|| type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_OFFICIALS_HAVE_NOT_WORKING)){
						
				Optional<ChargeUnit> baseSalary = chargeUnitRepository.findByCodeAndIsDeleted(Constants.BASE_SALARY, 
						Constants.NOT_DELETED);
				if(!baseSalary.isPresent()) {
					return new ServiceResponse<ProfileReponse>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
				}
				
				
				totalCost = bySocialInsurancePaidChargeFor4OtherObject(period.get().getMonth(), baseSalary.get().getCost());
			}
			else if(type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_UNEMPLOYEEE)) {
				if(request.getAvgLast6MonthPaymentForUnemployeeInsuarance() <= 0) {
					return new ServiceResponse<ProfileReponse>(MessageCode.AVG_PAID_FOR_UNEMPLOYEE_INSURANCE_INVALID, 
							MessageCode.AVG_PAID_FOR_UNEMPLOYEE_INSURANCE_INVALID_MESSAGE, null);
				}
				
				totalCost =  bySocialInsurancePaidChargeForUnemployee(request.getAvgLast6MonthPaymentForUnemployeeInsuarance(), 
						period.get().getMonth());
			}
			else {
				return new ServiceResponse<ProfileReponse>(MessageCode.THIS_TYPE_NOT_VALID, MessageCode.THIS_TYPE_NOT_VALID_MESSAGE, null);
			}
			
			if(totalCost.getData() == null) {
				return new ServiceResponse<ProfileReponse>(totalCost.getCode(), totalCost.getMessage(), null);
			}
			
			System.out.println(totalCost);
			
			Profile profile = new Profile();
			profile.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			profile.setCost(totalCost.getData());
			profile.setType(type.get());
			profile.setStatus(Constants.NOT_ACTIVATE);
			profile.setIsDeleted(Constants.NOT_DELETED);
			profile.setCreatedDate(new Date());
			profile.setCreatedUser("001201012182");
			
			ProfileReponse response = new ProfileReponse();
			response.setCost(profile.getCost());
			response.setPaidBy("001201012182");
			response.setStatus(Constants.NOT_ACTIVATE);
			
			List<ProfileInformationInfo> profileResponse = new ArrayList<>();
			
			ProfileInformation member = new ProfileInformation();
			member.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			member.setCccd(identity.get().getCccd());
			member.setName(identity.get().getName());
			member.setBirthDay(identity.get().getBirthday());
			member.setGender(identity.get().getGender());
			member.setStatus(Constants.NOT_ACTIVATE);
			member.setIsDeleted(Constants.NOT_DELETED);
			member.setPeriod(period.get());
			member.setPersonalTypeCode(Constants.BY_SOCIAL_INSURANCE_PAID_CODE);
			member.setPersonalTypeName(Constants.BY_SOCIAL_INSURANCE_PAID_NAME);
			
			member.setCreatedDate(new Date());
			member.setCreatedUser("001201012182");	
			member.setIsRepresent(Constants.TRUE);
			
			member.setProfile(profile);
			
			
			ProfileInformationInfo profileInfoData = new ProfileInformationInfo();
			profileInfoData.setCccd(identity.get().getCccd());
			profileInfoData.setName(identity.get().getName());
			profileInfoData.setGender(identity.get().getGender());
			profileInfoData.setPersonalTypeCode(member.getPersonalTypeCode());
			profileInfoData.setPersonalTypeName(member.getPersonalTypeName());
			profileInfoData.setCreatedDate(new Date());
			profileInfoData.setCreatedUser("001201012182");
			profileInfoData.setPeriod(period.get().getMonth());
			profileResponse.add(profileInfoData);
			
			HealthInsurance insurance = new HealthInsurance();
			insurance.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			
			String object = "";
			String benefit = Constants.INSURANCE_BENEFIT_BY_SOCIAL_INSURANCE_PAID_FOR_RETIRED;
			if(type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_RETIRED)) {
				object = Constants.INSURANCE_BY_SOCIAL_INSURANCE_PAID_FOR_RETIRED;
				benefit = Constants.INSURANCE_BENEFIT_BY_SOCIAL_INSURANCE_PAID_FOR_RETIRED;
			}
			else if(type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_LABOR_ACCIDENT)) {
				object = Constants.INSURANCE_BY_SOCIAL_INSURANCE_PAID_FOR_RETIRED;
				benefit = Constants.INSURANCE_BENEFIT_BY_SOCIAL_INSURANCE_PAID_FOR_RETIRED;
			}
			else if(type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_SICK_PERSON)) {
				object = Constants.INSURANCE_BY_SOCIAL_INSURANCE_PAID_FOR_SICK_PERSON;
				benefit = Constants.INSURANCE_BENEFIT_BY_SOCIAL_INSURANCE_PAID_FOR_SICK_PERSON;
			}
			else if(type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_RUBBER_WORKER)) {
				object = Constants.INSURANCE_BY_SOCIAL_INSURANCE_PAID_FOR_RUBBER_WORKER;
				benefit = Constants.INSURANCE_BENEFIT_BY_SOCIAL_INSURANCE_PAID_FOR_RUBBER_WORKER;
			}
			else if(type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_80_YEARS_OLD_PERSON)) {
				object = Constants.INSURANCE_BY_SOCIAL_INSURANCE_PAID_FOR_80_YEARS_OLD_PERSON;
				benefit = Constants.INSURANCE_BENEFIT_BY_SOCIAL_INSURANCE_PAID_FOR_80_YEARS_OLD_PERSON;
			}
			else if(type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_OFFICIALS_HAVE_NOT_WORKING)) {
				object = Constants.INSURANCE_BY_SOCIAL_INSURANCE_PAID_FOR_OFFICIALS_HAVE_NOT_WORKING;
				benefit = Constants.INSURANCE_BENEFIT_BY_SOCIAL_INSURANCE_PAID_FOR_OFFICIALS_HAVE_NOT_WORKING;
			}
			else if(type.get().getCode().equals(Constants.BY_SOCIAL_INSURANCE_PAID_FOR_UNEMPLOYEEE)) {
				object = Constants.INSURANCE_BY_SOCIAL_INSURANCE_PAID_FOR_UNEMPLOYEE;
				benefit = Constants.INSURANCE_BENEFIT_BY_SOCIAL_INSURANCE_PAID_FOR_UNEMPLOYEE;
			}		
			
			String districtId = identity.get().getDistrictIdentity().getCode();
			String socialInsuranceId = com.sqa.bhyt.common.ultis.StringUtils.generateRandomNumber();
			
			insurance.setIdentityNumber(object + benefit + districtId + socialInsuranceId);
			insurance.setName(identity.get().getName());
			insurance.setBirthday(identity.get().getBirthday());
			insurance.setGender(identity.get().getGender());						
			insurance.setRegisterPlaceCode(identity.get().getWardIdentity().getCodeName());
			insurance.setRegisterPlaceName(identity.get().getWardIdentity().getFullName());
			
			Date validFrom = new Date();
			insurance.setValidTimeFrom(validFrom);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(validFrom);
			calendar.add(Calendar.MONTH, period.get().getMonth());
			Date validTo = calendar.getTime();
			insurance.setValidTimeTo(validTo);
			
			insurance.setNumberOfPeriod(period.get().getMonth());
			insurance.setCardIssuerCode(identity.get().getDistrictIdentity().getCodeName());
			insurance.setCardIssuerName(identity.get().getDistrictIdentity().getFullName());
			insurance.setStatus(Constants.NOT_ACTIVATE);
			insurance.setIsDeleted(Constants.NOT_DELETED);
			insurance.setCreatedDate(new Date());
			insurance.setCreatedUser("001201012182");
			insurance.setIdentity(identity.get());
			
			healthInsuranceRepository.save(insurance);
			profileRepository.save(profile);
			profileInfoRepository.save(member);	

			response.setMembers(profileResponse);
			
			return new ServiceResponse<ProfileReponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ProfileReponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}
	
	@Override
	public ServiceResponse<ProfileReponse> createByStateBudgetProfile(PaidByStateBudgetRequest request) {
		try {
			if(!StringUtils.hasText(request.getCccd())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.CCCD_NOT_EMPTY, MessageCode.CCCD_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getPeriodCode())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.PERIOD_CODE_NOT_EMPTY, MessageCode.PERIOD_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(!StringUtils.hasText(request.getTypeCode())) {
				return new ServiceResponse<ProfileReponse>(MessageCode.TYPE_ID_NOT_EMPTY, MessageCode.TYPE_ID_NOT_EMPTY_MESSAGE, null);
			}
				
			
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			String getUser = authentication.getName();
//			
//			Optional<User> currUser = userRepository.findByUsernameAndIsDeleted(getUser, Constants.NOT_DELETED);
//			if(!currUser.isPresent()) {
//				return new ServiceResponse<ProfileReponse>(MessageCode.ACCOUNT_NOT_EXISTS, MessageCode.ACCOUNT_NOT_EXISTS_MESSAGE, null);
//			}
//			
//			if(currUser.get().getStatus() == Constants.NOT_ACTIVATE) {
//				return new ServiceResponse<ProfileReponse>(MessageCode.ACCOUNT_IS_NOT_VERIFY, MessageCode.ACCOUNT_IS_NOT_VERIFY_MESSAGE, null);
//			}
			
			Optional<Type> type = typeRepository.findByCodeAndIsDeleted(request.getTypeCode(), Constants.NOT_DELETED);
			if(!type.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.TYPE_NOT_FOUND, MessageCode.TYPE_NOT_FOUND_MESSAGE, null);
			}
			
			Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(request.getCccd(), Constants.NOT_DELETED); 
			if(!identity.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.PERSONAL_INFORMATION_INVALID, MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, 
						null);
			}
			
			Optional<Period> period = periodRepository.findByCodeAndIsDeleted(request.getPeriodCode(), Constants.NOT_DELETED);
			if(!period.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
			}
			
			if(healthInsuranceRepository.existsByCCCDAndIsDeleted(request.getCccd(), Constants.NOT_DELETED) == 1) {
				return new ServiceResponse<ProfileReponse>(MessageCode.INSURANCE_MEMBER_EXISTED, MessageCode.
						INSURANCE_MEMBER_EXISTED_MESSAGE, null);
			}
			
			
			Optional<ChargeUnit> baseSalary = chargeUnitRepository.findByCodeAndIsDeleted(Constants.BASE_SALARY, 
					Constants.NOT_DELETED);
			if(!baseSalary.isPresent()) {
				return new ServiceResponse<ProfileReponse>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
			}
			
			ServiceResponse<Double> totalCost = bySocialInsurancePaidChargeFor4OtherObject(period.get().getMonth(), baseSalary.get().getCost());		
			
			if(totalCost.getData() == null) {
				return new ServiceResponse<ProfileReponse>(totalCost.getCode(), totalCost.getMessage(), null);
			}
			
			System.out.println(totalCost);
			
			Profile profile = new Profile();
			profile.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			profile.setCost(totalCost.getData());
			profile.setType(type.get());
			profile.setStatus(Constants.NOT_ACTIVATE);
			profile.setIsDeleted(Constants.NOT_DELETED);
			profile.setCreatedDate(new Date());
			profile.setCreatedUser("001201012182");

			ProfileReponse response = new ProfileReponse();
			response.setCost(profile.getCost());
			response.setPaidBy("001201012182");
			response.setStatus(Constants.NOT_ACTIVATE);
			
			List<ProfileInformationInfo> profileResponse = new ArrayList<>();
			
			ProfileInformation member = new ProfileInformation();
			member.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			member.setCccd(identity.get().getCccd());
			member.setName(identity.get().getName());
			member.setBirthDay(identity.get().getBirthday());
			member.setGender(identity.get().getGender());
			member.setStatus(Constants.NOT_ACTIVATE);
			member.setIsDeleted(Constants.NOT_DELETED);
			member.setPeriod(period.get());
			member.setPersonalTypeCode(Constants.BY_SOCIAL_INSURANCE_PAID_CODE);
			member.setPersonalTypeName(Constants.BY_SOCIAL_INSURANCE_PAID_NAME);
			
			member.setCreatedDate(new Date());
			member.setCreatedUser("001201012182");	
			member.setIsRepresent(Constants.TRUE);
			
			member.setProfile(profile);
			
			ProfileInformationInfo profileInfoData = new ProfileInformationInfo();
			profileInfoData.setCccd(identity.get().getCccd());
			profileInfoData.setName(identity.get().getName());
			profileInfoData.setGender(identity.get().getGender());
			profileInfoData.setPersonalTypeCode(member.getPersonalTypeCode());
			profileInfoData.setPersonalTypeName(member.getPersonalTypeName());
			profileInfoData.setCreatedDate(new Date());
			profileInfoData.setCreatedUser("001201012182");
			profileInfoData.setPeriod(period.get().getMonth());
			profileResponse.add(profileInfoData);
			
			HealthInsurance insurance = new HealthInsurance();
			insurance.setId(com.sqa.bhyt.common.ultis.StringUtils.generateTransactionId());
			
			String object = "";
			String benefit = "";
			if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_OFFICIALS_HAVE_NOT_WORKING)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_OFFICIALS_HAVE_NOT_WORKING;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_OFFICIALS_HAVE_NOT_WORKING;
			}
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_LOSS_OF_WORKING)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_LOSS_OF_WORKING;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_LOSS_OF_WORKING;
			}
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_MERITORIOUS_SERVICES)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_MERITORIOUS_SERVICES;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_MERITORIOUS_SERVICES;
			}
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_VETERAN)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_VETERAN;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_VETERAN;
			}
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_MEMBER_OF_NA)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_MEMBER_OF_NA;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_MEMBER_OF_NA;
			}
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_6_YEAR_OLD_PERSON)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_6_YEAR_OLD_PERSON;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_6_YEAR_OLD_PERSON;
			}
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_SOCIAL_PROTECTION_PERSON)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_SOCIAL_PROTECTION_PERSON;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_SOCIAL_PROTECTION_PERSON;
			}	
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_POOR_PERSON)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_POOR_PERSON;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_POOR_PERSON;
			}	
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_ETHNIC_MINORITY)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_ETHNIC_MINORITY;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_ETHNIC_MINORITY;
			}	
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_ESPECCIALLY_DIFFICULT_PLACE)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_ESPECCIALLY_DIFFICULT_PLACE;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_ESPECCIALLY_DIFFICULT_PLACE;
			}	
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_ISLAND_PLACE)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_ISLAND_PLACE;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_ISLAND_PLACE;
			}	
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_RELATIVE_MERITORIOUS_PERSON)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_RELATIVE_MERITORIOUS_PERSON;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_RELATIVE_MERITORIOUS_PERSON;
			}	
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_RELATIVE_MERITORIOUS_PERSON_TC)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_RELATIVE_MERITORIOUS_PERSON_TC;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_RELATIVE_MERITORIOUS_PERSON_TC;
			}	
			else if(type.get().getCode().equals(Constants.BY_STATE_BUDGET_PAID_FOR_BODY_DONATION)) {
				object = Constants.INSURANCE_BY_STATE_BUDGET_PAID_FOR_BODY_DONATION;
				benefit = Constants.INSURANCE_BENEFIT_BY_BUDGET_PAID_PAID_FOR_BODY_DONATION;
			}	
			
			String districtId = identity.get().getDistrictIdentity().getCode();
			String socialInsuranceId = com.sqa.bhyt.common.ultis.StringUtils.generateRandomNumber();
			
			insurance.setIdentityNumber(object + benefit + districtId + socialInsuranceId);
			insurance.setName(identity.get().getName());
			insurance.setBirthday(identity.get().getBirthday());
			insurance.setGender(identity.get().getGender());						
			insurance.setRegisterPlaceCode(identity.get().getWardIdentity().getCodeName());
			insurance.setRegisterPlaceName(identity.get().getWardIdentity().getFullName());
			
			Date validFrom = new Date();
			insurance.setValidTimeFrom(validFrom);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(validFrom);
			calendar.add(Calendar.MONTH, period.get().getMonth());
			Date validTo = calendar.getTime();
			insurance.setValidTimeTo(validTo);
			
			insurance.setNumberOfPeriod(period.get().getMonth());
			insurance.setCardIssuerCode(identity.get().getDistrictIdentity().getCodeName());
			insurance.setCardIssuerName(identity.get().getDistrictIdentity().getFullName());
			insurance.setStatus(Constants.NOT_ACTIVATE);
			insurance.setIsDeleted(Constants.NOT_DELETED);
			insurance.setCreatedDate(new Date());
			insurance.setCreatedUser("001201012182");
			insurance.setIdentity(identity.get());
			
			
			
			healthInsuranceRepository.save(insurance);
			profileRepository.save(profile);
			profileInfoRepository.save(member);	
			
			response.setMembers(profileResponse);
			
			return new ServiceResponse<ProfileReponse>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<ProfileReponse>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}
	
	@Override
	public boolean extendInsurance() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getTransactionHistory() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private ServiceResponse<Double> bySocialInsurancePaidChargeForRetired(Date dayOfBirth, int gender, int month, 
			double avgMonthlyPaymentForSocialInsurance, int numberOfYearPayingSocialInsurance) {
		
		try {
			Calendar now = Calendar.getInstance();
			Calendar calDayOfbirth = Calendar.getInstance();
			calDayOfbirth.setTime(dayOfBirth);
			
			int age = now.get(Calendar.YEAR) - calDayOfbirth.get(Calendar.YEAR);
			if(now.get(Calendar.MONTH) < calDayOfbirth.get(Calendar.MONTH)) {
				age--;
			}else if(now.get(Calendar.MONTH) == calDayOfbirth.get(Calendar.MONTH)
					&& now.get(Calendar.DAY_OF_MONTH) == calDayOfbirth.get(Calendar.DAY_OF_MONTH)) {
				age--;
			}
			
			if(gender == Constants.MALE) {
				int percent = 45;
				int baseYear = 20;
				double cost = 1;
				int baseAge = 60;
				int maxPercent = 75;
				
				if(age < baseAge) {
					return new ServiceResponse<>(MessageCode.AGE_IS_NOT_ENOUGH, MessageCode.AGE_IS_NOT_ENOUGH_MESSAGE, null);
				}
				
				if(numberOfYearPayingSocialInsurance < baseYear) {
					return new ServiceResponse<>(MessageCode.NUMBER_OF_YEAR_NOT_ENOUGH, MessageCode.NUMBER_OF_YEAR_NOT_ENOUGH_MESSAGE, null);
				}
				
				while (percent < maxPercent && numberOfYearPayingSocialInsurance != baseYear) {
					percent += 2;
					baseYear++;
				}
				
				cost = avgMonthlyPaymentForSocialInsurance * 4.5 / 100* percent / 100 * month;
				
				return new ServiceResponse<Double>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, cost);
			}
			else if(gender == Constants.FEMALE) {
				int percent = 45;
				int baseYear = 15;
				double cost = 0;
				int baseAge = 55;
				int maxPercent = 75;
				
				if(age < baseAge) {
					return new ServiceResponse<>(MessageCode.AGE_IS_NOT_ENOUGH, MessageCode.AGE_IS_NOT_ENOUGH_MESSAGE, null);
				}
				
				if(numberOfYearPayingSocialInsurance < baseYear) {
					return new ServiceResponse<>(MessageCode.NUMBER_OF_YEAR_NOT_ENOUGH, MessageCode.NUMBER_OF_YEAR_NOT_ENOUGH_MESSAGE, null);
				}
				
				while (percent < maxPercent && numberOfYearPayingSocialInsurance != baseYear) {
					percent += 2;
					baseYear++;
				}
				
				cost = avgMonthlyPaymentForSocialInsurance * 4.5 /100 * percent / 100 * month;
				
				return new ServiceResponse<Double>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, cost);
			}
			
			
			return new ServiceResponse<Double>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Double>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
		
	}
	
	private ServiceResponse<Double> bySocialInsurancePaidChargeForUnemployee(Double avgLast6MonthPaymentForUnemployeeInsuarance, int month) {
		try {
			double cost = avgLast6MonthPaymentForUnemployeeInsuarance * 4.5 / 100 * 60 / 100 *  month;
			
			return new ServiceResponse<Double>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, cost);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Double>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
		
	}
	
	private ServiceResponse<Double> bySocialInsurancePaidChargeFor4OtherObject(int month, double baseSalary) {
		try {
			double cost = baseSalary * 4.5 / 100 * month;
			
			return new ServiceResponse<Double>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, cost);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Double>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}
	
	private ServiceResponse<Double> familyCharge(List<FamilyProfileInfo> members, double baseSalary) {
		
		try {
			double result = 0;
			int cnt = 1;
			int numberOfMembers = members.size();
			
			if(numberOfMembers == 0) {
				return new ServiceResponse<Double>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			for (FamilyProfileInfo i : members) {
				
				
				Optional<Period> period = periodRepository.findByCodeAndIsDeleted(i.getPeriodCode(), Constants.NOT_ACTIVATE);
				
				if(!period.isPresent()) {
					return new ServiceResponse<Double>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
				}
				
				if(cnt == 1) {
					result = baseSalary * 4.5 / 100 * period.get().getMonth();
				}
				else if(cnt == 2) {
					result += baseSalary * 4.5 / 100 * period.get().getMonth() * 70/100;
				}
				else if(cnt == 3) {
					result += baseSalary * 4.5 / 100 * period.get().getMonth() * 60/100;
				}
				else if(cnt == 4) {
					result += baseSalary * 4.5 / 100 * period.get().getMonth() * 50/100;
				}
				else {
					result += baseSalary * 4.5 / 100 * period.get().getMonth() * 40/100;
				}
				if(cnt == numberOfMembers) {
					break;
				}
				cnt++;
			}
			
			return new ServiceResponse<Double>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Double>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}
	
	private ServiceResponse<Double> moreFamilyMembersCharge(List<FamilyProfileInfo> members, double baseSalary) {
		try {
			double result = 0;
			int numberOfMembers = members.size();
			
			if(numberOfMembers == 0) {
				return new ServiceResponse<Double>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			//TODO: Kiểm tra xem gia đình đã có bao nhiêu người đăng ký
			//Lấy thông tin chủ hộ -> Kiểm tra xem có những ai đã đăng ký rồi
			FamilyProfileInfo householdRequest = members.get(Constants.FIRST_ITEM);
			
			//Người đầu trong danh sách là chủ hộ
			Optional<Identity> household = identityRepository.findByCccdAndIsHouseHoldAndIsDeleted(householdRequest.getCccd(), 
					Constants.IS_HOUSEHOLD, Constants.NOT_DELETED);
			if(!household.isPresent()) {
				return new ServiceResponse<Double>(MessageCode.HOUSEHOLD_NOT_FOUND, MessageCode.HOUSEHOLD_NOT_FOUND_MESSAGE, null);
			}
			
			//Lấy danh sách gia đình từ mã sổ hộ khẩu
			List<Identity> identities = identityRepository.findByFamilyAndIsDeleted(household.get().getFamily().getId(), Constants.NOT_DELETED);
			int memberRegistedCount = 1;
			for(Identity i : identities) {
				if(healthInsuranceRepository.existsByCCCDAndIsDeleted(i.getCccd(), Constants.NOT_DELETED) == 1) {
					memberRegistedCount++;
				}
			}
			
			for (FamilyProfileInfo i : members) {
				if(i.getCccd() != householdRequest.getCccd()) {
					Optional<Period> period = periodRepository.findByCodeAndIsDeleted(i.getPeriodCode(), Constants.NOT_DELETED);
					
					if(!period.isPresent()) {
						return new ServiceResponse<Double>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
					}
					
					if(memberRegistedCount == 1) {
						result = baseSalary * 4.5 / 100 * period.get().getMonth();
					}
					else if(memberRegistedCount == 2) {
						result += baseSalary * 4.5 / 100 * period.get().getMonth() * 70/100;
					}
					else if(memberRegistedCount == 3) {
						result += baseSalary * 4.5 / 100 * period.get().getMonth() * 60/100;
					}
					else if(memberRegistedCount == 4) {
						result += baseSalary * 4.5 / 100 * period.get().getMonth() * 50/100;
					}
					else {
						result += baseSalary * 4.5 / 100 * period.get().getMonth() * 40/100;
					}
					if(memberRegistedCount == numberOfMembers) {
						break;
					}
					memberRegistedCount++;
				}
			}
			
			System.out.println(result);
			return new ServiceResponse<Double>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Double>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}
	
	
	private double personalCharge(String typeCode, int month, double baseSalary) {
		
		double result = 0;
		
		
		if(typeCode.equals(Constants.BY_STATE_BUDGET_SUPPORT_FOR_NEAR_POOR) ) {
			result = baseSalary * 4.5 / 100 * month * 30 / 100;
		}
		else if(typeCode.equals(Constants.BY_STATE_BUDGET_SUPPORT_FOR_STUDENT) || 
				typeCode.equals(Constants.BY_STATE_BUDGET_SUPPORT_FOR_LOW_INCOME)) {
			result = baseSalary * 4.5 / 100 * month * 70 / 100;
		}
		
		return result;
	}
	
	//TÍnh tiền cho người hoạt động không chuyên trách ở xã, phường, thị trấn
	private ServiceResponse<Double> OrganizationChargeForType3(List<OrginizationProfileInfo> members, double baseSalary) {
		try {
			double result = 0;
			int numberOfMembers = members.size();
			
			if(numberOfMembers == 0) {
				return new ServiceResponse<Double>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			for(OrginizationProfileInfo i : members) {
				Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(i.getCccd(), Constants.NOT_DELETED);
				
				if(!StringUtils.hasText(i.getCccd())) {
					return new ServiceResponse<Double>(MessageCode.CCCD_NOT_EMPTY, MessageCode.CCCD_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!identity.isPresent()) {
					return new ServiceResponse<Double>(MessageCode.PERSONAL_INFORMATION_INVALID, MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, 
							null);
				}
				
		
				if(!StringUtils.hasText(i.getPeriodCode())) {
					return new ServiceResponse<Double>(MessageCode.PERIOD_ID_NOT_EMPTY, MessageCode.PERIOD_ID_NOT_EMPTY_MESSAGE, null);
				}
					
				Optional<Period> period = periodRepository.findByCodeAndIsDeleted(i.getPeriodCode(), Constants.NOT_DELETED);
				
				if(!period.isPresent()) {
					return new ServiceResponse<Double>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
				}
				
				result += baseSalary * 4.5 / 100 * period.get().getMonth();
			}
			
			return new ServiceResponse<Double>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, result);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Double>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	// Tính tiền cho người clao động có thời hạn trên 3 tháng
	// Hoặc cho cán bộ, công chức, viên chức
	private ServiceResponse<Double> OrganizationCharge(List<OrginizationProfileInfo> members) {
		try {
			double result = 0;
	
			int numberOfMembers = members.size();
			
			if(numberOfMembers == 0) {
				return new ServiceResponse<Double>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			for(OrginizationProfileInfo i : members) {
				Optional<Identity> identity = identityRepository.findByCccdAndIsDeleted(i.getCccd(), Constants.NOT_DELETED);
				
				if(!identity.isPresent()) {
					return new ServiceResponse<Double>(MessageCode.PERSONAL_INFORMATION_INVALID, MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, 
							null);
				}
				
				if(!StringUtils.hasText(i.getCccd())) {
					return new ServiceResponse<Double>(MessageCode.CCCD_NOT_EMPTY, MessageCode.CCCD_NOT_EMPTY_MESSAGE, null);
				}
				
				if(!StringUtils.hasText(i.getWorkType())) {
					return new ServiceResponse<Double>(MessageCode.WORK_TYPE_EMPTY, MessageCode.WORK_TYPE_EMPTY_MESSAGE, null);
				}
				
				if(i.getSalary() == 0) {
					return new ServiceResponse<Double>(MessageCode.SALARY_INVALID, MessageCode.SALARY_INVALID_MESSAGE, null);
				}

				if(!StringUtils.hasText(i.getPeriodCode())) {
					return new ServiceResponse<Double>(MessageCode.PERIOD_ID_NOT_EMPTY, MessageCode.PERIOD_ID_NOT_EMPTY_MESSAGE, null);
				}
				
				Optional<Period> period = periodRepository.findByCodeAndIsDeleted(i.getPeriodCode(), Constants.NOT_DELETED);
				
				if(!period.isPresent()) {
					return new ServiceResponse<Double>(MessageCode.PERIOD_NOT_FOUND, MessageCode.PERIOD_NOT_FOUND_MESSAGE, null);
				}
				
				int region = identity.get().getDistrictIdentity().getRegion();

				if(i.getWorkType().equals(Constants.NORMAL)) {
					if(region == Constants.REGON1) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.NORMAL_REGION1, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, 
									null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, 
									null);
						}
					}
					else if(region == Constants.REGON2) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.NORMAL_REGION2, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, 
									null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, 
									null);
						}
					}
					else if(region == Constants.REGON3) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.NORMAL_REGION3, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON4) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.NORMAL_REGION4, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
				}
				else if(i.getWorkType().equals(Constants.TRAINED)) {
					if(region == Constants.REGON1) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.TRAINED_REGION1, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON2) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.TRAINED_REGION2, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON3) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.TRAINED_REGION3, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON4) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.TRAINED_REGION4, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
				}
				else if(i.getWorkType().equals(Constants.DANGEROUS_CONDITION_NORMAL)) {
					if(region == Constants.REGON1) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.DANGEROUS_CONDITION_NORMAL_REGION1, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON2) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.DANGEROUS_CONDITION_NORMAL_REGION2, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON3) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.DANGEROUS_CONDITION_NORMAL_REGION3, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					if(region == Constants.REGON4) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.DANGEROUS_CONDITION_NORMAL_REGION4, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
				}
				else if(i.getWorkType().equals(Constants.DANGEROUS_CONDITION_TRAINED)) {
					if(region == Constants.REGON1) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.DANGEROUS_CONDITION_TRAINED_REGION1, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON2) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.DANGEROUS_CONDITION_TRAINED_REGION2, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON3) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.DANGEROUS_CONDITION_TRAINED_REGION3, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON4) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.DANGEROUS_CONDITION_TRAINED_REGION4, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
				}
				else if(i.getWorkType().equals(Constants.SPECIAL_DANGEROUS_CONDITION_NORMAL)) {
					if(region == Constants.REGON1) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.SPECIAL_DANGEROUS_CONDITION_NORMAL_REGION1, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON2) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.SPECIAL_DANGEROUS_CONDITION_NORMAL_REGION2, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON3) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.SPECIAL_DANGEROUS_CONDITION_NORMAL_REGION3, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON4) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.SPECIAL_DANGEROUS_CONDITION_NORMAL_REGION4, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
				}
				else if(i.getWorkType().equals(Constants.SPECIAL_DANGEROUS_CONDITION_TRAINED)) {
					if(region == Constants.REGON1) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.SPECIAL_DANGEROUS_CONDITION_TRAINED_REGION1, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON2) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.SPECIAL_DANGEROUS_CONDITION_TRAINED_REGION2, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON3) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.SPECIAL_DANGEROUS_CONDITION_TRAINED_REGION3, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					else if(region == Constants.REGON4) {
						Optional<ChargeUnit> cost = chargeUnitRepository.findByCodeAndIsDeleted(Constants.SPECIAL_DANGEROUS_CONDITION_TRAINED_REGION4, Constants.NOT_DELETED);
						if(!cost.isPresent()) {
							return new ServiceResponse<Double>(MessageCode.CHARGE_UNIT_NOT_FOUND, MessageCode.CHARGE_UNIT_NOT_FOUND_MESSAGE, null);
						}
						
						if(i.getSalary() >= cost.get().getCost()) {
							result += cost.get().getCost() * 4.5 / 100 * period.get().getMonth();
						}
						else {
							return new ServiceResponse<Double>(MessageCode.USER_NOT_ELIGIBLE_TO_JOIN, MessageCode.USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE, null);
						}
					}
					
				}
				
		
			}
			return new ServiceResponse<Double>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Double>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, null);
		}
	}

	@Transactional
	@Override
	public ServiceResponse<Boolean> payment(PaymentInfoRequest request) {
		try {
			if(!StringUtils.hasText(request.getUsername())) {
				return new ServiceResponse<Boolean>(MessageCode.USERNAME_NOT_EMPTY, MessageCode.USERNAME_NOT_EMPTY_MESSAGE, false);
			}
			
			Optional<User> user = userRepository.findByUsernameAndIsDeleted(request.getUsername(), Constants.NOT_DELETED);
			if(!user.isPresent()) {
				return new ServiceResponse<Boolean>(MessageCode.PERSONAL_INFORMATION_INVALID, 
						MessageCode.PERSONAL_INFORMATION_INVALID_MESSAGE, false);
			}
			
			List<HealthInsurance> paymentList = healthInsuranceRepository.findByCreatedUser(user.get().getUsername());
			if(paymentList.size() == 0) {
				return new ServiceResponse<Boolean>(MessageCode.LIST_IS_EMPTY, MessageCode.LIST_IS_EMPTY_MESSAGE, false);
			}
			
			Optional<Profile> profile = profileRepository.findByCreatedUserAndIsDeleted(user.get().getUsername(), Constants.NOT_DELETED);
			if(!profile.isPresent()) {
				return new ServiceResponse<Boolean>(MessageCode.PROFILE_NOT_FOUND, MessageCode.PROFILE_NOT_FOUND_MESSAGE, false);
			}
			profile.get().setUpdatedDate(new Date());
			profile.get().setUpdatedUser(user.get().getUsername());
			profile.get().setStatus(Constants.PAID);
			profileRepository.save(profile.get());
			
			for(HealthInsurance i : paymentList) {
				if(i.getStatus() == Constants.IS_ACTIVATE) {
					return new ServiceResponse<Boolean>(MessageCode.INSURANCE_IS_ACTIVATE, MessageCode.INSURANCE_IS_ACTIVATE_MESSAGE, false);
				}
				Date validFrom = new Date();
				i.setValidTimeFrom(validFrom);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(validFrom);
				calendar.add(Calendar.MONTH, i.getNumberOfPeriod());
				Date validTo = calendar.getTime();
				i.setValidTimeTo(validTo);
				i.setStatus(Constants.IS_ACTIVATE);
				i.setUpdatedDate(new Date());
				i.setUpdatedUser(user.get().getUsername());
			}
			
			healthInsuranceRepository.saveAll(paymentList);
			return new ServiceResponse<Boolean>(MessageCode.SUCCESS, MessageCode.SUCCESS_MESSAGE, true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<Boolean>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE, false);
		}
	}

}
