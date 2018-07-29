<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http//www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
	<title>Reader Information Form</title>
	<html:base/>
</head>
<body>
<div>

<html:form action="updateReaderInfo.do" method="post" onsubmit="return validateReaderInfoForm(this)">
	<table>
		<tr><td>Reader ID        </td><td><input name="id" type="text" value="${reader.id}" readOnly /></td></tr>
		<tr><td>Reader name      </td><td><input name="name" type="text" value="${reader.name}" readOnly /></td></tr>
		<tr><td>Password         </td><td><input name="password" type="password" /></td></tr>
		<tr><td>New Password     </td><td><input name="newPassword" type="password" /></td></tr>
		<tr><td>Confirm Password </td><td><input name="confirmPassword" type="password" /></td></tr>
		<tr><td>E-mail           </td><td><input name="mailAddr" type="text" value="${reader.mailAddr}" /></td></tr>
		<tr><td>Activated        </td><td><input name="activated" type="text" value="${reader.activated}" readOnly /></td></tr>
		<tr><td><input type="submit" value="Update" /></td></tr>
	</table>
</html:form>
<html:javascript formName="readerInfoForm"/>
<hr />
<form name="ReaderInfoForm" action="updateReaderInfo.do" method="post">
	<table>
		<tr><td>Reader ID </td>
		<td>
			<input name="id" type="text" value="${reader.id}" readOnly />
		</td>
		</tr>
	
		<tr><td>Reader name</td>
		<td>
			<input name="name" type="text" value="${reader.name}" readOnly />
		</td></tr>
		
		<tr><td>Password</td>
		<td>
			<input name="password" type="password" />
		</td></tr>
		
		<tr><td>New Password</td>
		<td>
			<input name="newPassword" type="password" />
		</td></tr>
		
		<tr><td>Retype New Password</td>
		<td>
			<input name="retypeNewPassword" type="password" />
		</td></tr>
		
		<tr><td>E-mail</td>
		<td>
			<input name="mailAddr" type="text" value="${reader.mailAddr}" />
		</td></tr>
		
		<tr><td>Activated</td>
		<td>
			<input name="activated" type="text" value="${reader.activated}" readOnly />
		</td></tr>
		
		<tr><td>
			<input type="submit" value="Update" />
		</td></tr>
	</table>
</form>

</div>
</body>
</html:html>