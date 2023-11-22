package com.ssafy.enjoytrip.member.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Data
public class MemberLetterDto {
	public String Id;
	public String fromId;
	public String nickName;
	public String toId;
	public String subject;
	public String content;
	public String sendDate;
	public String isRead;
}
