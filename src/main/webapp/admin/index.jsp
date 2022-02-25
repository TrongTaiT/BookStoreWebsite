<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Books Store Administration</title>
	<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<jsp:include page="/admin/header.jsp" />

	<div align="center">
		<h2 class="pageheading">Administrative Dashboard</h2>
	</div>
	
	<hr width="60%">
	<div align="center">
		<h2 class="pageheading">Quick Action</h2>
		<b>
		<a href="create_book">New Book</a> &nbsp;
		<a href="create_user">New User</a> &nbsp;
		<a href="create_category">New Category</a> &nbsp;
		<a href="create_customer">New Customer</a> &nbsp;
		</b>
	</div>
	
	<hr width="60%">
	<div align="center">
		<h2 class="pageheading">Recent Sales:</h2>
	</div>
	
	<hr width="60%">
	<div align="center">
		<h2 class="pageheading">Recent Reviews:</h2>
	</div>
	
	<hr width="60%">
	<div align="center">
		<h2 class="pageheading">Statistics:</h2>
	</div>
	
	<hr width="60%">
	<jsp:include page="/admin/footer.jsp"></jsp:include>
</html>