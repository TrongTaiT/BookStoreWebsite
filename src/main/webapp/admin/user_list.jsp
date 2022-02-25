<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Manage Users - Evergreen BookStore Administration</title>
	<link rel="stylesheet" href="../css/style.css">
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:include page="/admin/header.jsp" />

	<div align="center">
		<h2 class="pageheading">Users Management</h2>
		<a href="user_form.jsp">Create new User</a>
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
				<th>Email</th>
				<th>Full Name</th>
				<th>Actions</th>
			</tr>
			<c:forEach var="user" items="${listUsers}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${user.userId}</td>
					<td>${user.email}</td>
					<td>${user.fullName}</td>
					<td><a href="edit_user?id=${user.userId}">Edit</a> &nbsp; 
					<a href="javascript:void(0);" class="deleteLink" id="${user.userId}">Delete</a></td>
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
					userId = $(this).attr("id");
					if (confirm('Are you sure to delete the user with ID ' + userId + '?')) {
						window.location = 'delete_user?id=' + userId;
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