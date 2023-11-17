package com.example.trip.member.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trip.member.mapper.MemberMapper;
import com.example.trip.member.model.MemberDto;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper memberMapper;
	
	@Override
	public MemberDto login(MemberDto dto) throws Exception{
		// TODO Auto-generated method stub
		return memberMapper.login(dto);
	}


	@Override
	public void join(MemberDto dto) throws Exception{
		// TODO Auto-generated method stub
		memberMapper.join(dto);
		return;
	}


	@Override
	public int idCheck(String userId) throws Exception{
		return memberMapper.idCheck(userId);
	}


	@Override
	public MemberDto getMember(String userId) throws Exception {
		return memberMapper.getMember(userId);
	}


	@Override
	public void deleteMember(String userId) throws Exception {
		memberMapper.deleteMember(userId);
		return;
		
	}

	
	@Override
	public void updateMember(MemberDto dto) throws Exception {
		memberMapper.updateMember(dto);
		return;
	}

}
