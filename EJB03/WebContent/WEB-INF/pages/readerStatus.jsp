<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.List" %>
<%
List<?> borrowList = (List<?>) request.getAttribute("borrowList");
List<?> reserveList = (List<?>) request.getAttribute("reserveList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Reader Status</title>
</head>
<body>
<div>

<h1>Hi, ${readerName}</h1>
You have borrowed <%=borrowList.size() %> books.<br />
<table>
	<tr>
		<th>Borrow Note ID</th>
		<th>ISBN</th>
		<th>BookName</th>
		<th>Book Entity ID</th>
		<th>Status</th>
		<th>Start Date</th>
		<th>Exprired Date</th>
	</tr>
<c:forEach var="borrowNote" items="${borrowList}">
	<tr>
		<td>${borrowNote.id}</td>
		<td>${borrowNote.bookEntity.book.isbn}</td>
		<td>${borrowNote.bookEntity.book.name}</td>
		<td>${borrowNote.bookEntity.id}</td>
		<td>${borrowNote.status}</td>
		<td>${borrowNote.startDate}</td>
		<td>${borrowNote.expireDate}</td>
	</tr>
</c:forEach>
</table>
You have reserved <%=reserveList.size() %> books.<br />
<table>
	<tr>
		<th>Reserve Note ID</th>
		<th>ISBN</th>
		<th>BookName</th>
		<th>Status</th>
		<th>Reserve Date</th>
		<th>Exprired Date</th>
	</tr>
<c:forEach var="reserveNote" items="${reserveList}">
	<tr>
		<td>
			<a href="showReserve.do?reserveNoteId=${reserveNote.id}">${reserveNote.id}</a>
		</td>
		<td>${reserveNote.book.isbn}</td>
		<td>${reserveNote.book.name}</td>
		<td>${reserveNote.status}</td>
		<td>${reserveNote.reserveDate}</td>
		<td>${reserveNote.expireDate}</td>
		
		<td>
			<form action="cancelReserve.do" action="get">
				<input type="hidden" name="reserveNoteId" value="${reserveNote.id}">
				<input type="submit" value="Cancel">
			</form>
		</td>
	</tr>
</c:forEach>
</table>

</div>
</body>
</html>