package com.stuoy.myspringstuoy01.model.service;

import java.util.List;

import com.stuoy.myspringstuoy01.model.domain.Board;

public interface BoardService { //같은 package 안에 있으니까 public 안쓰는게 더 좋은 코드다
	 int listCount();
	
	 int insertBoard(Board b);
	
	 List<Board> selectList(); //요건 전체 읽기라 bDetail을 위해선 하나만 읽기 만들어야해 Mapper부터 손봐야해
	
	 Board selectOne(String board_num);
	
//	 int addReadCount(String board_num);
	
	 Board updateBoard(Board b);
	
	 int deleteBoard(String board_num);
}
