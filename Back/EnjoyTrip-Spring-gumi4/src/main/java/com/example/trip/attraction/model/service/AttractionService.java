package com.example.trip.attraction.model.service;

import java.util.List;

import com.example.trip.attraction.model.AttractionInfoDto;
import com.example.trip.attraction.model.searchParamDto;

public interface AttractionService {
	public List<AttractionInfoDto> search(searchParamDto dto);
}
