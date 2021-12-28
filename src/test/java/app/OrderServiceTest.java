package app;

import app.dao.AddressDAOImpl;
import app.dao.OrderDAOImpl;
import app.dao.ProductDAOImpl;
import app.dao.UserDAOImpl;
import app.model.Product;
import app.model.Order;
import app.model.OrderList;
import app.model.Address;
import app.model.User;
import app.model.enums.PaymentMethod;
import app.model.enums.OrderStatus;
import app.model.enums.PaymentState;
import app.model.enums.UserStatus;
import app.model.enums.UserRole;
import app.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderDAOImpl orderDAO;
    @Mock
    private AddressDAOImpl addressDAO;
    @Mock
    private UserDAOImpl userDAO;
    @Mock
    private ProductDAOImpl productDAO;

    private static Order order;
    private static Order newOrder;
    private static List<Order> list;
    private static Address address;
    private static User user;

    @BeforeEach
    public void setUp() {
        OrderList orders = new OrderList(1, 1000, 2, 2000, order, 1);
        List<OrderList> orderList = new ArrayList<>();
        orderList.add(orders);

        user = User.newBuilder()
                .id(1)
                .dateOfBirth(new Date())
                .email("mail@mail.com")
                .firstName("Egor")
                .lastName("Bannikov")
                .password("11111111")
                .passwordConfirm("11111111")
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .build();


        address = Address.newBuilder()
                .id(1)
                .country("Russia")
                .city("Moscow")
                .index(456345)
                .street("Street")
                .building(100)
                .room(10)
                .user(user)
                .build();

        order = Order.newBuilder()
                .id(71)
                .orderList(orderList)
                .orderNum(10)
                .orderStatus(OrderStatus.DELIVERED)
                .address(address)
                .addressId(address.getId())
                .amount((double) 3000)
                .datetime(new Date())
                .paymentMethod(PaymentMethod.CARD)
                .paymentState(PaymentState.NOT_PAID)
                .userId(user.getId())
                .build();

        orders.setOrder(order);

        newOrder = Order.newBuilder()
                .id(2)
                .orderList(orderList)
                .orderNum(12)
                .orderStatus(OrderStatus.DELIVERED)
                .address(address)
                .addressId(address.getId())
                .amount((double) 3100)
                .datetime(new Date())
                .paymentMethod(PaymentMethod.CARD)
                .paymentState(PaymentState.NOT_PAID)
                .userId(user.getId())
                .build();

        list = new ArrayList<>();
        list.add(order);
        list.add(newOrder);
    }


    @Test
    public void testGetOrderById() {
        given(orderDAO.getOrderById(order.getId())).willReturn(order);
        Order expected = orderService.getOrderById(order.getId());
        assertNotNull(expected);
    }

    @Test
    public void testGetOrdersByUserId() {
        given(orderDAO.getOrdersByUserId(user.getId())).willReturn(list);
        List<Order> expected = orderService.getOrdersByUserId(user.getId());
        assertEquals(expected, list);
    }

    @Test
    public void testGetAllOrders() {
        given(orderDAO.getAllOrders()).willReturn(list);
        List<Order> expected = orderService.getAllOrders();
        assertEquals(expected, list);
    }

    @Test
    public void testGetMonthlyIncome() {
        Double expected = orderService.getMonthlyIncome();
        assertNotNull(expected);
    }

    @Test
    public void testGetDailyIncome() {
        Double expected = orderService.getDailyIncome();
        assertNotNull(expected);
    }

    @Test
    public void testGetTenProducts() {
        Map<Product, Integer> expected = orderService.getTenProducts();
        assertNotNull(expected);
    }

    @Test
    public void testGetTenUsers() {
        Map<User, Integer> expected = orderService.getTenUsers();
        assertNotNull(expected);
    }

}
