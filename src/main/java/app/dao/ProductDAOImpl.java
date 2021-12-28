package app.dao;

import app.model.Product;
import app.model.ProductFilter;
import app.model.cart.ProductDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO{
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Product> allProducts(int page, ProductFilter filter) {
        Session session = sessionFactory.getCurrentSession();
        if (filter == null) {
            return session.createQuery("from Product").setFirstResult(10 * (page - 1)).setMaxResults(10).list();
        }
        return session.createQuery("from Product where " + filter.getQuery()).setProperties(filter)
                .setFirstResult(10 * (page - 1)).setMaxResults(10).list();
    }

    @Override
    public int productCount() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count (*) from Product", Number.class).getSingleResult().intValue();
    }

    @Override
    public int add(Product product) {
        Session session = sessionFactory.getCurrentSession();
        return (int) session.save(product);
    }

    @Override
    public void delete(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(product);
    }

    @Override
    public void edit(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.update(product);
    }

    @Override
    public Product getProductById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Product.class, id);
    }

    @Override
    public Product getProductByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        try {
            return (Product)session.createQuery("from Product where name =: name")
                    .setParameter("name", name).getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void save(ProductDTO productDTO) {
        Product product = null;
        boolean isNew = false;
        Integer id = productDTO.getId();
        if (id != null)
            product = this.getProductById(id);
        if (product == null){
            isNew = true;
            product = new Product();
        }
        product.setId(id);
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setSize(productDTO.getSize());
        product.setBrand(productDTO.getBrand());
        product.setColor(productDTO.getColor());
        product.setQuantityInStock(productDTO.getQuantityInStock());
        if (isNew)
            this.sessionFactory.getCurrentSession().persist(product);
        this.sessionFactory.getCurrentSession().flush();
    }

    @Override
    public ProductDTO getProductDTOById(int id) {
        Product product = this.getProductById(id);
        if (product == null)
            return null;
        return new ProductDTO(product);
    }

    @Override
    public Integer getQuantityOfProduct(ProductDTO productDTO) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createQuery("select quantityInStock from Product where id =: productId")
                .setParameter("productId", productDTO.getId()).getSingleResult();
    }
}
