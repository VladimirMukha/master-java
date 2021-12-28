package app.dao;

import app.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class ScoreboardDAOImpl implements ScoreboardDAO {
    private SessionFactory sessionFactory;
    private ProductDAO productDAO;

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> getMapToScoreboard() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery("select id from products " +
                " order by price desc limit 10");
        List<Integer> ids = query.getResultList();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            products.add(productDAO.getProductById(ids.get(i)));
        }
        return products;
    }

}
