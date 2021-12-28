package app.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_list")
public class OrderList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private double price;
    @Column
    private int quantity;
    @Column
    private double amount;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(name = "product_id")
    private Integer productId;

    public OrderList() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderList(int id, double price, int quantity,
                     double amount, Order order, Integer productId) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.order = order;
        this.productId = productId;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
