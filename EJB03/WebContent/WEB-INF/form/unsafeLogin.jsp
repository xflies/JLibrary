<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log In</title>
</head>
<body>

<center>
	<form name="logInForm" action="unsafeLogin.do" method="post">
		<table>
			<tr>
			
			<td>user name</td>
			<td>
				<input name="readerName" type="text" />
			</td>
			
			</tr>
			<tr>
			
			<td>password</td>
			<td>
				<input name="password" type="password" />
			</td>
			
			</tr>
		</table>
		<input type="submit" />
	</form>
</center>

</body>
</html>