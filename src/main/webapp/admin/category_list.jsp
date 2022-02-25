<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">	
	<link rel="stylesheet" href="../css/style.css">

	<title>Manage Categories - Evergreen BookStore Administration</title>
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:include page="/admin/header.jsp" />

	<div align="center">
		<h2 class="pageheading">Category Management</h2>
		<a href="category_form.jsp">Create New Category</a>
	</div>

	<c:if test="${message != null}">
		<div align="center">
			<h4 class="message">${message}</h4>
		</div>
	</c:if>
	<br>
	<br>
	<div align="center">
		<table>
			<tr>
				<th>Index</th>
				<th>ID</th>
				<th>Name</th>
				<th>Actions</th>
			</tr>
			<c:forEach var="category" items="${listCategory}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${category.categoryId}</td>
					<td>${category.name}</td>
					<td>
						<a href="edit_category?id=${category.categoryId}">Edit</a> &nbsp; 
						<a href="javascript:void(0);" class="deleteLink" id="${category.categoryId}">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>


	<jsp:include page="/admin/footer.jsp"></jsp:include>

	<script>
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					categoryId = $(this).attr("id");
					if (confirm('Are you sure to delete the category with ID ' + categoryId + '?')) {
						window.location = 'delete_category?id=' + categoryId;
					}
				});
			});
		});
	
		/* function confirmDelete(categoryId) {
			if (confirm('Are you sure to delete the category with ID ' + categoryId + '?')) {
				window.location = 'delete_category?id=' + categoryId;
			}
		} */
	</script>
</body>
</html>