package com.ssafy.enjoytrip.member.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.member.model.MemberLetterDto;
import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.member.model.MemberListDto;

public interface MemberService {

	public MemberDto login(MemberDto dto) throws Exception;
	public void join(MemberDto dto) throws Exception;
	public int idCheck(String userId) throws Exception;
	public MemberDto getMember(String userId) throws Exception;
	public void deleteMember(String userId) throws Exception;
	public void updateMember(MemberDto dto) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	MemberDto userInfo(String userId) throws Exception;
	public List<MemberListDto> listfollow(String userId) throws Exception;
	public List<MemberListDto> listfollower(String userId) throws Exception;
	public void unFollow(String toId, String fromId) throws Exception;
	public void following(String toId, String fromId) throws Exception;
	public List<MemberListDto> listSearchUser(String userId) throws Exception;
	public List<MemberLetterDto> receiveGetList(String userId) throws Exception;
	public MemberLetterDto getDetailLetter(String userId) throws Exception;
	public void sendLetter(MemberLetterDto dto) throws Exception;
	public void isReadCheck(String contentId) throws Exception;
//	public MemberDto (MemberDto dto);
}
