package app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer price;
    @Column
    private Integer size;
    @Column
    private String brand;
    @Column
    private String color;
    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Transient
    private Integer categoryId;

    public Product() {
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", size=" + size +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", quantityInStock=" + quantityInStock +
                ", category=" + category +
                ", categoryId=" + categoryId +
                '}';
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public static Builder newBuilder() {
        return new Product().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder id(Integer id) {
            Product.this.id = id;
            return this;
        }

        public Builder name(String name) {
            Product.this.name = name;
            return this;
        }

        public Builder price(Integer price) {
            Product.this.price = price;
            return this;
        }

        public Builder size(Integer size) {
            Product.this.size = size;
            return this;
        }

        public Builder brand(String brand) {
            Product.this.brand = brand;
            return this;
        }

        public Builder color(String color) {
            Product.this.color = color;
            return this;
        }

        public Builder quantityInStock(Integer quantityInStock) {
            Product.this.quantityInStock = quantityInStock;
            return this;
        }

        public Builder category(Category category) {
            Product.this.category = category;
            return this;
        }

        public Builder categoryId(Integer categoryId) {
            Product.this.categoryId = categoryId;
            return this;
        }


        public Product build() {
            return Product.this;
        }

    }
}
