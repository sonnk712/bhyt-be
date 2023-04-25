package com.sqa.bhyt.common.authentication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {
	private String username;
	private String gmail;
	private String phoneNumber;
}
