<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>${book.title} - Online Books Store</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/frontend/header.jsp" />

	<div align="center">
		<table width="80%" style="border:0;">
			<tr>
				<td colspan="3" align="left">
					<h2>${book.title}</h2> by ${book.author}
				</td>
			</tr>
			<tr>
				<td rowspan="2">
					<img src="data:image/jpg;base64, ${book.base64Image}" width="240" height="300">
				</td>
				<td valign="top" align="left">
					Rating *****
				</td>
				<td valign="top" rowspan="2" width="20%">
					$${book.price} <br><br>
					<button type="submit">Add to Cart</button>
				</td>
			</tr>
			<tr>
				<td valign="top">
					${book.description}
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<h2>Customer Reviews</h2>
				</td>
				<td colspan="2" align="center">
					<button>Write a Customer Review</button>
				</td>
			</tr>
		</table>
	</div>



	<br>
	<br>
	<br>
	<jsp:include page="/frontend/footer.jsp"></jsp:include>
</html>