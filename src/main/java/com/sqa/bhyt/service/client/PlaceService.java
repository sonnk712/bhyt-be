package com.sqa.bhyt.service.client;

import java.util.List;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.place.FilterDistrictByProvince;
import com.sqa.bhyt.dto.request.place.FilterWardByDistrict;
import com.sqa.bhyt.dto.response.place.DistrictCombobox;
import com.sqa.bhyt.dto.response.place.ProvinceCombobox;
import com.sqa.bhyt.dto.response.place.WardCombobox;

public interface PlaceService {
	
	ServiceResponse<List<ProvinceCombobox>> findProvince();
	
	ServiceResponse<List<DistrictCombobox>> findDistictByProvince(FilterDistrictByProvince request);
	
	ServiceResponse<List<WardCombobox>> findWardByDistrict(FilterWardByDistrict request);
}
