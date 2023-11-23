package com.ssafy.enjoytrip.map.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PlanDetailDto {
	private String planId;
	private String contentTypeId;
	private String attractionId;
	private String date;
	private String price;
	private String durationTime;
	private String memo;
}
