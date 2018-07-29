<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
<form name="logInForm" action="login.do" method="post">
	<table>
		<tr>
		
		<td>Reader name:</td>
		<td>
			<input name="readerName" type="text" />
		</td>
		
		<td>
		&nbsp;&nbsp;&nbsp;
		</td>
		
		<td>Password:</td>
		<td>
			<input name="password" type="password" />
		</td>
		
		<td>
			<input type="submit" value="Login" />
		</td>
		
		</tr>
	</table>
</form>
</div>