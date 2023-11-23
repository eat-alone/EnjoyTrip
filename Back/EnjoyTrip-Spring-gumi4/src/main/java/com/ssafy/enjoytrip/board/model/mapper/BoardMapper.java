package com.ssafy.enjoytrip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.CommentDto;
import com.ssafy.enjoytrip.board.model.FileInfoDto;

@Mapper
public interface BoardMapper {

	void writeArticle(BoardDto boardDto) throws SQLException;

	void registerFile(BoardDto boardDto) throws Exception;
//
	List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;
	
//
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
//
	BoardDto getArticle(int articleNo) throws SQLException;

	void updateHit(int articleNo) throws SQLException;

	void modifyArticle(BoardDto boardDto) throws SQLException;

	void deleteFile(int articleNo) throws Exception;

	void deleteArticle(int articleNo) throws SQLException;

	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
	FileInfoDto fileInfo(String saveFile) throws Exception;
	
	List<BoardDto> getHotArticleList() throws Exception;
	
	List<CommentDto> listComment(String articleNo) throws Exception;
	
	void registComment(CommentDto dto) throws Exception;
	
	void deleteComment(int commentId) throws Exception;
	
//	
}
