<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">MyMarket</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-link " href="${pageContext.request.contextPath}/">Home</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/products">Products</a>
            <sec:authorize access="!isAuthenticated()">
                <a class="nav-link" href="${pageContext.request.contextPath}/auth/login">Login</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/cart">Cart</a>
            </sec:authorize>
            <sec:authorize access="hasAuthority('read')">
                <a class="nav-link" href="${pageContext.request.contextPath}/cart">Cart</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
            </sec:authorize>
            <sec:authorize access="hasAuthority('write')">
                <a class="nav-link" href="${pageContext.request.contextPath}/orders/manage">Manage Orders</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/orders/statistics">Statistics</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
            </sec:authorize>

        </div>
    </div>
</nav>
