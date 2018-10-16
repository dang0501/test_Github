<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
	<meta charset='utf-8' />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<title>Spring Example</title>
	<!-- Bootstrap + jQuery -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="http://code.jquery.com/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<!--// Bootstrap + jQuery -->
</head>

<body>
	<div class="container">
		<h1 class='page-header'>학생 상세 보기</h1>
		
		<!-- 조회결과를 출력하기 위한 표 -->
		<table class="table table-bordered">
			<tbody>
				<tr>
					<th class="info text-center" width="130">학생번호</th>
					<td>${item.studno}</td>
				</tr>
				<tr>
					<th class="info text-center">이름</th>
					<td>${item.name}</td>
				</tr>
				<tr>
					<th class="info text-center">아이디</th>
					<td>${item.userid}</td>
				</tr>
				<tr>
					<th class="info text-center">학년</th>
					<td>${item.grade}</td>
				</tr>
				<tr>
					<th class="info text-center">주민번호</th>
					<td>${item.idnum}</td>
				</tr>
				<tr>
					<th class="info text-center">생년월일</th>
					<td>${item.birthdate}</td>
				</tr>
				<tr>
					<th class="info text-center">전화번호</th>
					<td>${item.tel}</td>
				</tr>
				<tr>
					<th class="info text-center">키</th>
					<td>${item.height}</td>
				</tr>
				<tr>
					<th class="info text-center">몸무게</th>
					<td>${item.weight}</td>
				</tr>
				<tr>
					<th class="info text-center">학과번호</th>
					<td>${item.deptno}</td>
				</tr>
				<tr>
					<th class="info text-center">교수번호</th>
					<td>${item.profno}</td>
				</tr>
			</tbody>
		</table>
		
		<!-- 버튼 -->
		<div class="text-center">
			<a href="${pageContext.request.contextPath}/student/stud_list.do" class="btn btn-primary">목록</a>
			<a href="${pageContext.request.contextPath}/student/stud_add.do" class="btn btn-info">추가</a>
			<a href="${pageContext.request.contextPath}/student/stud_edit.do?studno=${item.studno}" class="btn btn-warning">수정</a>
			<a href="${pageContext.request.contextPath}/student/stud_delete.do?studno=${item.studno}" class="btn btn-danger">삭제</a>
		</div>
	</div>
</body>
</html>