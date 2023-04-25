package com.sqa.bhyt.dto.response.profile;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileInfoResponse {
	private List<ProfileFilterResponse> profileInfo;
	private double totalCost;
}
