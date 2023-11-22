package com.ssafy.enjoytrip.board.model;

import java.util.List;

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
@ApiModel(value = "BoardDto : 게시글정보", description = "게시글의 상세 정보를 나타낸다.")
public class BoardDto {
	@ApiModelProperty(value = "글번호")
	private int articleNo;
	@ApiModelProperty(value = "작성자 아이디")
	private int userId;
	@ApiModelProperty(value = "작성자 닉네임")
	private String userNickName;
	@ApiModelProperty(value = "글제목")
	private String subject;
	@ApiModelProperty(value = "글내용")
	private String content;
	@ApiModelProperty(value = "조회수")
	private int hit;
	@ApiModelProperty(value = "작성일")	
	private String registerTime;
	@ApiModelProperty(value = "업로드 파일정보")
	private List<FileInfoDto> fileInfos;
	@ApiModelProperty(value = "게시판 정보")
	private int boardType;
	@ApiModelProperty(value = "게시판 정보")
	private String thumbnail;

}
