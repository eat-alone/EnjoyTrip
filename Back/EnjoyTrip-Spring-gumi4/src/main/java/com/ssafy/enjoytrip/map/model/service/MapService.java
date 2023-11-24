package com.ssafy.enjoytrip.map.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.map.model.AttractionInfo;
import com.ssafy.enjoytrip.map.model.AttractionInfoList;
import com.ssafy.enjoytrip.map.model.PlanInfoDto;
import com.ssafy.enjoytrip.map.model.SidoGugunCodeDto;

public interface MapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<SidoGugunCodeDto> getContentType() throws Exception;
	AttractionInfoList getAttInfoList(Map<String, String> map) throws Exception;

	void saveUserPlan(Map<String, Object>map) throws Exception;

	List<AttractionInfo> hotAttractionList() throws Exception;
	List<PlanInfoDto> getMyPlanList(String userId) throws Exception;
	void inviteMember(Map<String, Object> map) throws Exception;

}
