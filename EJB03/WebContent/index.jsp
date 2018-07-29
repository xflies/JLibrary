<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EJB03</title>
</head>
<body>

<div><%@include file="WEB-INF/banner/loginBanner.jsp" %></div>
<div>
	<h1>Hello, EJB03 is a project integrating EJB 3, Spring 4, Hibernate 4 and Struts 1.3.</h1>
		
	<a href="listAllBooks.do">List All Books</a>
	<jsp:include page="WEB-INF/banner/searchBar.jsp" />
	<div>
		<a href="unsafeLogin.do">Unsafe Login</a>
	</div>
</div> 

</body>
</html>