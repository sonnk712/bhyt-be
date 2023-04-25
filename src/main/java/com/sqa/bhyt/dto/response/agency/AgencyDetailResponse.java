package com.sqa.bhyt.dto.response.agency;

import java.util.Date;

import com.sqa.bhyt.dto.response.place.DistrictCombobox;
import com.sqa.bhyt.dto.response.place.ProvinceCombobox;
import com.sqa.bhyt.dto.response.place.WardCombobox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyDetailResponse {
	private String id;
	private String code;
	private String name;
	private String receivingPlace;
	private String person;
	private String phoneNumber;
	private String address;
	private int status;
	private Date createdDate;
	private Date updatedDate;
	private DistrictCombobox district;
	private WardCombobox ward;
	private ProvinceCombobox province;
}
