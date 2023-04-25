package com.sqa.bhyt.common.authentication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
	private String username;
	private String password;
	private String confirmPassword;
	private String phoneNumber;
	private String email;
}
