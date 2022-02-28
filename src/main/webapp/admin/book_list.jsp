<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Manage Books - Evergreen BookStore Administration</title>
	<link rel="stylesheet" href="../css/style.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:include page="/admin/header.jsp" />

	<div align="center">
		<h2 class="pageheading">Books Management</h2>
		<a href="new_book">Create new Book</a>
	</div>

	<c:if test="${message != null}">
		<div align="center">
			<h4 class="message">${message}</h4>
		</div>
	</c:if>
	<br>
	<br>
	<div align="center">
		<table border="1" cellpadding="5">
			<tr>
				<th>Index</th>
				<th>ID</th>
				<th>Image</th>
				<th>Title</th>
				<th>Author</th>
				<th>Category</th>
				<th>Price</th>
				<th>Last Updated</th>
				<th>Actions</th>
			</tr>
			<c:forEach var="book" items="${listBooks}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${book.bookId}</td>
					<td>
						<img src="data:image/jpg;base64,${book.base64Image}" width="84" height="110"/>
					</td>
					<td>${book.title}</td>
					<td>${book.author}</td>
					<td>${book.category.name}</td>
					<td>$ ${book.price}</td>
					<td><fmt:formatDate pattern='MM/dd/yyyy' value="${book.lastUpdateTime}"/></td>
					<td>
						<a href="edit_book?id=${book.bookId}">Edit</a> &nbsp; 
						<a href="javascript:void(0);" class="deleteLink" id="${book.bookId}">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>


	<hr width="60%">
	<jsp:include page="/admin/footer.jsp"></jsp:include>

	<script>
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					bookId = $(this).attr("id");
					if (confirm('Are you sure to delete the book with ID ' + bookId + '?')) {
						window.location = 'delete_book?id=' + bookId;
					}
				});
			});
		});
		/* function confirmDelete(userId) {
			if (confirm('Are you sure to delete the user with ID ' + userId + '?')) {
				window.location = 'delete_user?id=' + userId;
			}
		} */
	</script>
</body>
</html>