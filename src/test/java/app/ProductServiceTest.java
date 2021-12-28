package app;

import app.dao.CategoryDAOImpl;
import app.dao.ProductDAOImpl;
import app.model.Category;
import app.model.Product;
import app.model.cart.ProductDTO;
import app.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductDAOImpl productDAO;
    @Mock
    private CategoryDAOImpl categoryDAO;

    private static Product product;

    @BeforeEach
    public void setUp() {
        Category category = new Category(1, "new");
        product = Product.newBuilder()
                .id(1)
                .name("name")
                .price(1000)
                .brand("GJ")
                .category(category)
                .categoryId(category.getId())
                .color("blue")
                .quantityInStock(10)
                .size(32)
                .build();
    }

    @Test
    public void testFindProductById() {
        given(productDAO.getProductById(product.getId())).willReturn(product);
        Product expected = productService.getProductById(product.getId());
        assertNotNull(expected);
    }

    @Test
    public void testFindProductByName() {
        given(productDAO.getProductByName(product.getName())).willReturn(product);
        Product expected = productService.getProductByName(product.getName());
        assertNotNull(expected);
    }

    @Test
    public void testGetAllProducts() {
        Product newProduct = Product.newBuilder()
                .id(2)
                .name("nam")
                .price(1020)
                .brand("G")
                .size(32)
                .category(new Category(2, "jee"))
                .categoryId(2)
                .color("blue")
                .build();

        List<Product> list = new ArrayList<>();
        list.add(product);
        list.add(newProduct);

        given(productDAO.allProducts(1, null)).willReturn(list);
        List<Product> expected = productService.allProducts(1, null);
        assertEquals(expected, list);
    }

    @Test
    public void testAddProduct() {
        given(productDAO.add(product)).willReturn(product.getId());
        Integer idExpected = productService.add(product);
        assertNotNull(idExpected);
        verify(productDAO).add(any(Product.class));
    }

    @Test
    public void testProductCount() {
        Integer expected = productService.productCount();
        assertNotNull(expected);
    }

    @Test
    public void testGetQuantityOfProduct() {
        ProductDTO productDTO = new ProductDTO(product);
        Integer expected = productService.getQuantityOfProduct(productDTO);
        assertNotNull(expected);
    }

    @Test
    public void testDeleteProduct() {
        productService.delete(product);
        verify(productDAO).delete(product);
    }

    @Test
    public void testEditProduct() {
        productService.edit(product);
        verify(productDAO).edit(product);
    }

    @Test
    public void testSaveProduct() {
        ProductDTO productDTO = new ProductDTO(product);
        productService.save(productDTO);
        verify(productDAO).save(productDTO);
    }
}
