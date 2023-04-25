package com.sqa.bhyt.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivateInfo {
	private String username;
	private String transactionId;
	private String email;
}
