package com.sqa.bhyt.dto.request.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClearOTPRequest {
	private String secret;
}
