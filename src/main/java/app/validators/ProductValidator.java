package app.validators;

import app.model.Product;
import app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class ProductValidator implements Validator {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Product product = (Product) o;

        Pattern validName = Pattern.compile("^[a-zA-Z]+$");
        if (product.getName() == null || product.getName().equals("")) {
            errors.rejectValue("name", "NotEmpty");
        } else if (!(product.getName().length() > 1 && product.getName().length() < 30
                && validName.matcher(product.getName()).find())) {
            errors.rejectValue("name", "productName[invalidValue]");
        } else if (productService.getProductByName(product.getName()) != null) {
            errors.rejectValue("name", "productName[duplicate]");
        }

        Pattern validNumber = Pattern.compile("\\d+");
        if (product.getPrice() == null) {
            errors.rejectValue("price", "NotEmpty");
        } else if (!(product.getPrice() > 0 && validNumber.matcher(product.getPrice().toString()).find())) {
            errors.rejectValue("price", "price[invalidValue]");
        }

        if (product.getCategoryId() == null) {
            errors.rejectValue("categoryId", "NotEmpty");
        }

        if (product.getSize() == null) {
            errors.rejectValue("size", "NotEmpty");
        } else if (!(product.getSize() > 0 && validNumber.matcher(product.getSize().toString()).find())) {
            errors.rejectValue("size", "size[invalidValue]");
        }

        if (product.getBrand() == null || product.getBrand().equals("")) {
            errors.rejectValue("brand", "NotEmpty");
        } else if (!(product.getBrand().length() > 1 && product.getBrand().length() < 30
                && validName.matcher(product.getBrand()).find())) {
            errors.rejectValue("brand", "brand[invalidValue]");
        }

        if (product.getColor() == null || product.getColor().equals("")) {
            errors.rejectValue("color", "NotEmpty");
        } else if (!(product.getColor().length() > 1 && product.getColor().length() < 30
                && validName.matcher(product.getColor()).find())) {
            errors.rejectValue("color", "color[invalidValue]");
        }

        if (product.getQuantityInStock() == null) {
            errors.rejectValue("quantityInStock", "NotEmpty");
        } else if (!(product.getQuantityInStock() > 0
                && validNumber.matcher(product.getQuantityInStock().toString()).find())) {
            errors.rejectValue("quantityInStock", "quantity[invalidValue]");
        }

    }
}
