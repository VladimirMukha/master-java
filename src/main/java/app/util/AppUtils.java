package app.util;

import app.model.cart.Cart;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class AppUtils {

    public static final Logger log = LoggerFactory.getLogger(AppUtils.class);
    /**
     * Название куки для хранения корзины
     */
    public static final String CART_COOKIE_NAME = "cart";
    /**
     * Название атрибута, содержащего идентификатор юзера, в сессии
     */
    public static final String USER_ID_IN_SESSION = "session_userId";
    /**
     * 30 дней
     */
    private static final int COOKIE_MAX_AGE = 2592000;

    private static final Gson gson = new Gson();

    public static void setCartToSession(HttpServletRequest request, Cart cart) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.setAttribute(CART_COOKIE_NAME, cart);
        }
    }

    public static Cart getCartFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session != null ? (Cart) session.getAttribute(CART_COOKIE_NAME) : null;
    }

    public static void removeCartFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute(CART_COOKIE_NAME);
        }
    }

    public static Integer getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session != null ? (Integer) session.getAttribute(USER_ID_IN_SESSION) : null;
    }

    public static void setUserIdInSession(HttpServletRequest request, Integer userId) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.setAttribute(USER_ID_IN_SESSION, userId);
        }
    }

    public static boolean isAuthUser() {
        return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();

        st.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> result.put(e.getKey(), e.getValue()));

        return result;
    }
    //TODO Implement cookie

//    public static Cart getCartFromCookie(HttpServletRequest request){
//        Cookie cookie = getCookieByName(request, CART_COOKIE_NAME);
//        try {
//            return cookie != null ? gson.fromJson(URLDecoder.decode(cookie.getValue(), "UTF-8"), Cart.class) : new Cart();
//        } catch (UnsupportedEncodingException e) {
//            log.error(e.getMessage(), e);
//            return null;
//        }
//    }

//    public static void setCartToCookie(HttpServletRequest request, HttpServletResponse response, Cart cart) {
//        if (cart != null && !cart.isEmpty() && request !=null && response != null) {
//            Cookie cookie = AppUtils.getCookieByName(request, AppUtils.CART_COOKIE_NAME);
//            try {
//                String cartToCookie = URLEncoder.encode(gson.toJson(cart), "UTF-8");
//                if (cookie == null) {
//                    cookie = new Cookie(CART_COOKIE_NAME, cartToCookie);
//                } else {
//                    cookie.setValue(cartToCookie);
//                }
//                cookie.setMaxAge(COOKIE_MAX_AGE);
//                response.addCookie(cookie);
//            } catch (UnsupportedEncodingException e) {
//                log.error(e.getMessage(), e);
//            }
//        }

//    }

//    public static void cleanCookie(HttpServletRequest request, HttpServletResponse response) {
//        Cookie cookie = getCookieByName(request, CART_COOKIE_NAME);
//        if (cookie != null) {
//            cookie.setMaxAge(-1);
//            response.addCookie(cookie);
//        }
//    }

//    public static Cookie getCookieByName(HttpServletRequest request, String name) {
//        Cookie[] cookies = request.getCookies();
//        if(cookies != null) {
//            for(Cookie c: cookies) {
//                if(name.equals(c.getName())) {
//                    return c;
//                }
//            }
//        }
//        return null;
//    }
}
