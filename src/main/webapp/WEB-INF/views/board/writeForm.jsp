<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
</head>
<body>
<form action="bInsert.do" method="post" enctype="multipart/form-data">
		<table align="center">
			<tr>
				<td>제목</td>
				<td><input type="text" name="board_title"></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="board_writer"></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<!-- 원래 vo에 있는 이름 board_file 을 사용하게 되면 String 형태여야 함. file 형태로 가져가야 하므로 name을 vo의 field명과 다르게 지정함. -->
				<td><input type="file" name="upfile"></td>
			</tr>
			<tr>
				<td>글비밀번호</td>
				<td><input type="text" name="board_pwd"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><input type="text" name="board_content"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<input type="submit" value="등록하기"> &nbsp;&nbsp;
				<a href="bList.do">목록으로</a>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>