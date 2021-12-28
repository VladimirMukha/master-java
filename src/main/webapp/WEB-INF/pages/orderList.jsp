<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>

<%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">--%>

</head>
<body>

<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />

<fmt:setLocale value="en_US" scope="session"/>

<div class="page-title">Order List</div>

<%--<div>Total Order Count: ${paginationResult.totalRecords}</div>--%>

<table border="1" style="width:100%">
    <tr>
        <th>Order number</th>
        <th>Delivery Method</th>
        <th>Order Status</th>
        <th>Payment Method</th>
        <th>Order Payment State</th>
        <th>Order Address</th>
        <th>Amount</th>
        <th>Date</th>
        <th>View</th>
        <th>Repeat</th>
    </tr>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.id}</td>
            <td>${order.deliveryMethod}</td>
            <td>${order.orderStatus}</td>
            <td>${order.paymentMethod}</td>
            <td>${order.paymentState}</td>
            <td hidden>${order.addressId}</td>
            <td>${order.address.toString()}</td>
            <td style="color:red;">
                <fmt:formatNumber value="${order.amount}" type="number"/>
            </td>
            <td>${order.datetime}</td>
            <td><a href="${pageContext.request.contextPath}/orders/${order.id}">
                View</a></td>
            <td><a href="${pageContext.request.contextPath}/orders/repeat/${order.id}">
                Repeat</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>