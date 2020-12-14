<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js">
//webapp까지의 경로

</script>
<title>Insert title here</title>
</head>
<body>
<form action="bUpdate.do" method="post" enctype="multipart/form-data" name="renewForm">
		<input type="hidden" name="board_num" value="${board.board_num }">
		<input type="hidden" name="board_file" value="${board.board_file }">
		<table align="center">
			<tr>
				<td>제목</td>
				<td><input type="text" name="board_title" value="${board.board_title }"></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="board_writer" value="${board.board_writer }"></td>
			</tr>
			<tr>
			<td>첨부파일</td>
				<!-- 원래 vo에 있는 이름 board_file 을 사용하게 되면 String 형태여야 함. file 형태로 가져가야 하므로 name을 vo의 field명과 다르게 지정함. -->
				<td>
				<c:if test="${empty board.board_file }"> 
				첨부된 파일이 없습니다.
				</c:if>
				<c:if test="${!empty board.board_file }">
				<a href="${pageContext.request.contextPath }/resources/uploadFiles/${board.board_file }">${board.board_file }</a> <!-- click을 하면 이미지가 보여지게끔 -->
				<!-- contextPath까지하면 myspringstuoy01 -->
				</c:if> <!-- choose else if 를 안쓰니까 이렇게 구성 -->
				</td>
			</tr>
			<tr>
				<td>변경할 첨부파일</td>
				<!-- 원래 vo에 있는 이름 board_file 을 사용하게 되면 String 형태여야 함. file 형태로 가져가야 하므로 name을 vo의 field명과 다르게 지정함. -->
				<td><input type="file" name="upfile" multiple></td>
			</tr>
			<tr>
				<td>글비밀번호</td>
				<td><input type="password" name="board_pwd"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><input type="text" name="board_content" value="${board.board_content }"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<input type="submit" value="수정하기"> &nbsp;&nbsp;
				<a href="bList.do">목록으로</a>
				</td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">

	$(function(){
		//비밀번호 똑같은지 확인
		$('form[name=renewForm]').on('submit', function(event){
			if($('input[name=board_pwd]').val() != "${board.board_pwd}"){
				alert("비밀번호가 일치하지 않습니다.");
				event.preventDefault(); //지금까지 쌓여있는 이벤트 모조리 삭제, submit 이벤트도 삭제된다.
			} else {
				return true; //남아있는 다음 동작들 실행
			}
		});
		
	});

</script>



</html>