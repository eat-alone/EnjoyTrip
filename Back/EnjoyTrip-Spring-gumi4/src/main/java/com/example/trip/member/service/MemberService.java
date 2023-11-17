package com.example.trip.member.service;

import java.sql.SQLException;

import com.example.trip.member.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto dto) throws Exception;
	public void join(MemberDto dto) throws Exception;
	public int idCheck(String userId) throws Exception;
	public MemberDto getMember(String userId) throws Exception;
	public void deleteMember(String userId) throws Exception;
	public void updateMember(MemberDto dto) throws Exception;
//	public MemberDto (MemberDto dto);
}
