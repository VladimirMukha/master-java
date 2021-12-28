package app.util;

import app.cache.CartCache;
import app.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;

public class CartCacheUtils {

    public static void putCart(HttpServletRequest request, Cart cart) {
        Integer userId = AppUtils.getUserIdFromSession(request);
        if (userId != null) {
            CartCache.getInstance().put(userId, cart);
        }
    }

    public static Cart getCart(HttpServletRequest request) {
        Integer userId = AppUtils.getUserIdFromSession(request);
        return userId != null ? CartCache.getInstance().get(userId) : null;
    }

    public static void mergeCart(HttpServletRequest request, Cart cart) {
        Integer userId = AppUtils.getUserIdFromSession(request);
        CartCache.getInstance().merge(userId, cart);
    }
}
