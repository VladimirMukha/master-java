package app.util;

import app.model.Product;
import jakarta.validation.Valid;

import java.util.Comparator;

@Valid
public class ProductComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
