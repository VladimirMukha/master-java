package app;

import app.model.Category;
import app.model.Product;
import app.service.ProductService;
import app.validators.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ProductValidatorTest {

    @InjectMocks
    private ProductValidator productValidator;
    @Mock
    private ProductService productService;

    private static Product product;
    private static Errors errors;

    private static final int QUANTITY_VALID = 10;
    private static final int QUANTITY_INVALID = -1;
    private static final String COLOR_VALID = "blue";
    private static final String COLOR_INVALID = "1blue";
    private static final int SIZE_VALID = 10;
    private static final int SIZE_INVALID = -1;
    private static final String BRAND_VALID = "blue";
    private static final String BRAND_INVALID = "1blue";
    private static final int PRICE_VALID = 10;
    private static final int PRICE_INVALID = -1;
    private static final String NAME_VALID = "blue";
    private static final String NAME_INVALID = "1blue";
    private static final String EMPTY_STRING = "";

    @BeforeEach
    public void setUp() {
        product = Product.newBuilder()
                .id(2)
                .name("nam")
                .price(1020)
                .brand("GJ")
                .size(32)
                .category(new Category(2, "jee"))
                .categoryId(2)
                .color("blue")
                .quantityInStock(10)
                .build();

        errors = new BeanPropertyBindingResult(product, "product");
        lenient().when(productService.getProductById(anyInt())).thenReturn(null);
    }

    @Test
    public void validateProductValid() {
        productValidator.validate(product, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateProductNameEmpty() {
        product.setName(EMPTY_STRING);
        productValidator.validate(product, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("name"));
    }
}
