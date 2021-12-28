package app.cache;

import app.model.cart.Cart;
import app.model.cart.CartLine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CartCache {

    private static final CartCache instance = new CartCache();

    public static CartCache getInstance() {
        return instance;
    }

    private volatile ConcurrentHashMap<Key, Cart> userCartCache = new ConcurrentHashMap<>();

    public void put(Integer key, Cart data) {
        userCartCache.put(new Key(key), data);
    }

    public void merge(Integer key, Cart data) {
        Cart existCart = get(key);
        if (existCart != null) {
            mergeCartLists(data, existCart);
        }
        userCartCache.put(new Key(key), data);
    }

    public boolean containsKey(Integer key) {
        return userCartCache.containsKey(new Key(key));
    }

    public Cart get(Integer key) {
        return userCartCache.get(new Key(key));
    }

    public void remove(Integer key) {
        userCartCache.remove(new Key(key));
    }

    public void removeAll() {
        userCartCache.clear();
    }

    private void mergeCartLists(Cart data, Cart existCart) {
        List<CartLine> cartLinesFromData = data.getCartLines();
        List<CartLine> cartLinesFromExistCart = existCart.getCartLines();
        List<CartLine> retainList = new ArrayList<>(cartLinesFromData);
        int count = 0;
        if (!cartLinesFromData.isEmpty()) {
            for (CartLine cartLineFromData : cartLinesFromData) {
                for (CartLine cartLineFromExistCart : cartLinesFromExistCart) {
                    if (cartLineFromData.getProductDTO().getId().equals(cartLineFromExistCart.getProductDTO().getId())) {
                        count++;
                    }
                }
                if (count == 0) {
                    retainList.add(cartLineFromData);
                }
                count = 0;
            }
            data.setCartLines(retainList);
        } else {
            data = existCart;
        }
    }

    private static class Key {

        private final Object key;

        public Key(Object key) {
            this.key = key;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Key other = (Key) obj;
            return this.key == other.key || (this.key != null && this.key.equals(other.key));
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 43 * hash + (this.key != null ? this.key.hashCode() : 0);
            return hash;
        }

        @Override
        public java.lang.String toString() {
            return super.toString();
        }
    }
}