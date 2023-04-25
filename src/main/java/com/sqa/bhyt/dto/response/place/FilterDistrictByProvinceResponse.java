package com.sqa.bhyt.dto.response.place;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterDistrictByProvinceResponse {
	private List<DistrictCombobox> districts;
}
