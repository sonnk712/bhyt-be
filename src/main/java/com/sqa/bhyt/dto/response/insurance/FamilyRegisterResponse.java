package com.sqa.bhyt.dto.response.insurance;

import java.util.List;

import com.sqa.bhyt.dto.request.insurance.FamilyProfileInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FamilyRegisterResponse {
	List<InsuranceInfoResponse> listMembers;
	FamilyProfileInfo profile;
}
