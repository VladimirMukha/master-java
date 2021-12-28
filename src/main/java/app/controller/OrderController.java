package app.controller;

import app.model.Address;
import app.model.Order;
import app.model.Product;
import app.model.User;
import app.model.cart.ProductDTO;
import app.model.enums.DeliveryMethod;
import app.model.enums.OrderStatus;
import app.model.enums.PaymentMethod;
import app.model.enums.PaymentState;
import app.service.AddressService;
import app.service.CartService;
import app.service.OrderService;
import app.service.UserService;
import app.util.AppUtils;
import app.util.CartCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@EnableWebMvc
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private CartService cartService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ModelAndView createOrder(@ModelAttribute Order orderInfo,
                                    HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        if (!cartService.isCartValid(request)) {
            List<ProductDTO> errorList = cartService.getErrorList(request);
            modelAndView.setViewName("error");
            modelAndView.addObject("errorList", errorList);
            return modelAndView;
        }
        DeliveryMethod deliveryMethod = orderInfo.getDeliveryMethod();
        PaymentMethod paymentMethod = orderInfo.getPaymentMethod();
        Integer addressId = orderInfo.getAddressId();
        Order order = orderService.save(deliveryMethod, paymentMethod, addressId, request, response);
        modelAndView.setViewName("show");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getOrderListPage(HttpServletRequest request) {
        Integer userId = AppUtils.getUserIdFromSession(request);
        List<Order> orders = orderService.getOrdersByUserId(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderList");
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    @GetMapping("/manage")
    public ModelAndView getManagePage() {
        List<Order> orders = orderService.getAllOrders();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manageOrderList");
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    @GetMapping("/manage/{id}")
    public ModelAndView getManageOrderPage(@PathVariable("id") Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editOrder");
        modelAndView.addObject("order", order);
        modelAndView.addObject("statuses", OrderStatus.values());
        modelAndView.addObject("states", PaymentState.values());
        return modelAndView;
    }

    @PostMapping("/manage")
    public ModelAndView manageOrder(@ModelAttribute("order") Order order) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/orders/manage");
        orderService.edit(order);
        return modelAndView;
    }

    @GetMapping("/statistics")
    public ModelAndView getStatisticsPage() {
        Map<Product, Integer> products = orderService.getTenProducts();
        Map<User, Integer> users = orderService.getTenUsers();
        Double monthlyIncome = orderService.getMonthlyIncome();
        Double dailyIncome = orderService.getDailyIncome();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("statistics");
        modelAndView.addObject("products", products);
        modelAndView.addObject("users", users);
        modelAndView.addObject("monthlyIncome", monthlyIncome);
        modelAndView.addObject("dailyIncome", dailyIncome);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getOrderView(@PathVariable("id") Integer id) {
        Order order = orderService.getOrderById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("viewOrder");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @GetMapping("/repeat/{id}")
    public ModelAndView getRepeatPage(@PathVariable("id") Integer id, HttpServletRequest request) {
        Order order = orderService.getOrderById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("repeatOrder");
        modelAndView.addObject("order", order);
        User user = userService.findById(AppUtils.getUserIdFromSession(request));
        List<Address> addresses = user.getAddresses();
        modelAndView.addObject("addresses", addresses);
        modelAndView.addObject("deliveryMethods", DeliveryMethod.values());
        modelAndView.addObject("paymentMethods", PaymentMethod.values());
        return modelAndView;
    }

    @PostMapping("/repeat")
    public ModelAndView repeatOrder(@ModelAttribute Order orderInfo,
                                    HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();

        DeliveryMethod deliveryMethod = orderInfo.getDeliveryMethod();
        PaymentMethod paymentMethod = orderInfo.getPaymentMethod();
        orderService.repeat(deliveryMethod, paymentMethod, orderInfo.getAddressId(), request, response, orderInfo.getId());
        if (!cartService.isCartValid(request)) {
            List<ProductDTO> errorList = cartService.getErrorList(request);
            modelAndView.setViewName("error");
            modelAndView.addObject("errorList", errorList);
            return modelAndView;
        }
        Order newOrder = orderService.save(deliveryMethod, paymentMethod, orderInfo.getAddressId(), request, response);
        modelAndView.setViewName("show");
        modelAndView.addObject("order", newOrder);
        return modelAndView;
    }
}
