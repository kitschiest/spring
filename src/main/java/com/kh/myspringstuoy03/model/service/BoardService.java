package com.kh.myspringstuoy03.model.service;

import java.util.List;

import com.kh.myspringstuoy03.model.domain.Board;

public interface BoardService {
	
	int listCount();
	
	int insertBoard(Board b);
	
	Board selectBoard(int chk, String board_num);
	
	List<Board> selectList(int startPage, int limit);
	
	List<Board> selectList();
	
	List<Board> selectSearch(String keyword);
	
	Board selectOne(String board_num);
	
	Board updateBoard(Board b);
	
	int deleteBoard(String board_num);

}
