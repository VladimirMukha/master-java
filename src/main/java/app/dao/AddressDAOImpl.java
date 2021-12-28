package app.dao;

import app.model.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDAOImpl implements AddressDAO{

    private static final Logger log = LoggerFactory.getLogger(AddressDAOImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Address edit(Address address) {
        Session session = sessionFactory.getCurrentSession();
        return (Address) session.merge(address);
    }

    @Override
    public Address findAddressById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Address where id =:addressId")
                .setParameter("addressId", id);
        return (Address) query.getSingleResult();
    }

    @Override
    public Integer addAddress(Address address) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.save(address);
    }
}
