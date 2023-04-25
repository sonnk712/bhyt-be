package com.sqa.bhyt.dto.response.profile;

import java.util.Date;

import com.sqa.bhyt.dto.response.type.TypeCombobox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileFilterResponse {
	private String id;
	private String cccd;
	private String name;
	private double cost;
	private TypeCombobox type;
	private Date paidDate;
	private int status;
	private String contact;
	private double totalPrice;
}
