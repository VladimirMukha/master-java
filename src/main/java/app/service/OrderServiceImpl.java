package app.service;

import app.dao.AddressDAO;
import app.dao.OrderDAO;
import app.dao.ProductDAO;
import app.dao.UserDAO;
import app.model.*;
import app.model.cart.Cart;
import app.model.cart.CartLine;
import app.model.cart.ProductDTO;
import app.model.enums.DeliveryMethod;
import app.model.enums.PaymentMethod;
import app.util.AppUtils;
import app.util.CartCacheUtils;
import app.util.ProductComparator;
import app.util.UserComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private AddressDAO addressDAO;
    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Transactional
    @Override
    public Order save(DeliveryMethod deliveryMethod, PaymentMethod paymentMethod, Integer addressId,
                      HttpServletRequest request, HttpServletResponse response) {
        boolean isAuth = AppUtils.isAuthUser();
        Cart cart = isAuth ? CartCacheUtils.getCart(request) : AppUtils.getCartFromSession(request);
        Address address = addressDAO.findAddressById(addressId);
        Order order = orderDAO.save(deliveryMethod, paymentMethod, address, cart, request);
        if (!isAuth && order != null) {
            AppUtils.removeCartFromSession(request);
        }
        return order;
    }

    @Transactional
    @Override
    public Order getOrderById(Integer id) {
        return orderDAO.getOrderById(id);
    }

    @Transactional
    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    @Transactional
    @Override
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    @Transactional
    @Override
    public Order edit(Order order) {
        Address address = addressDAO.findAddressById(order.getAddressId());
        List<OrderList> orderLists = getOrderById(order.getId()).getOrderList();
        Date orderDate = orderDAO.getOrderDateById(order.getId());
        order.setDatetime(orderDate);
        order.setAddress(address);
        order.getOrderList().clear();
        order.getOrderList().addAll(orderLists);
        return orderDAO.edit(order);
    }

    @Transactional
    @Override
    public Map<Product, Integer> getTenProducts() {
        Map<Integer, Integer> idsAndCount = orderDAO.getTenProducts();
        ProductComparator productComparator = new ProductComparator();
        Map<Product, Integer> productAndCount = new TreeMap<>(productComparator);
        for (Map.Entry<Integer, Integer> pair : idsAndCount.entrySet()) {
            productAndCount.put(productDAO.getProductById(pair.getKey()), pair.getValue());
        }
        return AppUtils.sortByValue(productAndCount);
    }

    @Transactional
    @Override
    public Map<User, Integer> getTenUsers() {
        Map<Integer, Integer> idsAndCount = orderDAO.getTenUsers();
        UserComparator userComparator = new UserComparator();
        Map<User, Integer> userAndCount = new TreeMap<>(userComparator);
        for (Map.Entry<Integer, Integer> pair : idsAndCount.entrySet()) {
            userAndCount.put(userDAO.findById(pair.getKey()), pair.getValue());
        }
        return AppUtils.sortByValue(userAndCount);
    }

    @Transactional
    @Override
    public Double getMonthlyIncome() {
        return orderDAO.getMonthlyIncome();
    }

    @Transactional
    @Override
    public Double getDailyIncome() {
        return orderDAO.getDailyIncome();
    }

    @Transactional
    @Override
    public Order repeat(DeliveryMethod deliveryMethod, PaymentMethod paymentMethod, Integer addressId,
                        HttpServletRequest request, HttpServletResponse response, Integer orderId) {
        Order order = orderDAO.getOrderById(orderId);
        Cart cart = new Cart();
        List<CartLine> cartLines = new ArrayList<>();
        List<OrderList> orderLists = order.getOrderList();
        for (OrderList orderList : orderLists) {
            Product product = productDAO.getProductById(orderList.getProductId());
            cartLines.add(new CartLine(new ProductDTO(product), orderList.getQuantity()));
        }
        cart.setCartLines(cartLines);
        CartCacheUtils.putCart(request, cart);
        return order;
    }

}
