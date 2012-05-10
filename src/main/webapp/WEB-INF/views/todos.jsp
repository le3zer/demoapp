<%@page import="ie.cit.cloudapp.Todo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="styles/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TODO</title>
</head>
<body>
<a href="j_spring_security_logout">Logout: <security:authentication
	property="principal.username" /> </a>
	<h1>Todo Application (Controller)</h1>
	<h2>List of current TODO items</h2>


	<c:forEach items="${todos}" var="todo" varStatus="row">
		<c:choose>
			<c:when test="${todo.done}">
				<del>${todo.text}</del>
			</c:when>
			<c:otherwise>
${todo.text}
</c:otherwise>
		</c:choose>
<form method="post">
			<input name="_method" type="hidden" value="delete"> <input
				name="todoId" type="hidden" value="${todo.id}"> <input
				type="submit" value="Delete">
		</form>
		<form method="post">
			<input name="_method" type="hidden" value="put"> <input
				name="todoId" type="hidden" value="${todo.id}"> <input
				type="submit" value="Update">
		</form>
		<br />
	</c:forEach>
	<h2>Create New TODO</h2>
	<form method="post">
		Text: <input name="text"><input type="submit">
	</form>

</body>
</html>