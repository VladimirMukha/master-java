package app.dao;

import app.model.Address;
import app.model.Order;
import app.model.OrderList;
import app.model.Product;
import app.model.cart.Cart;
import app.model.cart.OrderDTO;
import app.model.cart.OrderListDTO;
import app.model.enums.DeliveryMethod;
import app.model.enums.PaymentMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDAO {
    Order save(DeliveryMethod deliveryMethod, PaymentMethod paymentMethod, Address address, Cart cart, HttpServletRequest request);
    Order getOrderById(Integer id);
    OrderDTO getOrderDTOById(Integer id);
    List<OrderListDTO> listOrderDTO(Integer id);
    List<Order> getOrdersByUserId(Integer userId);
    List<Order> getAllOrders();
    Order edit(Order order);
    Map<Integer, Integer> getTenProducts();
    Map<Integer, Integer> getTenUsers();
    Double getMonthlyIncome();
    Double getDailyIncome();
    Date getOrderDateById(Integer id);
}
