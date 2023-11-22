package com.ssafy.enjoytrip.map.model;

import java.util.List;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttractionInfoList {
	@ApiModelProperty(value = "관광지목록")
	private List<AttractionInfo> attInfo;
	@ApiModelProperty(value = "현재 페이지번호")
	private int currentPage;
	@ApiModelProperty(value = "전체 페이지 수")
	private int totalPageCount;
}
