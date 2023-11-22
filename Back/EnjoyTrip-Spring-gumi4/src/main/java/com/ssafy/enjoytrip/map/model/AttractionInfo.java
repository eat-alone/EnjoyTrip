package com.ssafy.enjoytrip.map.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "attractionInfo : 관광지 정보", description = "관광지 정보에 대한 내용을 나타낸다.")
public class AttractionInfo {
	@ApiModelProperty(value = "컨텐츠아이디")
	private String contentId;
	@ApiModelProperty(value = "컨텐츠타입")
	private String contentTypeId;
	@ApiModelProperty(value = "관광지명")
	private String title;
	@ApiModelProperty(value = "관광지주소")
	private String addr1;
	@ApiModelProperty(value = "관광지주소")
	private String addr2;
	@ApiModelProperty(value = "zipcode")
	private String zipcode;
	@ApiModelProperty(value = "전화번호")
	private String tel;
	@ApiModelProperty(value = "이미지")
	private String firstImage;
	@ApiModelProperty(value = "이미지")
	private String firstImage2;
	@ApiModelProperty(value = "검색횟수")
	private String readCount;
	@ApiModelProperty(value = "시도코드")
	private String sidoCode;
	@ApiModelProperty(value = "구군코드")
	private String gugunCode;
	@ApiModelProperty(value = "위도")
	private String latitude;
	@ApiModelProperty(value = "경도")
	private String longitude;
	@ApiModelProperty(value = "maplevel")
	private String mlevel;
}
