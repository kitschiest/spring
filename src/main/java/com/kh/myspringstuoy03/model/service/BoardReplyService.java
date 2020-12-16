package com.kh.myspringstuoy03.model.service;

import java.util.List;

import com.kh.myspringstuoy03.model.domain.BoardReply;

public interface BoardReplyService {

	List<BoardReply> selectBoardReplyAll(String board_num);
	
	BoardReply selectBoardReply(String comment_id);
	
	int insertBoardReply(BoardReply br);
	
	int updateBoardReply(BoardReply br);
	
	int deleteBoardReply(BoardReply br);
	
	
}
