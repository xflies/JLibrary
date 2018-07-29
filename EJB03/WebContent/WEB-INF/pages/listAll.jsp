<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Book List - ${helloWord}</title>
</head>
<body>

<div><%@include file="/WEB-INF/banner/loginBanner.jsp" %></div>
<div>
<table>
	<tr>
		<th>ISBN</th>
		<th>Book Name</th>
		<th>Book Type</th>
		<th>Quantity</th>
		<th>Borrowed Quantity</th>
		<th>On-desked Quantity</th>
		<th>Reserved Quantity</th>
	</tr>
<logic:iterate id="book" name="bookList">
	<tr>
		<td><bean:write name="book" property="isbn" /></td>
		<td><bean:write name="book" property="name" /></td>
		<td><bean:write name="book" property="type" /></td>
		<td><bean:write name="book" property="qty" format="0.0" /></td>
		<td><bean:write name="book" property="borrowQty" format="0" /></td>
		<td><bean:write name="book" property="ondeskQty" format="0" /></td>
		<td><bean:write name="book" property="reserveQty" format="0" /></td>
		
		<td>
			<logic:equal name="book" property="reservable" value="true">
				<logic:notEmpty name="readerId">
					<form action="reserveBook.do" method="get">
						<input type="hidden" name="isbn" value="${book.isbn}" />
						<input type="submit" value="Reserve" />
					</form>
				</logic:notEmpty>
			</logic:equal>
		</td>
	</tr>
</logic:iterate>

</table>

</div>
</body>
</html>