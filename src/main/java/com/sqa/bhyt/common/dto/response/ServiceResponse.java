package com.sqa.bhyt.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> {
	private int code;
	private String message;
	private T data;
}
