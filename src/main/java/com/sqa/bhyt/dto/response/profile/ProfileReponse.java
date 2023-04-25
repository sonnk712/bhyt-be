package com.sqa.bhyt.dto.response.profile;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileReponse {
	private String paidBy;
	private double cost;
	private int status;
	private List<ProfileInformationInfo> members;
}
