package app.model.cart;

import app.model.Order;
import app.model.Product;

import javax.persistence.*;

public class OrderListDTO {
    private int id;
    private double price;
    private double amount;
    private int quantity;
    private Product product;

    public OrderListDTO() {
    }

    public OrderListDTO(int id, double price, double amount,
                        int quantity, Product product) {
        this.id = id;
        this.price = price;
        this.amount = amount;
        this.quantity = quantity;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
