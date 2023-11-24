package com.ssafy.enjoytrip.map.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.board.model.BoardListDto;
import com.ssafy.enjoytrip.map.model.AttractionInfo;
import com.ssafy.enjoytrip.map.model.AttractionInfoList;
import com.ssafy.enjoytrip.map.model.PlanInfoDto;
import com.ssafy.enjoytrip.map.model.SidoGugunCodeDto;
import com.ssafy.enjoytrip.map.model.service.MapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/map")
@Api("Map 컨트롤러  API V1")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MapController {
	
	private MapService mapService;

	public MapController(MapService mapService) {
		super();
		this.mapService = mapService;
	}

	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		log.info("sido - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(mapService.getSido(), HttpStatus.OK);
	}

	@ApiOperation(value = "컨텐트 타입 정보", notes = "컨텐트 타입을 반환한다.", response = List.class)
	@GetMapping("/type")
	public ResponseEntity<List<SidoGugunCodeDto>> type() throws Exception {
		log.info("type - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(mapService.getContentType(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "구군 정보", notes = "시도에 속한 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun/{sidoCode}")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(
			@PathVariable("sidoCode") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		log.info("gugun - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(mapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@ApiOperation(value = "관광지 정보", notes = "관광지 정보를 반환한다.", response = List.class)
	@GetMapping("/attraction")
	public ResponseEntity<?> listAttraction(
			@RequestParam @ApiParam(value = "관광지 정보를 얻기 위한 부가 정보", required = true) Map<String, String> map) throws Exception {
		log.info("listAttraction map - {}", map);
		try {
			AttractionInfoList attInfoList = mapService.getAttInfoList(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(attInfoList);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "플랜 정보", notes = "유저의 플랜정보를 반환한다.", response = List.class)
	@GetMapping("/getmyplanlist/{userId}")
	public ResponseEntity<?> getMyPlanList(
			@PathVariable("userId") @ApiParam(value = "플랜 정보를 얻기 위한 아이디값", required = true) String userId) throws Exception {
		try {
			System.out.println(userId +"*************8");
			List<PlanInfoDto> attInfoList = mapService.getMyPlanList(userId);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(attInfoList);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/invitemember")
	public ResponseEntity<?> inviteMember(
			@RequestBody @ApiParam(value = "플랜 초대를 위한 유저아이디", required = true) Map<String, Object> map) throws Exception {
		System.out.println(map);
		try {
			mapService.inviteMember(map);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("이미 초대된 사람입니다.", HttpStatus.OK);
		}
	}
	 
	@PostMapping("/saveplan")
	public ResponseEntity<?> saveUserPlan(
			@RequestBody @ApiParam(value = "사용자 여행 계획 저장을 위한 정보", required = true) Map<String, Object> map) throws Exception {
		log.info("saveUserPlan map - {}", map);
		try {
			mapService.saveUserPlan(map);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "인기 관광지 정보", notes = "인기 관광지 정보 4개를 반환한다.", response = List.class)
	@GetMapping("/hotattraction")
	public ResponseEntity<?> hotAttractionList() throws Exception {
		try {
			List<AttractionInfo> attInfoList = mapService.hotAttractionList();
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			System.out.println(attInfoList);
			return ResponseEntity.ok().headers(header).body(attInfoList);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
