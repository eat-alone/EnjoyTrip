package com.ssafy.enjoytrip.map.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.BoardListDto;
import com.ssafy.enjoytrip.map.model.AttractionInfo;
import com.ssafy.enjoytrip.map.model.AttractionInfoList;
import com.ssafy.enjoytrip.map.model.PlanListDto;
import com.ssafy.enjoytrip.map.model.SidoGugunCodeDto;
import com.ssafy.enjoytrip.map.model.mapper.MapMapper;

@Service
public class MapServiceImpl implements MapService {
	
	private MapMapper mapMapper;

	public MapServiceImpl(MapMapper mapMapper) {
		super();
		this.mapMapper = mapMapper;
	}

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return mapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return mapMapper.getGugunInSido(sido);
	}

	@Override
	public List<SidoGugunCodeDto> getContentType() throws Exception {
		return mapMapper.getContentType();
	}

	@Override
	public AttractionInfoList getAttInfoList(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		System.out.println(map.get("sido"));
		int sidoCode = Integer.parseInt(map.get("sido") == "" ? "0" : map.get("sido"));
		int gugunCode = Integer.parseInt(map.get("gugun") == "" ? "0" : map.get("gugun"));
		int contentType = Integer.parseInt(map.get("category") == "" ? "0" : map.get("category"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("gugun", gugunCode);
		param.put("sido", sidoCode);
		param.put("start", start);
		param.put("listsize", sizePerPage);
		param.put("contenttype", contentType);

		List<AttractionInfo> list = mapMapper.getAttInfoList(param);
		int totalArticleCount = mapMapper.getTotalAttCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		
		AttractionInfoList attlist = new AttractionInfoList();
		attlist.setAttInfo(list);
		attlist.setCurrentPage(currentPage);
		attlist.setTotalPageCount(totalPageCount);
		
		return attlist;
	}

	@Override
	public void saveUserPlan(Map<String, Object> map) throws Exception {
		System.out.println(map);
		map.put("planId", null);
		int userId = Integer.parseInt((String) map.get("userId"));
		map.put("userId", userId);
		mapMapper.saveTripPlan(map);
		mapMapper.saveTripDetail(map);
		mapMapper.userAndPlan(map);
	}
	

	public List<AttractionInfo> hotAttractionList() throws Exception {
		return mapMapper.hotAttractionList();
	}

	@Override
	public List<PlanListDto> getMyPlanList(String userId) throws Exception {
		return mapMapper.getMyPlanList(userId);
	}

	@Override
	public void inviteMember(Map<String, Object> map) throws Exception {
		mapMapper.inviteMember(map);
		
	}


}
