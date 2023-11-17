package com.example.trip.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.trip.member.model.MemberDto;
import com.example.trip.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	
	@PostMapping("/login")
	public ResponseEntity<MemberDto> login(@ApiParam(value = "로그인을 위한 부가정보.", required = true) MemberDto dto) {
		MemberDto resultDto;
		try {
			resultDto = memberService.login(dto);
			if(resultDto != null) {
				return new ResponseEntity<MemberDto>(resultDto, HttpStatus.OK);
			}
			resultDto = new MemberDto();
			return new ResponseEntity<MemberDto>(resultDto, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultDto = new MemberDto();
			return new ResponseEntity<MemberDto>(resultDto, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/join", produces = "applicaion/json")
	public ResponseEntity<MemberDto> join(@RequestBody MemberDto dto) {
		try {
			System.out.println(dto);
			memberService.join(dto);
			
			MemberDto resultDto = memberService.getMember(dto.getUserId());
			if(resultDto != null) {
				System.out.println("ㄱㅊㄱㅊ");
				return new ResponseEntity<MemberDto>(resultDto, HttpStatus.OK);
			}
			resultDto = new MemberDto();
			return new ResponseEntity<MemberDto>(resultDto, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	@GetMapping("/idCheck")
	public ResponseEntity<?> idCheck(String userId) {
		int value;
		try {
			value = memberService.idCheck(userId);
			if(value == 0) {
				return new ResponseEntity<String>("사용할 수 있는 아이디입니다.", HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("중복되는 아이디가 존재합니다.", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("에러 발생!", HttpStatus.OK);
		}
	}
	
	@GetMapping("/deleteMember")
	public ResponseEntity<?> deleteMember(String userId) {
		int value;
		try {
			value = memberService.idCheck(userId);
			if(value == 0) {
				return new ResponseEntity<String>("탈퇴불가! : 존재하지 않는 아이디", HttpStatus.OK);
			}
			memberService.deleteMember(userId);
			return new ResponseEntity<String>("탈퇴되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("에러 발생!", HttpStatus.OK);
		}
	}
	
	@GetMapping("/updateMember")
	public ResponseEntity<MemberDto> updateMember(MemberDto dto) {
		MemberDto resultDto;
		System.out.println(dto);
		try {
			memberService.updateMember(dto); //업데이트 시킨 후
			
			//해당 아이디의 사람의 정보를 반환
			resultDto = memberService.getMember(dto.getUserId());
			if(resultDto != null) {
				return new ResponseEntity<MemberDto>(resultDto, HttpStatus.OK);
			}
			resultDto = new MemberDto();
			return new ResponseEntity<MemberDto>(resultDto, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
