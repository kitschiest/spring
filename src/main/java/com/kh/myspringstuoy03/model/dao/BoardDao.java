package com.kh.myspringstuoy03.model.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.kh.myspringstuoy03.model.domain.Board;

@Repository("bDao")
public class BoardDao {
	@Autowired
	SqlSession sqls;
	
	public int listCount() {
		return sqls.selectOne("Board.listCount");
	}
	
	public Board selectOne(String board_num){
		return sqls.selectOne("Board.searchOne", board_num); //mapper에 id부분이랑 같아야
	}
	
	public int insertBoard(Board b) {
		return sqls.insert("Board.insertBoard", b);
	}
	
	public List<Board> selectList(){ //여기에만 around로 걸리게 pointcut
		return sqls.selectList("Board.selectList");
	}
	
	public List<Board> searchList(String keyword) { // 게시글 검색 조회 return sqlSession.selectList("Board.searchList", keyword);
		return sqls.selectList("Board.searchList", keyword);
	}
	
	public List<Board> selectList(int startPage, int limit){ // 특정 페이지 단위의 게시글 조회
		int startRow = (startPage-1)*limit;
		RowBounds row = new RowBounds(startRow, limit);
		return sqls.selectList("Board.selectList", null, row);
	}
	
	public int addReadCount(String board_num){
		return sqls.update("Board.addReadCount", board_num); //mapper에 id부분이랑 같아야
	}
	
	public int updateBoard(Board b){
		return sqls.update("Board.updateBoard", b); //mapper에 id부분이랑 같아야
	}
	
	public int deleteBoard(String board_num){
		return sqls.delete("Board.deleteBoard", board_num); //mapper에 id부분이랑 같아야
	}
	
}
