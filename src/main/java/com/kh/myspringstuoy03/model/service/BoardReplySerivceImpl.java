package com.kh.myspringstuoy03.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.myspringstuoy03.model.dao.BoardReplyDao;
import com.kh.myspringstuoy03.model.domain.BoardReply;

@Service("bReService")
public class BoardReplySerivceImpl implements BoardReplyService {
	
	@Autowired
	private BoardReplyDao bReDao;

	@Override
	public List<BoardReply> selectBoardReplyAll(String board_num) {
		// TODO Auto-generated method stub
		return bReDao.selectBoardReplyAll(board_num);
	}

	@Override
	public BoardReply selectBoardReply(String comment_id) {
		// TODO Auto-generated method stub
		return bReDao.selectBoardReply(comment_id);
	}

	@Override
	public int insertBoardReply(BoardReply br) {
		// TODO Auto-generated method stub
		return bReDao.insertBoardReply(br);
	}

	@Override
	public int updateBoardReply(BoardReply br) {
		// TODO Auto-generated method stub
		return bReDao.updateBoardReply(br);
	}

	@Override
	public int deleteBoardReply(BoardReply br) {
		// TODO Auto-generated method stub
		return bReDao.deleteBoardReply(br);
	}
	
}
