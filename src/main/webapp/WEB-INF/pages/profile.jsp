<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <title>Profile</title>
</head>
<body>
<jsp:include page="_header.jsp" />

<jsp:include page="_menu.jsp" />
<table>
    <tr>
        <th>first name</th>
        <th>last name</th>
        <th>date of birth</th>
        <th>email</th>
    </tr>
        <tr>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.dateOfBirth}</td>
            <td>${user.email}</td>
        </tr>
</table>
<sec:authorize access="hasAuthority('read')">
    <a href="profile/editProfile/${user.id}">Edit Profile</a>
    <a href="profile/addAddress/${user.id}">Add Address</a>
    <c:if test="${not empty user.addresses}">
    <c:forEach var="address" items="${user.addresses}">
    <table>
        <tr>
            <th>Index</th>
            <th>Country</th>
            <th>City</th>
            <th>Street</th>
            <th>Building</th>
            <th>Room</th>
        </tr>
        <tr>
            <td>${address.index}</td>
            <td>${address.country}</td>
            <td>${address.city}</td>
            <td>${address.street}</td>
            <td>${address.building}</td>
            <td>${address.room}</td>
        </tr>
    </table>
    <a href="profile/editAddress/${address.id}">Edit Address</a>
    </c:forEach>
    </c:if>
</sec:authorize>
</body>
</html>
