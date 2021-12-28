package app;

import app.dao.CategoryDAOImpl;
import app.model.Category;
import app.service.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryDAOImpl categoryDAO;

    private static Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setName("first");
        category.setId(15);
    }

    @Test
    public void testFindCategoryById() {
        given(categoryDAO.getCategoryById(category.getId())).willReturn(category);
        Category expected = categoryService.getCategoryById(category.getId());
        assertNotNull(expected);
    }

    @Test
    public void testFindCategoryByName() {
        given(categoryDAO.getCategoryByName(category.getName())).willReturn(category);
        Category expected = categoryService.getCategoryByName(category.getName());
        assertNotNull(expected);
    }

    @Test
    public void testAddCategory() {
        given(categoryDAO.add(category)).willReturn(category.getId());
        Integer idExpected = categoryService.add(category);
        assertNotNull(idExpected);
        verify(categoryDAO).add(any(Category.class));
    }

    @Test
    public void testGetAllCategories() {
        List<Category> list = new ArrayList<>();
        list.add(category);
        list.add(new Category(16, "second"));
        list.add(new Category(17, "third"));
        given(categoryDAO.allCategories()).willReturn(list);
        List<Category> expected = categoryService.allCategories();
        assertEquals(expected, list);
    }

}
