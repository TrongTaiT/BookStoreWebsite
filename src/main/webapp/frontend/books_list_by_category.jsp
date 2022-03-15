<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Books in ${category.name}</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/frontend/header.jsp" />

	<div align="center">
		<h2>${category.name}</h2>
		
	</div>

	<div align="center" style="width: 80%; margin: 0 auto">
		<c:forEach items="${listBooks}" var="book">
			<div style="float: left; display: inline-block; margin: 10px;">
				<div>
					<a href="view_book?id=${book.bookId}">
						<img src="data:image/jpg;base64,${book.base64Image}" width="128" height="164" />
					</a>
				</div>
				<div>
					<a href="view_book?id=${book.bookId}">
						<b>${book.title}</b>
					</a>
				</div>
				<div>Rating *****</div>
				<div><i>by ${book.author}</i></div>
				<div><b>$ ${book.price}</b></div>
			</div>
		</c:forEach>
	</div>

	<br>
	<br>
	<br>
	<jsp:include page="/frontend/footer.jsp"></jsp:include>
</body>
</html>