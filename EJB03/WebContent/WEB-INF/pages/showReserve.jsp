<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Message</title>
</head>
<body>
<div>

<c:choose>
	<c:when test="${reserveNote!=null}">
		<h1>Reserve Book Successful: </h1>
		<table>
			<tr>
				<th>Reserver Note ID</th>
				<th>ISBN</th>
				<th>Book Name</th>
				<th>Status</th>
				<th>Reserve Date</th>
				<th>Expired Date</th>
			</tr>
			<tr>
				<td>${reserveNote.id}</td>
				<td>${reserveNote.book.isbn}</td>
 				<td>${reserveNote.book.name}</td>
				<td>${reserveNote.status}</td>
				<td>${reserveNote.reserveDate}</td>
				<td>${reserveNote.expireDate}</td>
			</tr>
		</table>
	</c:when>
</c:choose>

</div>
</body>
</html>