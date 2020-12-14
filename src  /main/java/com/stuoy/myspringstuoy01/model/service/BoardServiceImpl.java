package com.stuoy.myspringstuoy01.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stuoy.myspringstuoy01.model.dao.BoardDao;
import com.stuoy.myspringstuoy01.model.domain.Board;

@Service("bService")
public class BoardServiceImpl implements BoardService{
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
		bDao.addReadCount(board_num); //조회수 하나 올려주고
		return bDao.selectOne(board_num); //selectOne한다.
		
	}

//	@Override
//	public int addReadCount(String board_num) {
//		// TODO Auto-generated method stub
//		return bDao.addReadCount(board_num);
//	} selectOne 안에 들어가게 되어서 굳이 따로 쓰지 않아도 돼

	@Override
	public Board updateBoard(Board b) { //Board b 는 업데이트 하려고 실어온 Board //update성공하면 변경된 값들 출력되게끔 의도
		
		// TODO Auto-generated method stub
		int result = bDao.updateBoard(b);
		if(result > 0) {
			b = bDao.selectOne(b.getBoard_num());
		} else {
			b = null;
		}
		return b;
	}

	@Override
	public int deleteBoard(String board_num) { //교재에는 void로 해서 리턴값 안받는데, 그냥 다 상관없다 (기능적인 측면에서)
		// TODO Auto-generated method stub
		return bDao.deleteBoard(board_num);
	}
	
	

}
