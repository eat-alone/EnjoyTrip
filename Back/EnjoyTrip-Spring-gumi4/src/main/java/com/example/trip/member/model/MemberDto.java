package com.example.trip.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MemberDto {
	public String userId;
	public String userName;
	public String userPwd;
	public String joinDate;
}
