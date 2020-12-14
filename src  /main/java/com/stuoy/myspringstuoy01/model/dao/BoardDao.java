package com.stuoy.myspringstuoy01.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stuoy.myspringstuoy01.model.domain.Board;

@Repository("bDao")
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	public int listCount() {
		return sqlSession.selectOne("Board.listCount");
	}
	
	public int insertBoard(Board b) {
		return sqlSession.insert("Board.insertBoard", b);
	}
	
	public List<Board> selectList(){
		return sqlSession.selectList("Board.selectList");
	}
	
	public Board selectOne(String board_num){
		return sqlSession.selectOne("Board.searchOne", board_num); //mapper에 id부분이랑 같아야
	}
	
	public int addReadCount(String board_num){
		return sqlSession.update("Board.addReadCount", board_num); //mapper에 id부분이랑 같아야
	}
	
	public int updateBoard(Board b){
		return sqlSession.update("Board.updateBoard", b); //mapper에 id부분이랑 같아야
	}
	
	public int deleteBoard(String board_num){
		return sqlSession.delete("Board.deleteBoard", board_num); //mapper에 id부분이랑 같아야
	}
	
	

}
