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

<div class="page-title">Order Info</div>
<div class="customer-info-container">
    <h3>Successfully create order</h3>
    <ul>
        <li>Your order's number: ${order.id}</li>
        <li>Order's amount: ${order.amount}</li>
    </ul>
</div>

<a class="navi-item"
   href="${pageContext.request.contextPath}/products">Continue
    Buy</a>
</body>
</html>