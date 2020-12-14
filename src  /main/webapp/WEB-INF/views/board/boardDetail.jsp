<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세 조회</title>
</head>
<body>
	
		<!-- writeForm 복붙 -->
	<table align="center">
			<tr>
				<th colspan="2">${board.board_num }번 글 상세보기</th> <!-- addObject에서 'board'라고 선언 -->
			</tr>
			<tr>
				<td>제목</td>
				<td>${board.board_title }</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${board.board_writer }</td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<!-- 원래 vo에 있는 이름 board_file 을 사용하게 되면 String 형태여야 함. file 형태로 가져가야 하므로 name을 vo의 field명과 다르게 지정함. -->
				<td><c:if test="${empty board.board_file }"> 
				첨부된 파일이 없습니다.
				</c:if>
				<c:if test="${!empty board.board_file }">
				<a href="${pageContext.request.contextPath }/resources/uploadFiles/${board.board_file }" download>>${board.board_file }</a> <!-- click을 하면 이미지가 보여지게끔 -->
				<!-- contextPath까지하면 myspringstuoy01 -->
				</c:if> <!-- choose else if 를 안쓰니까 이렇게 구성 -->
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>${board.board_content }</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<c:url var="bdel" value="bDelete.do">
					<c:param name="board_num" value="${board.board_num }"/>
				</c:url>
				<c:url var="brenew" value="bRenew.do">
					<c:param name="board_num" value="${board.board_num }"/>
				</c:url>
				<!-- var는 이름 -->
				<a href="${brenew }">수정하기</a> 
				&nbsp;&nbsp;
				<a href="${bdel }">삭제하기</a>  <!-- c:url에 걸려있는 value로 들어가게 된다. -->
				&nbsp;&nbsp;
				<a href="bList.do">목록으로</a>
				</td>
			</tr>
		</table>


</body>
</html>