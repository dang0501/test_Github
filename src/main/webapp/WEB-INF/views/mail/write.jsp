<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
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
	<script src="http://cdn.ckeditor.com/4.5.10/standard/ckeditor.js"></script>
</head>
<body>
	<div class="container">
		<!-- 제목 -->
	<div class="page-header"> <h1>Spring 메일 발송 연습</h1> </div>

	<!-- 메일 폼 영역 -->
	<form class="form-horizontal" method="post" 
        action="${pageContext.request.contextPath}/mail/mail_ok.do">
		<div class="form-group">
            <label class="control-label col-sm-2" for="sender">보내는주소</label>
            <div class="col-sm-10">
                <input type="text" name="sender" id="sender" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="receiver">받는주소</label>
            <div class="col-sm-10">
                <input type="text" name="receiver" id="receiver" class="form-control"/>
            </div>
        </div>
                
        <div class="form-group">
            <label class="control-label col-sm-2" for="subject">메일 제목</label>
            <div class="col-sm-10">
                <input type="text" name="subject" id="subject" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="content">내용입력</label>
            <div class="col-sm-10">
                <textarea name="content" id="content" class="ckeditor" ></textarea>
            </div>
        </div>
                
        <div class="col-sm-10 col-sm-offset-2 text-right">
            <input type="submit" class="btn btn-primary" value="메일보내기" />
            <input type="reset" class="btn btn-default" value="다시작성" />
        </div>
	</form>
	</div>
</body>
</html>


