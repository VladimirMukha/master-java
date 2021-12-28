package app.validators;

import app.model.Category;
import app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class CategoryValidator implements Validator {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Category.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Category category = (Category) o;

        Pattern validName = Pattern.compile("^[a-zA-Z]+$");
        if (category.getName() == null || category.getName().equals("")) {
            errors.rejectValue("name", "NotEmpty");
        } else if (categoryService.getCategoryByName(category.getName()) != null) {
            errors.rejectValue("name", "name[duplicate]");
        } else if (!validName.matcher(category.getName()).find()) {
            errors.rejectValue("name", "name[invalidValue]");
        }
    }
}
