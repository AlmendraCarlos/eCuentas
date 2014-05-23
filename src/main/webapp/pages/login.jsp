<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	
	<head>
	
	<c:set var="appCtx" value="${pageContext.request.contextPath}" />
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src='resources/bootstrap/js/bootstrap.min.js'></script>
	   
	<link   rel="stylesheet" href='resources/bootstrap/css/bootstrap.min.css' type="text/css" />
			
	<title>Login</title>
	
		<style>
		
			/* bootstrap styles
			-------------------------------------------------- */
			body {
			  background-color: #eee;
			}
			.form-signin {
			  max-width: 330px;
			  padding: 15px;
			  margin: 10% auto 0;
			}
			.form-signin .form-signin-heading,
			.form-signin .checkbox {
			  margin-bottom: 10px;
			}
			.form-signin .checkbox {
			  font-weight: normal;
			}
			.form-signin .form-control {
			  position: relative;
			  height: auto;
			  -webkit-box-sizing: border-box;
			     -moz-box-sizing: border-box;
			          box-sizing: border-box;
			  padding: 10px;
			  font-size: 16px;
			}
			.form-signin .form-control:focus {
			  z-index: 2;
			}
			.form-signin input[type="email"] {
			  margin-bottom: -1px;
			  border-bottom-right-radius: 0;
			  border-bottom-left-radius: 0;
			}
			.form-signin input[type="password"] {
			  margin-bottom: 10px;
			  border-top-left-radius: 0;
			  border-top-right-radius: 0;
			}
			
			/* mensajes styles
			-------------------------------------------------- */
			.error {
				padding: 15px;
				margin-bottom: 20px;
				border: 1px solid transparent;
				border-radius: 4px;
				color: #a94442;
				background-color: #f2dede;
				border-color: #ebccd1;
			}
			 
			.msg {
				padding: 15px;
				margin-bottom: 20px;
				border: 1px solid transparent;
				border-radius: 4px;
				color: #31708f;
				background-color: #d9edf7;
				border-color: #bce8f1;
			}
			
			/* Sticky footer styles
			-------------------------------------------------- */
			html {
			  position: relative;
			  min-height: 100%;
			}
			body {
			  /* Margin bottom by footer height */
			  margin-bottom: 60px;
			}
			#footer {
			  position: absolute;
			  bottom: 0;
			  width: 100%;
			  /* Set the fixed height of the footer here */
			  height: 70px;
			  background-color: #eee;
			}
			
		</style>
	</head>
	<body>
	 
			<c:if test="${not empty error}">
				<div class="error">${error}</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="msg">${msg}</div>
			</c:if>
	 
			<div class="container">
		
		      <form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST' class="form-signin" role="form">
		        <h2 class="form-signin-heading">eCuentas</h2>
		        <input type="text" class="form-control" placeholder="Username" name='username' required autofocus>
		        <input type="password" class="form-control" placeholder="Password" name='password' required>
				<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		      </form>
			</div> <!-- /container -->
			
			<div id="footer" style="text-align:center">
			  <div class="container">
			   <div style="text-align:center"><a href="http://www.softarte.com.ar" target="_blank"><img alt="www.sotarte.com.ar" src="resources/img/soft.png" /></a></div>
			  </div>
			</div>	
	</body>
</html>