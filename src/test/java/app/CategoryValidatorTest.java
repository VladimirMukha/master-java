package app;

import app.model.Category;
import app.service.CategoryService;
import app.validators.CategoryValidator;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class CategoryValidatorTest {
    @InjectMocks
    private CategoryValidator categoryValidator;
    @Mock
    private CategoryService categoryService;

    private static Category category;
    private static Errors errors;


    private static final String NAME_VALID = "blue";
    private static final String NAME_INVALID = "1blue";
    private static final String EMPTY_STRING = "";

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setName(NAME_VALID);
        errors = new BeanPropertyBindingResult(category, "category");
        lenient().when(categoryService.getCategoryById(anyInt())).thenReturn(null);
    }

    @Test
    public void validateCategoryValid() {
        categoryValidator.validate(category, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateCategoryNameEmpty() {
        category.setName(EMPTY_STRING);
        categoryValidator.validate(category, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("name"));
    }
}

