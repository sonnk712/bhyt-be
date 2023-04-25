package com.sqa.bhyt.dto.response.place;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceCombobox {
	private String code;
	private String codeName;
	private String name;
	private String fullName;
}
