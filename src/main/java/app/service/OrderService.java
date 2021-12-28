package app.service;

import app.model.Order;
import app.model.Product;
import app.model.User;
import app.model.enums.DeliveryMethod;
import app.model.enums.PaymentMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order save(DeliveryMethod deliveryMethod, PaymentMethod paymentMethod, Integer addressId,
               HttpServletRequest request, HttpServletResponse response);

    Order getOrderById(Integer id);

    List<Order> getOrdersByUserId(Integer userId);

    List<Order> getAllOrders();

    Order edit(Order order);

    Map<Product, Integer> getTenProducts();

    Map<User, Integer> getTenUsers();

    Double getMonthlyIncome();

    Double getDailyIncome();

    Order repeat(DeliveryMethod deliveryMethod, PaymentMethod paymentMethod, Integer addressId,
                 HttpServletRequest request, HttpServletResponse response, Integer orderId);
}