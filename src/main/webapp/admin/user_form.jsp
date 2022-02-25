<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="../css/style.css">
	<title>
	<c:if test="${user != null}">
		Edit User
	</c:if>
	<c:if test="${user == null}">
		Create New User
	</c:if>
	</title>
	<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:include page="/admin/header.jsp" />

	<div align="center">
		<h2 class="pageheading">
			<c:if test="${user != null}">
				Edit User
			</c:if>
			<c:if test="${user == null}">
				Create New User
			</c:if>
		</h2>
	</div>

	<div align="center">
		<c:if test="${user != null}">
			<form action="update_user" method="post" id="userForm">
			<input type="hidden" name="userId" value="${user.userId}">
		</c:if>
		<c:if test="${user == null}">
			<form action="create_user" method="post" id="userForm">
		</c:if>
			<table class="form">
				<tr>
					<td  align="right">Email:</td>
					<td  align="left"><input type="email" name="email" value="${user.email}" id="email" size="20"></td>
				</tr>
				<tr>
					<td align="right">Full name:</td>
					<td align="left"><input type="text" name="fullName" value="${user.fullName}" id="fullName" size="20"></td>
				</tr>
				<tr>
					<td align="right">Password:</td>
					<td align="left"><input type="password" name="password" value="${user.password}" id="password" size="20"></td>
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
	
		$(document).ready(function() {
			$("#userForm").validate({
				rules: {
					email: {
						required: true,
						email: true
					},
					fullName: "required",
					password: "required",
				},
				
				messages: {
					email: {
						required: "Please enter email",
						email: "Please enter an valid email"
					},
					fullName: "Please enter full name",
					password: "Please enter password"
				}
			});
		});
		
		$("#buttonCancel").click(function() {
			history.go(-1);
		});
		
		/* function validateFormInput() {
			var fieldEmail = document.getElementById("email");
			var fieldFullname = document.getElementById("fullName");
			var fieldPassword = document.getElementById("password");
			
			if (fieldEmail.value.length == 0) {
				alert("Email is required");
				fieldEmail.focus();
				return false;
			}
			
			if (fieldFullname.value.length == 0) {
				alert("Fullname is required");
				fieldFullname.focus();
				return false;
			}
			
			if (fieldPassword.value.length == 0) {
				alert("Password is required");
				fieldPassword.focus();
				return false;
			}
			
			return true;
		} */
	</script>
</body>
</html>