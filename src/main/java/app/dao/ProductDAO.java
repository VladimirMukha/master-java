package app.dao;

import app.model.Product;
import app.model.ProductFilter;
import app.model.cart.ProductDTO;

import java.util.List;

public interface ProductDAO {
    List<Product> allProducts(int page, ProductFilter filter);
    int productCount();
    int add(Product product);
    void delete(Product product);
    void edit(Product product);
    Product getProductById(int id);
    Product getProductByName(String name);
    void save(ProductDTO productDTO);
    ProductDTO getProductDTOById(int id);
    Integer getQuantityOfProduct(ProductDTO productDTO);
}


