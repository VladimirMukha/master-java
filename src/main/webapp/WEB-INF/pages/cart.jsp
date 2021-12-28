<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Shopping Cart</title>

</head>
<body>
<jsp:include page="_header.jsp" />

<jsp:include page="_menu.jsp" />

<fmt:setLocale value="en_US" scope="session"/>

<div class="page-title">My Cart</div>
<security:authorize  access="hasAuthority('read')">
    <a href="${pageContext.request.contextPath}/orders">
        Order List
    </a>
</security:authorize>

<c:if test="${empty cart or empty cart.cartLines}">
    <h2>There is no items in Cart</h2>
    <a href="${pageContext.request.contextPath}/products">Show
        Product List</a>
</c:if>

<c:if test="${not empty cart and not empty cart.cartLines}">

    <form:form method="POST" modelAttribute="cart" action="${pageContext.request.contextPath}/cart/finalize">

        <c:set var="cartLines" value="${cart.cartLines}"/>
        <c:forEach items="${cartLines}" var="orderList"
                   varStatus="varStatus">
            <div class="product-preview-container">
                <ul>
                    <li>Id: <form:input readonly="true" path = "cartLines[${varStatus.index}].productDTO.id" value="${orderList.productDTO.id}" /></li>
                    <li>Name: <form:input readonly="true" path = "cartLines[${varStatus.index}].productDTO.name" value="${orderList.productDTO.name}" /></li>
                    <li>Price: <form:input readonly="true" path = "cartLines[${varStatus.index}].productDTO.price" value="${orderList.productDTO.price}" type="currency"/></li>
                    <li>QuantityInStock: <form:input readonly="true" path = "cartLines[${varStatus.index}].productDTO.quantityInStock" value="${orderList.productDTO.quantityInStock}"/></li>
                    <li>Quantity: <form:input path = "cartLines[${varStatus.index}].quantity" value="${orderList.quantity}" /></li>
                    <li>Subtotal: <form:input readonly="true" path = "cartLines[${varStatus.index}].amount" value="${orderList.amount}" type="currency" /></li>
                    <li><a href="${pageContext.request.contextPath}/cart/delete/${orderList.productDTO.id}">Delete</a></li>
                </ul>
            </div>
        </c:forEach>
        <div style="clear: both"></div>
        <input type="submit" value="Create order">
    </form:form>
</c:if>
</body>
</html>