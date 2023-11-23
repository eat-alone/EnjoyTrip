package com.ssafy.enjoytrip.board.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@ApiModel(value = "CommentDto : 댓글정보", description = "댓글의 상세 정보를 나타낸다.")
public class CommentDto {
	private String id;
	private String boardId;
	private String userId; //댓글 삭제 권한 조회용 데이터
	private String userNickname; //보여질 댓글 주인 닉네임
	private String content;
	private String writeDate;
}
