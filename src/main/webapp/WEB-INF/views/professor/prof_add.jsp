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
		<h1 class='page-header'>교수추가</h1>

		<!-- 추가를 위한 HTML 폼 시작 -->
		<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/professor/prof_add_ok.do">
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">교수이름</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="name" name="name" />
				</div>
			</div>

			<div class="form-group">
				<label for="userid" class="col-sm-2 control-label">아이디</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="userid" name="userid" />
				</div>
			</div>

			<div class="form-group">
				<label for="position" class="col-sm-2 control-label">직급</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="position" name="position" />
				</div>
			</div>

			<div class="form-group">
				<label for="sal" class="col-sm-2 control-label">급여</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="sal" name="sal" />
				</div>
			</div>

			<div class="form-group">
				<label for="comm" class="col-sm-2 control-label">보직수당</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="comm" name="comm" />
				</div>
			</div>

			<div class="form-group">
				<label for="hiredate" class="col-sm-2 control-label">입사일</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="hiredate" name="hiredate" />
				</div>
			</div>

			<div class="form-group">
				<label for="deptno" class="col-sm-2 control-label">소속학과</label>
				<div class="col-sm-10">
					<select name="deptno" class="form-control">
						<option value="">---- 소속학과를 선택하세요 ----</option>
						<c:forEach var="item" items="${deptList}">
							<option value="${item.deptno}">${item.dname}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">저장하기</button>
				</div>
			</div>
		</form>
		<!--// 추가를 위한 HTML 폼 끝  -->
	</div>
</body>
</html>



