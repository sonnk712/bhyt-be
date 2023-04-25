package com.sqa.bhyt.dto.response.profile;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileInformationInfo {
	private String cccd;
	private String name;
	private int gender;
	private int personalTypeCode;
	private String personalTypeName;
	private int isRepresent;
	private int period;
	private String createdUser;
	private Date createdDate;
}
