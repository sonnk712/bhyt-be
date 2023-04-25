package com.sqa.bhyt.dto.response.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeInfoResponse {
	private String id;
	private String code;
	private String name;
}
