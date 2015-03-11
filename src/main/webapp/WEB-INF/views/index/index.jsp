<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize var="loggedIn" access="isAuthenticated()" />

<html>
	<head>
		<title>Hello Facebook</title>
	</head>
	<body>
	
		<c:if test="${loggedIn}">
		 
		 <img style="border-radius:25px" src="${authUser.imageUrl}"></img>
		 ${authUser.fullName} You are now logged in.
		 <br><br>
		 
		 <a class="btn btn-primary btn-lg" href="/user/logout">Logout</a>
		</c:if>
		
		<c:if test="${!loggedIn}">		
		<a class="btn btn-primary btn-lg" href="/user/login">Login</a>
		</c:if>
	</body>
</html>