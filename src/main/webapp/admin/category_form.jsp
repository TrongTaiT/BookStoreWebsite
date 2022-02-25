<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="../css/style.css">
	<meta charset="utf-8">
	<title>
		<c:if test="${category != null}">
			Edit Category
		</c:if>
		<c:if test="${category == null}">
			Create New Category
		</c:if>
	</title>
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:include page="/admin/header.jsp" />

	<div align="center">
		<h2 class="pageheading">
			<c:if test="${category != null}">
				Edit Category
			</c:if>
			<c:if test="${category == null}">
				Create New Category
			</c:if>
		</h2>
	</div>

	<div align="center">
		<c:if test="${category != null}">
			<form action="update_category" method="post" id="categoryForm">
			<input type="hidden" name="categoryId" value="${category.categoryId}">
		</c:if>
		<c:if test="${category == null}">
			<form action="create_category" method="post" id="categoryForm">
		</c:if>
			<table class="form">
				<tr>
					<td align="right">Name:</td>
					<td align="left"><input type="text" name="name" value="${category.name}" id="name" size="20"></td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit">Save</button>&nbsp;&nbsp;&nbsp;
						<button id="buttonCancel">Cancel</button>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<jsp:include page="/admin/footer.jsp"></jsp:include>
	
	<script type="text/javascript">
	
		$("#categoryForm").validate({
			rules: {
				name: "required"
			},
			
			messages: {
				name: "Please enter name"
			}
		})
		
		$("#buttonCancel").click(function() {
			history.go(-1);
		});
	
		/* function validateFormInput() {
			var fieldName = document.getElementById("name");
			if (fieldName.value.length == 0) {
				alert("Category name is required");
				fieldName.focus();
				return false;
			}
			
			return true;
		} */
	</script>
</body>
</html>