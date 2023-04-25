package com.sqa.bhyt.service.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sqa.bhyt.common.constants.MessageCode;
import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.place.FilterDistrictByProvince;
import com.sqa.bhyt.dto.request.place.FilterWardByDistrict;
import com.sqa.bhyt.dto.response.place.DistrictCombobox;
import com.sqa.bhyt.dto.response.place.ProvinceCombobox;
import com.sqa.bhyt.dto.response.place.WardCombobox;
import com.sqa.bhyt.entity.Districts;
import com.sqa.bhyt.entity.Provinces;
import com.sqa.bhyt.entity.Wards;
import com.sqa.bhyt.repository.DistrictRepository;
import com.sqa.bhyt.repository.ProvinceRepository;
import com.sqa.bhyt.repository.WardsRepository;
import com.sqa.bhyt.service.client.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService {
	private static final Logger logger = LoggerFactory.getLogger(PlaceServiceImpl.class);

	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private WardsRepository wardsRepository;

	@Override
	public ServiceResponse<List<ProvinceCombobox>> findProvince() {
		try {
			Sort sort = Sort.by("code").ascending();
			List<Provinces> list = provinceRepository.findAll(sort);
			if(list.size() == 0) {
				return new ServiceResponse<List<ProvinceCombobox>>(MessageCode.LIST_IS_EMPTY,
						MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
			List<ProvinceCombobox> response = new ArrayList<>();
			
			ProvinceCombobox notSelected = new ProvinceCombobox();
			notSelected.setCode("00");
			notSelected.setCodeName("not_selected");
			notSelected.setName("--Chọn Tỉnh/Thành phố--");
			notSelected.setFullName("--Chọn Tỉnh/Thành phố--");
			response.add(notSelected);
				
			for(Provinces i : list) {
				ProvinceCombobox province = new ProvinceCombobox();
				province.setCode(i.getCode());
				province.setCodeName(i.getCodeName());
				province.setName(i.getName());
				province.setFullName(i.getFullName());
				
				response.add(province);
			}
				
			return new ServiceResponse<List<ProvinceCombobox>>(MessageCode.SUCCESS,
					MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<ProvinceCombobox>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE,
					null);
		}
	}

	@Override
	public ServiceResponse<List<DistrictCombobox>> findDistictByProvince(FilterDistrictByProvince request) {
		try {
			
			if(!StringUtils.hasText(request.getProvinceCode())) {
				return new ServiceResponse<List<DistrictCombobox>>(MessageCode.PROVINE_CODE_NOT_EMPTY,
						MessageCode.PROVINE_CODE_NOT_EMPTY_MESSAGE, null);
			}
			
			if(request.getProvinceCode().equals("00")) {
				return new ServiceResponse<List<DistrictCombobox>>(MessageCode.PROVINE_NOT_EMPTY,
						MessageCode.PROVINE_NOT_EMPTY_MESSAGE, null);
			}
//			Sort sort = Sort.by("code").ascending();
			List<Districts> list = districtRepository.findByProvinceCode(request.getProvinceCode());
			if(list.size() == 0) {
				return new ServiceResponse<List<DistrictCombobox>>(MessageCode.LIST_IS_EMPTY,
						MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			
			}
			
			List<DistrictCombobox> response = new ArrayList<>();
			
			DistrictCombobox notSelected = new DistrictCombobox();
			notSelected.setCode("000");
			notSelected.setCodeName("not_selected");
			notSelected.setName("--Chọn huyện--");
			notSelected.setFullName("--Chọn huyện--");
			response.add(notSelected);

			for(Districts i : list) {
				DistrictCombobox district = new DistrictCombobox();
				district.setCode(i.getCode());
				district.setCodeName(i.getCodeName());
				district.setName(i.getName());
				district.setFullName(i.getFullName());
				response.add(district);
			}
				
			return new ServiceResponse<List<DistrictCombobox>>(MessageCode.SUCCESS,
					MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<DistrictCombobox>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE,
					null);
		}
	}

	@Override
	public ServiceResponse<List<WardCombobox>> findWardByDistrict(FilterWardByDistrict request) {
		try {
			
			if(request.getDistrictCode().equals("000")) {
				return new ServiceResponse<List<WardCombobox>>(MessageCode.PROVINE_NOT_EMPTY,
						MessageCode.PROVINE_NOT_EMPTY_MESSAGE, null);
			}
			
//			Sort sort = Sort.by("code").ascending();
			List<Wards> list = wardsRepository.findByDistrictCode(request.getDistrictCode());
			if(list.size() == 0) {
				return new ServiceResponse<List<WardCombobox>>(MessageCode.LIST_IS_EMPTY,
						MessageCode.LIST_IS_EMPTY_MESSAGE, null);
			}
			
		
			List<WardCombobox> response = new ArrayList<>();
			WardCombobox notSelected = new WardCombobox();
			notSelected.setCode("00000");
			notSelected.setCodeName("not_selected");
			notSelected.setName("--Chọn Phường/Xã--");
			notSelected.setFullName("--Chọn Phường/Xã--");
			response.add(notSelected);
			
			for(Wards i : list) {
				WardCombobox ward = new WardCombobox();
				ward.setCode(i.getCode());
				ward.setCodeName(i.getCodeName());
				ward.setName(i.getName());
				ward.setFullName(i.getFullName());
				response.add(ward);
			}
			
			return new ServiceResponse<List<WardCombobox>>(MessageCode.SUCCESS,
					MessageCode.SUCCESS_MESSAGE, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ServiceResponse<List<WardCombobox>>(MessageCode.FAILED, MessageCode.FAILED_MESSAGE,
					null);
		}
	}

}
