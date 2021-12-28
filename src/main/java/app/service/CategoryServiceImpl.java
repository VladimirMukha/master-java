package app.service;

import app.dao.CategoryDAO;
import app.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryDAO categoryDAO;

    @Autowired
    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Transactional
    @Override
    public List<Category> allCategories() {
        return categoryDAO.allCategories();
    }

    @Transactional
    @Override
    public Category getCategoryById(int id) {
        return categoryDAO.getCategoryById(id);
    }

    @Transactional
    @Override
    public Category getCategoryByName(String name) {
        return categoryDAO.getCategoryByName(name);
    }

    @Transactional
    @Override
    public Integer add(Category category) {
        return categoryDAO.add(category);
    }
}
