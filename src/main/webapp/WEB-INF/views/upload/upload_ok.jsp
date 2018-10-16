<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false" %>
<!doctype html>
<html lang='ko'>
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
<div class='container'>
	<h1 class='page-header'>Upload Result</h1>

	<h2>${memo}</h2>
	
	<!-- 업로드 된 파일 목록 출력 처리 -->
	<c:forEach var="item" items="${fileList}" varStatus="status">
		<!-- 원본 파일명 + 다운로드 링크 -->
		<h3>
			<c:url value="/download.do" var="download_url">
				<c:param name="file" value="${item.fileDir}/${item.fileName}"></c:param>
			</c:url>
			<a href="${download_url}">${item.orginName}</a>
		</h3>
		<!-- 파일 정보 표시 -->
		<div class='alert alert-info'>
			fileDir: ${item.fileDir}<br />
			fileName: ${item.fileName}<br />
			contentType: ${item.contentType}<br />
			fileSize: ${item.fileSize}

			<!-- 이미지인 경우 썸네일 요청 -->
			<c:if test="${fn:indexOf(item.contentType,'image') > -1}" >
				<c:url value="../download.do" var="download_url">
					<c:param name="file" value="${item.fileDir}/${item.fileName}"></c:param>
					<c:param name="width" value="240"></c:param>
					<c:param name="height" value="240"></c:param>
				</c:url>
				<hr />
				<img src="${download_url}" class="img-responsive" />
			</c:if>
		</div>
		<hr />
	</c:forEach>
</div>
</body>
</html>
