<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" 				prefix="c"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<c:set var="appCtx" value="${pageContext.request.contextPath}" />
	
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	    <script src='${appCtx}/resources/bootstrap/js/bootstrap.min.js'></script>
	    
		<link   rel="stylesheet" href='${appCtx}/resources/bootstrap/css/bootstrap.min.css' type="text/css" />
	
		<title><tiles:insertAttribute name="title" ignore="true" /></title>
	</head>
	
	<body>
	
		<div id="header"> 
			<tiles:insertAttribute name="menu" /> 
		</div>
		
		<div id="body"> 
			<tiles:insertAttribute name="modal" />
			<tiles:insertAttribute name="body" /> 
		</div>
		
		<div id="footer"> 
			<tiles:insertAttribute name="footer" /> 
		</div>
	
	</body>

</html>