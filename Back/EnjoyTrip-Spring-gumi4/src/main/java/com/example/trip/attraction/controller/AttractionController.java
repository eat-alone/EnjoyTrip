package com.example.trip.attraction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.trip.attraction.model.AttractionInfoDto;
import com.example.trip.attraction.model.searchParamDto;
import com.example.trip.attraction.model.service.AttractionService;

@Controller
@RequestMapping("/attraction")
public class AttractionController {
	@Autowired
	AttractionService attrService;
	
	@GetMapping("/hello")
	public ResponseEntity<?> hello() {
		return new ResponseEntity<String>("hello", HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<AttractionInfoDto>> search(searchParamDto dto) {
		//areaCode, contentTypeId, keyword 3가지 요청값에 대한 dto 입력
		List<AttractionInfoDto> list;
		System.out.println(dto);
		list = attrService.search(dto);
		return new ResponseEntity<List<AttractionInfoDto>>(list, HttpStatus.OK);
	}
}
