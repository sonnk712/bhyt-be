package com.sqa.bhyt.dto.request.place;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardDTO {
	private String code;
	private String codeName;
	private String fullName;
}
