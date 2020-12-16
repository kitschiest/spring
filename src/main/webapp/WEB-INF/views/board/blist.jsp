<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery- 3.2.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('form[name=listForm]').on(
				'submit',
				function(e) {
					if ($('input[name=keyword]').val() == null
							|| $('input[name=keyword]').val() == "") {
						alert("검색어를 입력해 주세요");
						e.preventDefault();
					} else {
						return true;
					}
				});
	});
	function showInsertForm() {
		location.href = "writeForm.do";
	}
</script>
</head>
<body bgcolor="#FFEFD5">
	<h1>Spring MVC 서버</h1>
	<hr>
	<p align="center">
	<h3>게 시 판</h3>
	<form method="get" name="listForm" action="bList.do">
		<input type="hidden" name="page" value="${currentPage}"> 
		<input type="text" name="keyword"> 
		<input type="submit" value="검색">
	</form>
	<table>
		<tr>
			<td align="right" colspan="5">
			<input type="button" value="전체목록" onclick="window.location='bList.do'"> 
			<input type="button" value="글쓰기" onclick="window.location='writeForm.do'">
			</td>
		</tr>
		<tr bgcolor="#FFD1B7">
			<td align="center" width="60">번호</td>
			<td align="center" width="380">제목</td>
			<td align="center" width="100">작성자</td>
			<td align="center" width="100">작성일</td>
			<td align="center" width="60">조회수</td>
		</tr>
		<!-- 글이 없을 경우 -->
		<c:if test="${listCount eq 0}">
			<tr>
				<td colspan="6" align="center"><br> <br> 게시판에 저장된 글이 없습니다.
				<br> 
				<br>
				</td>
			</tr>
		</c:if>
		<c:if test="${listCount ne 0}">
			<c:forEach var="vo" items="${list}" varStatus="status">
				<tr>
					<td align="center">${status.count}</td>
					<td align="left"><a
						href="bDetail.do?board_num=${vo.board_num}">
							&nbsp;${vo.board_title} </a></td>
					<td align="center">${vo.board_writer}</td>
					<td align="center">${vo.regDate}</td>
					<td align="center">${vo.read_count}</td>
				</tr>
			</c:forEach>
		</c:if>
		<!-- 앞 페이지 번호 처리 -->
		<tr align="center" height="20">
			<td colspan="5"><c:if test="${currentPage <= 1}">
			[이전]&nbsp; </c:if> <c:if test="${currentPage > 1}">
					<c:url var="blistST" value="bList.do">
						<c:param name="page" value="${currentPage-1}" />
					</c:url>
					<a href="${blistST}">[이전]</a>
				</c:if> <!-- 끝 페이지 번호 처리 --> <c:set var="endPage" value="${maxPage}" /> <c:forEach var="p" begin="${startPage+1}" end="${endPage}">
					<c:if test="${p eq currentPage}">
						<font color="red" size="4"><b>[${p}]</b></font>
					</c:if>
					<c:if test="${p ne currentPage}">
						<c:url var="blistchk" value="bList.do">
							<c:param name="page" value="${p}" />
						</c:url>
						<a href="${blistchk}">${p}</a>
					</c:if>
				</c:forEach> <c:if test="${currentPage >= maxPage}"> [다음] </c:if> <c:if
					test="${currentPage < maxPage}">
					<c:url var="blistEND" value="bList.do">
						<c:param name="page" value="${currentPage+1}" />
					</c:url>
					<a href="${blistEND}">[다음]</a>
				</c:if></td>
		</tr>
	</table>
	<br>
	<h4>
		<span>댓글 (${commentList.size()})</span>
	</h4>
	<c:if test="${!empty commentList}">
		<c:forEach var="rep" items="${commentList}">
			<div id="comment">
				<hr>
				<input type="hidden" id="rep_id" name="rep_id"
					value="${rep.comment_id}"> <input type="hidden"
					id="rep_pwd" name="rep_pwd" value="${rep.comment_pwd}">
				<h4 class="comment-head">작성자 : ${rep.comment_name} &nbsp;
					&nbsp;작성일 : ${rep.regdate}</h4>
				<div class="comment-body">
					<p>${rep.comments}</p>
				</div>
				<div class="comment-confirm" style="display: none;">
					비밀번호 확인 : <input type="password" name="pwd_chk">
				</div>
				<p align="right">
					<button type="button" class="updateConfirm" name="updateConfirm"
						id="updateConfirm" style="display: none;">수정완료</button>
					&nbsp;&nbsp;&nbsp;
					<button type="button" class="delete" name="delete" id="delete"
						style="display: none;">삭제하기</button>
					&nbsp;&nbsp;&nbsp;
					<button type="button" class="update" name="update" id="update">수
						정 및 삭제</button>
				</p>
			</div>
			<br>
			<br>
		</c:forEach>
	</c:if>
	<hr>
	<div class="comment-box">
		<form action="brInsert.do" id="replyForm" method="get">
			<input type="hidden" id="board_num" name="board_num"
				value="${board.board_num}"> <input type="hidden" id="page"
				name="page" value="${currentPage}"> <input type="hidden"
				id="comments" name="comments" value="">
			<p align="center">
				작성자 : <input type="text" name="comment_name" size="23">
				&nbsp;&nbsp;비밀번호 : <input type="password" name="comment_pwd"
					size="23"><br> <br>
				<textarea id="reply_contents" class="form-control" rows="6"
					cols="70%"
					onfocus="if(this.value == 'Message') { this.value = ''; }"
					onblur="if(this.value == '') { this.value = 'Message'; }"
					placeholder="Message"></textarea>
				<br> <br> <input type="submit" value="댓글쓰기">
			</p>
		</form>
	</div>
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
// 댓글 
Insert Script $('#replyForm').on('submit',function(event){
	if($('#reply_contents').val() == ""){ alert("내용을 입력해주세요."); event.preventDefault();
	} else { $('#comments').val($('#reply_contents').val()); return true;
	} });
	//기존 댓글 수정 & 삭제 $(".update").on('click',function(){
	var parentP = $(this).parent();
	var parentDiv = parentP.parent();
	var commBody = parentDiv.children('.comment-body');
	var content = commBody.children('p').text().trim();
	
	if($(this).text() == "수정 및 삭제"){
	commBody.append('<textarea style="margin-top:7px;" rows="4" cols="70%"
	class="updateContent" name="updateContent" id="updateContent">'+content+'</textarea>'); parentDiv.children('.comment-confirm').show();
	parentP.children(".delete").toggle("fast"); parentP.children(".updateConfirm").toggle("fast");
	
	
	$(this).text("수정취소"); } else {
	commBody.children(".updateContent").remove(); parentDiv.children('.comment-confirm').hide(); $(this).text("수정 및 삭제"); parentP.children(".delete").toggle("fast"); parentP.children(".updateConfirm").toggle("fast");
	} });
	
	
	$(".updateConfirm").on('click',function(){ var parentP = $(this).parent();
	var parentDiv = parentP.parent();
	if(parentDiv.find('input[name=pwd_chk]').val() != parentDiv.children('input[name=rep_pwd]').val()){
	alert("비밀번호가 일치하지 않습니다.");
	return false; 
	} else {
	
		$.ajax({
	url : "${pageContext.request.contextPath}/brUpdate.do", method : "POST",
	async : false,
	data: {
	comment_id : parentDiv.find("input[name=rep_id]").val(), comment_pwd : parentDiv.find('input[name=pwd_chk]').val(), comments : parentDiv.find('.updateContent').val()
	},
	success : function(data) {
	alert(data);
	parentDiv.find(".comment-body p").text(parentDiv.find('.updateContent').val()); }, error : function(request,status,error) {
	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); }
	}); 
		}
	
	parentDiv.find(".updateContent").remove(); parentDiv.find(".comment-confirm").val(""); parentDiv.find(".comment-confirm").hide(); parentP.children(".updateConfirm").toggle("fast"); parentP.children(".delete").toggle("fast"); parentP.children('.update').text("수정 및 삭제");
	}); 
	
	$(".delete").on('click',function(){
		
	var parentP = $(this).parent();
	var parentDiv = parentP.parent();
	if(parentDiv.find('input[name=pwd_chk]').val() != parentDiv.children('input[name=rep_pwd]').val()){
	alert("비밀번호가 일치하지 않습니다.");
	return false; 
	} else {
		
	$.ajax({
	url : "${pageContext.request.contextPath}/brDelete.do", method : "POST",
	data: {
	comment_id : parentDiv.find("input[name=rep_id]").val(),
	comment_pwd : parentDiv.find('input[name=pwd_chk]').val() },
	success : function(data) { alert(data);
	parentDiv.remove();
	}, error : function(request,status,error) {
	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); }
	}); 
	}
	}); 
	
	</script>
</html>