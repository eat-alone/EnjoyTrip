package com.example.trip.attraction.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trip.attraction.model.AttractionInfoDto;
import com.example.trip.attraction.model.searchParamDto;
import com.example.trip.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService{
	@Autowired
	AttractionMapper mapper;
	
	@Override
	public List<AttractionInfoDto> search(searchParamDto dto) {
		return mapper.search(dto);
	}

}
