package com.ssafy.enjoytrip.board.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "BoardRequestParam : 게시글 요청 파라미터", description = "게시글 리스트를 얻기위한 파라미터.")
public class BoardRequestParam {
	
	private int pgno;
	private int spp;
	private String key;
	private String word;
	private int boardType;
}
