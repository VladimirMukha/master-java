package app.dao;

import app.cache.CartCache;
import app.model.Address;
import app.model.Order;
import app.model.OrderList;
import app.model.Product;
import app.model.cart.*;
import app.model.enums.DeliveryMethod;
import app.model.enums.OrderStatus;
import app.model.enums.PaymentMethod;
import app.model.enums.PaymentState;
import app.util.AppUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class OrderDAOImpl implements OrderDAO {
    private SessionFactory sessionFactory;
    private ProductDAO productDAO;
    private ScoreboardDAO scoreboardDAO;

    @Autowired
    public void setScoreboardDAO(ScoreboardDAO scoreboardDAO) {
        this.scoreboardDAO = scoreboardDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order save(DeliveryMethod deliveryMethod, PaymentMethod paymentMethod, Address address,
                      Cart cart, HttpServletRequest request) {
        Session session = sessionFactory.getCurrentSession();
        Integer userId = AppUtils.getUserIdFromSession(request);
        Map<Product, Integer> mapToScoreboard = new HashMap<>();
        Order order = new Order();
        order.setAmount(cart.getAmountTotal());
        order.setUserId(userId);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setPaymentState(PaymentState.NOT_PAID);
        order.setDeliveryMethod(deliveryMethod);
        order.setPaymentMethod(paymentMethod);
        order.setAddress(address);
        for (CartLine cartLine : cart.getCartLines()){
            OrderList list = new OrderList();
            list.setOrder(order);
            list.setAmount(cartLine.getAmount());
            list.setPrice(cartLine.getProductDTO().getPrice());
            list.setProductId(cartLine.getProductDTO().getId());
            list.setQuantity(cartLine.getQuantity());
            order.getOrderList().add(list);
            Product product = productDAO.getProductById(cartLine.getProductDTO().getId());
            product.setQuantityInStock(product.getQuantityInStock() - cartLine.getQuantity());
            mapToScoreboard.put(productDAO.getProductById(list.getProductId()), list.getQuantity());
        }
        Integer orderId = (Integer) session.save(order);
        if (orderId != null) {
            CartCache.getInstance().remove(userId);
        }
        return this.getOrderById(orderId);
    }

    @Override
    public Order getOrderById(Integer id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Order.class, id);
    }

    @Override
    public OrderDTO getOrderDTOById(Integer id) {
        Order order = this.getOrderById(id);
        if (order == null)
            return null;
        return new OrderDTO(order.getId(), order.getAddressId(),
                order.getUserId(), order.getAmount(), order.getOrderNum());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OrderListDTO> listOrderDTO(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select new" + OrderListDTO.class.getName()
                + "d.id, d.product, d.quantity, d.price, d.amount)"
                + "from" + OrderList.class.getName() + " d "
                + "where d.order.id=:id");
        query.setParameter("id", id);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Order where userId =:userId").setParameter("userId", userId);
        return (List<Order>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Order> getAllOrders() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Order ").list();
    }

    @Override
    public Order edit(Order order) {
        Session session = sessionFactory.getCurrentSession();
        return (Order) session.merge(order);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Integer, Integer> getTenProducts() {
        Session session = sessionFactory.getCurrentSession();
        Map<Integer, Integer> tenProducts = new TreeMap<>();
        List<Integer> productIds;
        List<Integer> counts;
        Query queryToFindProductIds = session.createNativeQuery("select product_id from order_list group by product_id " +
                "order by count (product_id) desc limit 10");
        Query queryToFindCounts = session.createNativeQuery("select sum(quantity) from order_list group by product_id" +
                " order by count (product_id) desc limit 10");
        productIds = queryToFindProductIds.getResultList();
        counts = queryToFindCounts.getResultList();
        for (int i = 0; i < productIds.size(); i++){
            tenProducts.put(productIds.get(i), counts.get(i));
        }
        return tenProducts;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Integer, Integer> getTenUsers() {
        Session session = sessionFactory.getCurrentSession();
        Map<Integer, Integer> tenUsers = new TreeMap<>();
        List<Integer> userIds;
        List<Integer> counts;
        Query queryToFindUserIds = session.createQuery("select userId from Order group by userId " +
                "order by count (userId) desc");
        Query queryToFindCounts = session.createQuery("select sum(amount) from Order group by userId" +
                " order by count (userId) desc");
        userIds = queryToFindUserIds.getResultList();
        counts = queryToFindCounts.getResultList();
        for (int i = 0; i < userIds.size(); i++){
            tenUsers.put(userIds.get(i), counts.get(i));
        }
        return tenUsers;
    }

    @Override
    public Double getMonthlyIncome() {
        Session session = sessionFactory.getCurrentSession();
        return (Double) session.createQuery("select sum(amount) from Order" +
                " where datetime > (current_date - 30)").getSingleResult();
    }

    @Override
    public Double getDailyIncome() {
        Session session = sessionFactory.getCurrentSession();
        return (Double) session.createQuery("select sum(amount) from Order" +
                " where datetime = current_date").getSingleResult();
    }

    @Override
    public Date getOrderDateById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return (Date) session.createQuery("select datetime from Order where id =: id")
                .setParameter("id", id).getSingleResult();
    }
}