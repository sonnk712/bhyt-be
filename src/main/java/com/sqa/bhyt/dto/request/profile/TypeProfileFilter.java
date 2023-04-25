package com.sqa.bhyt.dto.request.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeProfileFilter {
	private String textSearch;
	private String id;
}
