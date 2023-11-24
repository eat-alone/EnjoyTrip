package com.ssafy.enjoytrip.map.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.map.model.AttractionInfo;
import com.ssafy.enjoytrip.map.model.PlanInfoDto;
import com.ssafy.enjoytrip.map.model.SidoGugunCodeDto;

public interface MapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<SidoGugunCodeDto> getContentType() throws SQLException;
	List<AttractionInfo> getAttInfoList(Map<String, Object> param) throws SQLException;
	int getTotalAttCount(Map<String, Object> param) throws SQLException;

	void saveTripPlan(Map<String, Object> param) throws SQLException;
	void saveTripDetail(Map<String, Object> param) throws SQLException;
	void userAndPlan(Map<String, Object> param) throws SQLException;

	List<AttractionInfo> hotAttractionList() throws Exception;

	List<PlanInfoDto> getMyPlanList(String userId) throws Exception;
	void inviteMember(Map<String, Object> map) throws Exception;
}
