package com.sqa.bhyt.common.authentication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private String username;
	private String gmail;
	private String access_token;
	private Integer expires_in;
	private Integer refresh_expires_in;
	private String refresh_token;
}
