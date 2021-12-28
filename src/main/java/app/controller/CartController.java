package app.controller;

import app.model.Address;
import app.model.Order;
import app.model.User;
import app.model.cart.Cart;
import app.model.enums.DeliveryMethod;
import app.model.enums.PaymentMethod;
import app.service.CartService;
import app.service.UserService;
import app.util.CartCacheUtils;
import app.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Controller
@EnableWebMvc
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    private UserService userService;

    @Autowired
    public void setUserService(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/finalize")
    public ModelAndView finalizeCart(HttpServletRequest request,
                                     @ModelAttribute Cart cart){
        ModelAndView modelAndView = new ModelAndView();
        if (!AppUtils.isAuthUser()) {
            AppUtils.setCartToSession(request, cart);
            modelAndView.setViewName("login");
            return modelAndView;
        }
        CartCacheUtils.putCart(request, cart);
        User user = userService.findById(AppUtils.getUserIdFromSession(request));
        List<Address> addresses = user.getAddresses();
        modelAndView.setViewName("cartConfirmation");
        modelAndView.addObject("cart", cart);
        modelAndView.addObject("order", new Order());
        modelAndView.addObject("addresses", addresses);
        modelAndView.addObject("deliveryMethods", DeliveryMethod.values());
        modelAndView.addObject("paymentMethods", PaymentMethod.values());
        return modelAndView;
    }

    @GetMapping("/confirmation")
    public ModelAndView cartConfirmationReview(HttpServletRequest request){
        Cart cart = AppUtils.isAuthUser() ? CartCacheUtils.getCart(request) : AppUtils.getCartFromSession(request);
        ModelAndView modelAndView = new ModelAndView();
        if (cart != null && cart.isEmpty()){
            modelAndView.setViewName("redirect:/cart");
            return modelAndView;
        }
        modelAndView.setViewName("cartConfirmation");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProductFromCart(@PathVariable("id") int id, HttpServletRequest request,
                                              HttpServletResponse response){
        Cart cart = cartService.deleteProduct(id, request, response);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cart");
        modelAndView.addObject("cart", cart);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView cartHandler(HttpServletRequest request){
        Cart cart = AppUtils.isAuthUser() ? CartCacheUtils.getCart(request) : AppUtils.getCartFromSession(request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cart");
        if (cart == null) {
            cart = new Cart();
        }
        modelAndView.addObject("cart", cart);
        return modelAndView;
    }
}
