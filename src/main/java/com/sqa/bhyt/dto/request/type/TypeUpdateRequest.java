package com.sqa.bhyt.dto.request.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeUpdateRequest {
	private String id;
	private String code;
	private String name;
}
