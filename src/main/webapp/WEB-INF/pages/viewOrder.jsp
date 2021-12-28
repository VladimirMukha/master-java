<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="_header.jsp" />

<jsp:include page="_menu.jsp" />
<table>
    <tr>
        <th>product</th>
        <th>quantity</th>
        <th>price</th>
        <th>amount</th>
    </tr>
    <c:forEach var="orderList" items="${order.orderList}">
        <tr>
            <td>${orderList.productId}</td>
            <td>${orderList.quantity}</td>
            <td>${orderList.price}</td>
            <td>${orderList.amount}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
