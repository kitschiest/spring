package com.kh.myspringstuoy03.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.myspringstuoy03.model.dao.BoardDao;
import com.kh.myspringstuoy03.model.domain.Board;

@Service("bService")
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao bDao;

	@Override
	public int listCount() {
		// TODO Auto-generated method stub
		return bDao.listCount();
	}

	@Override
	public int insertBoard(Board b) {
		// TODO Auto-generated method stub
		return bDao.insertBoard(b);
	}

	@Override
	public List<Board> selectList() {
		// TODO Auto-generated method stub
		return bDao.selectList();
	}

	@Override
	public Board selectOne(String board_num) {
		// TODO Auto-generated method stub
		return bDao.selectOne(board_num);
	}

	@Override
	public Board updateBoard(Board b) {
		int result = bDao.updateBoard(b);
		System.out.println("result 1 : "+ result);
		if(result > 0) {
			System.out.println("result 2 : "+ result);
			b = bDao.selectOne(b.getBoard_num());
		} else {
//			throw new MemberNotFoundException();
			System.out.println("result 3 : "+ result);
			b = null;
		}
		return b;
	}

	@Override
	public int deleteBoard(String board_num) {
		// TODO Auto-generated method stub
		return bDao.deleteBoard(board_num);
	}

	@Override
	public Board selectBoard(int chk, String board_num) {
		if (chk == 0) bDao.addReadCount(board_num);
		return bDao.selectOne(board_num);
	}

	@Override
	public List<Board> selectList(int startPage, int limit) {
		return bDao.selectList(startPage, limit);
	}

	@Override
	public List<Board> selectSearch(String keyword) {
		return bDao.searchList(keyword);
	}

}
