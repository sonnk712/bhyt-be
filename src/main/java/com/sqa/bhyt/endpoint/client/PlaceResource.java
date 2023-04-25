package com.sqa.bhyt.endpoint.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.bhyt.common.dto.response.ServiceResponse;
import com.sqa.bhyt.dto.request.place.FilterDistrictByProvince;
import com.sqa.bhyt.dto.request.place.FilterWardByDistrict;
import com.sqa.bhyt.dto.response.place.DistrictCombobox;
import com.sqa.bhyt.dto.response.place.ProvinceCombobox;
import com.sqa.bhyt.dto.response.place.WardCombobox;
import com.sqa.bhyt.service.client.PlaceService;

@RestController
@RequestMapping("/api/v1/place")
public class PlaceResource {
	@Autowired
	PlaceService placeService; 
	
	@GetMapping("/province")
	public ResponseEntity<ServiceResponse<List<ProvinceCombobox>>> findProvind(){
		
		ServiceResponse<List<ProvinceCombobox>> result = placeService.findProvince();
		
		return new ResponseEntity<ServiceResponse<List<ProvinceCombobox>>>(result, HttpStatus.OK);	
	}
	
	@PostMapping("/district-by-province")
	public ResponseEntity<ServiceResponse<List<DistrictCombobox>>> findDistictByProvince(@RequestBody FilterDistrictByProvince request){
		
		ServiceResponse<List<DistrictCombobox>> result = placeService.findDistictByProvince(request);
		
		return new ResponseEntity<ServiceResponse<List<DistrictCombobox>>>(result, HttpStatus.OK);	
	}
	
	@PostMapping("/ward-by-district")
	public ResponseEntity<ServiceResponse<List<WardCombobox>>> findWardByDistrict(@RequestBody FilterWardByDistrict request){
		
		ServiceResponse<List<WardCombobox>> result = placeService.findWardByDistrict(request);
		
		return new ResponseEntity<ServiceResponse<List<WardCombobox>>>(result, HttpStatus.OK);	
	}
}
