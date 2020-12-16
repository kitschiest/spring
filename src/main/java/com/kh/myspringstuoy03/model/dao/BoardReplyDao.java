package com.kh.myspringstuoy03.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.myspringstuoy03.model.domain.BoardReply;

@Repository("bReDao")
public class BoardReplyDao {
	
	@Autowired
	private SqlSession sqls;
	
	public List<BoardReply> selectBoardReplyAll(String board_num){
		return sqls.selectList("BoardReply.selectBoardReplyAll", board_num);
	}
	
	//<select id="selectBoardReply" parameterType="string" resultType="BoardReply" resultMap="resultBoardReply">
	public BoardReply selectBoardReply(String comment_id) {
		return sqls.selectOne("BoardReply.selectBoardReply", comment_id);
	}
	//<insert id="insertBoardReply" parameterType="BoardReply" flushCache="true" statementType="PREPARED" timeout="20">
	public int insertBoardReply(BoardReply br) {
		return sqls.insert("BoardReply.insertBoardReply", br);
	}
	//<update id="updateBoardReply" parameterType="BoardReply" flushCache="true" statementType="PREPARED" timeout="20">
	public int updateBoardReply(BoardReply br) {
		return sqls.update("BoardReply.updateBoardReply", br);
	}
	//<delete id="deleteBoardReply" parameterType="BoardReply" flushCache="true" statementType="PREPARED" timeout="20"> DELETE FROM BOARD_COMMENT
	public int deleteBoardReply(BoardReply br) {
		return sqls.delete("BoardReply.deleteBoardReply", br);
	}
}
