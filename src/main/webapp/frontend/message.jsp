<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="css/style.css">
<title>Evergreen Bookstore</title>
</head>
<body>

	<jsp:include page="/frontend/header.jsp" />
	
	<div align="center">
		<h3>${message}</h3>
	</div>
	
	<jsp:include page="/frontend/footer.jsp"></jsp:include>
	
</body>
</html>