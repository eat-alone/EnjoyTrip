package com.example.trip.member.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.example.trip.member.model.MemberDto;

@Mapper
public interface MemberMapper {
	public MemberDto login(MemberDto dto) throws SQLException;
	public int idCheck(String userId) throws SQLException;
	public void join(MemberDto dto) throws SQLException;
	public MemberDto getMember(String userId) throws SQLException;
	public void deleteMember(String userId) throws SQLException;
	public void updateMember(MemberDto dto) throws Exception;
}
