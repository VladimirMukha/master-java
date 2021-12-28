package app.service;

import app.model.Product;
import app.model.ProductFilter;
import app.model.cart.ProductDTO;

import java.util.List;

public interface ProductService {
    List<Product> allProducts(int page, ProductFilter filter);

    int productCount();

    Integer add(Product product);

    void delete(Product product);

    void edit(Product product);

    Product getProductById(int id);

    Product getProductByName(String name);

    void save(ProductDTO productDTO);

    Integer getQuantityOfProduct(ProductDTO productDTO);
}



