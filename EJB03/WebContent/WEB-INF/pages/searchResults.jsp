<%@page import="javax.naming.directory.SearchResult"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.List" %>
<%@ page import="com.peter2.ejb.entity.Book" %>
<%
List<?> resultList = (List<?>) request.getAttribute("searchResults");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Results</title>
</head>
<body>
<div><%@include file="/WEB-INF/banner/loginBanner.jsp" %></div>
<jsp:include page="/WEB-INF/banner/searchBar.jsp" />
<div>

<table>
	<tr>
		<th>Book Entity ID</th>
		<th>Location</th>
		<th>ISBN</th>
		<th>Book Name</th>
		<th>Book Type</th>
		<th>Quantity</th>
		<th>Borrowed Quantity</th>
		<th>On-desked Quantity</th>
		<th>Reserved Quantity</th>
	</tr>

<c:forEach var="bookEntity" items="${searchResults}">
	<tr>
		<td>${bookEntity.id}</td>
		<td>${bookEntity.location}</td>
		<td>${bookEntity.book.isbn}</td>
		<td>${bookEntity.book.name}</td>
		<td>${bookEntity.book.type}</td>
		<td>${bookEntity.book.qty}</td>
		<td>${bookEntity.book.borrowQty}</td>
		<td>${bookEntity.book.ondeskQty}</td>
		<td>${bookEntity.book.reserveQty}</td>
		
		<td>
			<c:choose>
				<c:when test="${login && bookEntity.book.reservable}">
					<form action="reserveBook.do" method="get">
						<input type="hidden" name="isbn" value="${bookEntity.book.isbn}" />
						<input type="submit" value="Reserve" />
					</form>
				</c:when>
			</c:choose>
		</td>
	</tr>
</c:forEach>

</table>
Total <%=resultList.size() %> found.
</div>
</body>
</html>