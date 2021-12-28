package app.service;

import app.model.cart.Cart;
import app.model.cart.ProductDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {

    Cart addProduct(HttpServletRequest request, HttpServletResponse response, Integer id);
    Cart deleteProduct(Integer id, HttpServletRequest request, HttpServletResponse response);
    List<ProductDTO> getErrorList(HttpServletRequest request);
    boolean isCartValid(HttpServletRequest request);
}
