package com.sqa.bhyt.dto.request.insurance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalProfileRequest {
	private String cccd;
	private String periodCode;
	private String typeCode;
}
