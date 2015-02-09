<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize var="loggedIn" access="isAuthenticated()" />

<!-- HEADER BAR -->
<div class="navbar-header">
<a href="/" class="navbar-brand logo">quotebreaker</a>
   <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
     <span class="icon-bar"></span>
     <span class="icon-bar"></span>
     <span class="icon-bar"></span>
   </button>
 </div>
 <div class="navbar-collapse collapse" id="navbar-main">
 
 <c:choose>
    <c:when test="${loggedIn}">
			<sec:authorize access="hasRole('ROLE_USER')">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/user/home">Home</a></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#" id="headerUserTab">Account <span
						class="caret"></span></a>
					<ul class="dropdown-menu" aria-labelledby="headerUserTab">
						<li><a href="#">Settings</a></li>
						<li><a href="#">Conversations</a></li>
						<li class="divider"></li>
						<li><a href="/review/user">Reviews</a></li>
					</ul></li>
				<li><a href="/user/logout">Logout</a></li>
			</ul>
 			</sec:authorize>
 			<sec:authorize access="hasRole('ROLE_TRAVELAGENT')">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/agent/dashboard">Dashboard</a></li>				
				<li><a href="/user/logout">Logout</a></li>
			</ul>
 			</sec:authorize>
			
		</c:when>
    <c:otherwise>
			<ul class="nav navbar-nav navbar-right">
				<!-- <li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">Agents <span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
				<li><a href="/travel/agent-benefits">Travel Agents</a></li>
				<li><a href="/loan/agent-benefits">Loan Agents</a></li>
				</ul>
				</li> -->
				<li><a href="/travel/agent-benefits">Travel Agents</a></li>
				<li><a href="/insurance/agent-benefits">Insurance Agents</a></li>
				<li><a href="/user/login">Login</a></li>
				<li><a href="/user/register">Signup</a></li>
			</ul>
		</c:otherwise>
</c:choose>

</div>

