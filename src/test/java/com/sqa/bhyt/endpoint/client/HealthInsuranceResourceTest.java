package com.sqa.bhyt.endpoint.client;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.function.EntityResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sqa.bhyt.common.constants.MessageCode;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.insurance.FamilyProfileInfo;
import com.sqa.bhyt.dto.request.insurance.FamilyProfileRequest;
import com.sqa.bhyt.dto.response.profile.ProfileReponse;
import com.sqa.bhyt.endpoint.client.HealthInsuranceResource;
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
import com.sqa.bhyt.service.client.impl.HealthInsuranceServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class HealthInsuranceResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();
	

	@InjectMocks 
	HealthInsuranceResource healthInsuranceResource;
	
	@Mock 
	HealthInsuranceServiceImpl healthInsuranceServiceImpl;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	HealthInsuranceRepository healthInsuranceRepository;
	
	@MockBean
	IdentityRepository identityRepository;
	
	@MockBean
	TypeRepository typeRepository;
	
	@MockBean
	PeriodRepository periodRepository;
	
	@MockBean
	ChargeUnitRepository chargeUnitRepository;
	
	@MockBean
	ProfileRepository profileRepository;
	
	@MockBean
	ProfileInfoRepository profileInfoRepository;
	
	@MockBean
	DistrictRepository districtRepository;
	
	@MockBean
	WardsRepository wardsRepository;
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
  
    }
	
//	@Test
//	public void familyRegisterTestWithInputListEmpty() throws Exception{
//		FamilyProfileRequest request = new FamilyProfileRequest();
//		request.setChargeUnitCode("BASE_SALARY");
//		request.setTypeCode("FAMILY");
//		List<FamilyProfileInfo> member = new ArrayList<>();
//		member.add(new FamilyProfileInfo("001201012187", "ONE_YEAR"));
////		member.add(new FamilyProfileInfo("001201012181", "ONE_YEAR"));
//		request.setMembers(member);
//		
//		ServiceResponse<ProfileReponse> expectedOutput = new ServiceResponse<>(MessageCode.LIST_IS_EMPTY, 
//				MessageCode.LIST_IS_EMPTY_MESSAGE, null);
//		
////		HealthInsuranceServiceImpl healthInsuranceServiceImpl = Mockito.mock(HealthInsuranceServiceImpl.class);
//		when(healthInsuranceService.createFamilyProfile(request)).thenReturn(expectedOutput);
//		
//		ResponseEntity<ServiceResponse<ProfileReponse>> actualResponse = healthInsuranceResource.familyRegister(request);
//		
//		assertEquals(expectedOutput.getCode(), actualResponse.getBody().getCode());
//		assertEquals(expectedOutput.getMessage(), actualResponse.getBody().getMessage());
//		assertEquals(expectedOutput.getData(), actualResponse.getBody().getData());
//		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
//	}
	
	@Test
	public void familyRegisterTestWithSameInput() throws Exception{
		FamilyProfileRequest request = new FamilyProfileRequest();
		request.setChargeUnitCode("BASE_SALARY");
		request.setTypeCode("FAMILY");
		List<FamilyProfileInfo> member = new ArrayList<>();
		member.add(new FamilyProfileInfo("001201012181", "ONE_YEAR"));
		member.add(new FamilyProfileInfo("001201012182", "ONE_YEAR"));
		request.setMembers(member);
		
		ServiceResponse<ProfileReponse> expectedOutput = new ServiceResponse<>(MessageCode.EXISTED_MEMBER_IS_DUPLICATED, 
				MessageCode.EXISTED_MEMBER_IS_DUPLICATED_MESSAGE, null);
		

		when(healthInsuranceServiceImpl.createFamilyProfile(request)).thenReturn(expectedOutput);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/health-insurance/family-register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(request)))
	            .andExpect(status().isOk())
	            .andReturn();

	    String content = result.getResponse().getContentAsString();
	    ServiceResponse<ProfileReponse> actualOutput = objectMapper.readValue(content, new TypeReference<ServiceResponse<ProfileReponse>>(){});

	    assertEquals(expectedOutput.getCode(), actualOutput.getCode());
	    assertEquals(expectedOutput.getMessage(), actualOutput.getMessage());
	    assertEquals(expectedOutput.getData(), actualOutput.getData());
	}
	

//	@Test
//	public void familyRegisterTestWithNotContainHousehold() throws Exception{
//		FamilyProfileRequest request = new FamilyProfileRequest();
//		request.setChargeUnitCode("BASE_SALARY");
//		request.setTypeCode("FAMILY");
//		List<FamilyProfileInfo> member = new ArrayList<>();
//		member.add(new FamilyProfileInfo("001201012182", "ONE_YEAR"));
//		member.add(new FamilyProfileInfo("001201012185", "ONE_YEAR"));
//		request.setMembers(member);
//		
//		ServiceResponse<ProfileReponse> expectedOutput = new ServiceResponse<>(MessageCode.TYPE_NOT_FOUND, 
//				MessageCode.ACCOUNT_EXISTS_MESSAGE, null);
//		
//
//		Mockito.when(healthInsuranceServiceImpl.createFamilyProfile(request)).thenReturn(expectedOutput);
//		
//		ServiceResponse<ProfileReponse> actualResponse = healthInsuranceServiceImpl.createFamilyProfile(request);
//		
//		assertEquals(expectedOutput.getCode(), actualResponse.getCode());
//		assertEquals(expectedOutput.getMessage(), actualResponse.getMessage());
//		assertEquals(expectedOutput.getData(), actualResponse.getData());
//		
//	}
//	
//	@Test
//	public void familyRegisterTestWithInvalidType() throws Exception{
//		FamilyProfileRequest request = new FamilyProfileRequest();
//		request.setChargeUnitCode("BASE_SALARY_WRONG");
//		request.setTypeCode("FAMILY");
//		List<FamilyProfileInfo> member = new ArrayList<>();
//		member.add(new FamilyProfileInfo("001201012187", "ONE_YEAR"));
//		member.add(new FamilyProfileInfo("001201012181", "ONE_YEAR"));
//		request.setMembers(member);
//		
//		ServiceResponse<ProfileReponse> expectedOutput = new ServiceResponse<>(MessageCode.TYPE_NOT_FOUND, 
//				MessageCode.TYPE_NOT_FOUND_MESSAGE, null);
//		
//
//		Mockito.when(healthInsuranceServiceImpl.createFamilyProfile(request)).thenReturn(expectedOutput);
//		
//		ServiceResponse<ProfileReponse> actualResponse = healthInsuranceServiceImpl.createFamilyProfile(request);
//		
//		assertEquals(expectedOutput.getCode(), actualResponse.getCode());
//		assertEquals(expectedOutput.getMessage(), actualResponse.getMessage());
//		assertEquals(expectedOutput.getData(), actualResponse.getData());
//		
//	}

	
}
