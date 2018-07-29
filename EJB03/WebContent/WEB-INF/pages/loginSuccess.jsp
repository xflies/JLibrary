<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login successfully</title>
</head>
<body>
<h1>Hello ${sessionScope.readerName}, you log in successfully!</h1>
<h1>Your session ID is ${sessionScope.sessionNr}.</h1>
<h1>Wait 5 seconds to return the previous page ...</h1>
<div>
<div id="timer">
</div>
</div>

</body>

<script>
var myVar = setInterval(function() {myTimer()}, 5000);

function myTimer() {
	location.href = document.referrer;
}
</script>
</html>