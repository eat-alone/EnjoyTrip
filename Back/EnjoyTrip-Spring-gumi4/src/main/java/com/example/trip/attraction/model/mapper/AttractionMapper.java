package com.example.trip.attraction.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.trip.attraction.model.AttractionInfoDto;
import com.example.trip.attraction.model.searchParamDto;

@Mapper
public interface AttractionMapper {
	public List<AttractionInfoDto> search(searchParamDto dto);
}
