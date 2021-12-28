<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<html lang="en">
<head>
<title>Sign Up Customer</title>
</head>
<body>
<jsp:include page="_header.jsp" />

<jsp:include page="_menu.jsp" />
<div>

    <form:form method="POST" modelAttribute="userForm" acceptCharset="true">
        <h2>Registration</h2>
        <div>
            <form:input type="text" path="email" placeholder="Username"
                        autofocus="true"></form:input>
            <form:errors path="email"></form:errors>
        </div>
        <div>
            <form:input type="password" path="password" placeholder="Password"></form:input>
            <form:errors path="password"></form:errors>
        </div>
        <div>
            <form:input type="password" path="passwordConfirm"
                        placeholder="Confirm your password"></form:input>
            <form:errors path="passwordConfirm"></form:errors>
        </div>
        <div>
            <form:input type="text" path="firstName" placeholder="First Name"
                        autofocus="true"></form:input>
            <form:errors path="firstName"></form:errors>
        </div>
        <div>
            <form:input type="text" path="lastName" placeholder="Last Name"
                        autofocus="true"></form:input>
            <form:errors path="lastName"></form:errors>
        </div>
        <div>
            <form:input type="date" path="dateOfBirth" placeholder="Date Of Birth"
                        autofocus="true"></form:input>
            <form:errors path="dateOfBirth"></form:errors>
        </div>
        <button type="submit">Sign Up</button>
    </form:form>
</div>
</body>
</html>