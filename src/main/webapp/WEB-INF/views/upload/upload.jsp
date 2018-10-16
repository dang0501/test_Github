<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<title>My JSP Page</title>
	<!-- Twitter Bootstrap3 & jQuery -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" />
	<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h1 class="page-header">Upload Test</h1>
	
	<form method="post" action="${pageContext.request.contextPath}/upload/upload_ok.do"
		enctype="multipart/form-data">
		<div class="form-group">
			<label for="memo">파일설명</label>
			<input type="text" name="memo" id="memo" class="form-control" />
		</div>
		
		<div class="form-group">
			<label for="uplaod_file">첨부파일</label>
			<input type="file" name="upload_file" id="uplaod_file"
				class="form-control" multiple />
		</div>
		
		<div class="form-group">
			<input type="submit" class="btn btn-primary btn-block" value="업로드" />
		</div>
	</form>
</div>
</body>
</html>


