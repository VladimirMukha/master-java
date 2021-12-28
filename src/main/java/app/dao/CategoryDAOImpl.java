package app.dao;

import app.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO{
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Category> allCategories() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Category").list();
    }

    @Override
    public Category getCategoryById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Category.class, id);
    }

    @Override
    public Category getCategoryByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        try {
            return (Category)session.createQuery("from Category where name =: name")
                    .setParameter("name", name).getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Integer add(Category category) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.save(category);
    }
}
