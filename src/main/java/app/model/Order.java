package app.model;

import app.model.enums.DeliveryMethod;
import app.model.enums.OrderStatus;
import app.model.enums.PaymentMethod;
import app.model.enums.PaymentState;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "delivery_method")
    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "payment_state")
    @Enumerated(EnumType.STRING)
    private PaymentState paymentState;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Transient
    private Integer addressId;
    @Column(name = "user_id")
    private Integer userId;
    @Column
    private double amount;
    @Column(name = "order_num")
    private Integer orderNum;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderList> orderList;
    @Temporal(TemporalType.DATE)
    @Column(insertable = false)
    private Date datetime;

    public Order() {
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date date) {
        this.datetime = date;
    }

    public Address getAddress() {
        return address;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getId() {
        return id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentState getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(PaymentState paymentState) {
        this.paymentState = paymentState;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer clientId) {
        this.userId = clientId;
    }

    public List<OrderList> getOrderList() {
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        return orderList;
    }

    public static Order.Builder newBuilder() {
        return new Order().new Builder();
    }

    public void setOrderList(List<OrderList> orderList) {
        this.orderList = orderList;
    }

    public class Builder {
        private Builder() {
        }

        public Builder id(Integer id) {
            Order.this.id = id;
            return this;
        }

        public Builder paymentMethod(PaymentMethod paymentMethod) {
            Order.this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder deliveryMethod(DeliveryMethod deliveryMethod) {
            Order.this.deliveryMethod = deliveryMethod;
            return this;
        }

        public Builder orderStatus(OrderStatus orderStatus) {
            Order.this.orderStatus = orderStatus;
            return this;
        }

        public Builder paymentState(PaymentState paymentState) {
            Order.this.paymentState = paymentState;
            return this;
        }

        public Builder address(Address address) {
            Order.this.address = address;
            return this;
        }

        public Builder addressId(Integer id) {
            Order.this.addressId = id;
            return this;
        }

        public Builder userId(Integer id) {
            Order.this.userId = id;
            return this;
        }

        public Builder amount(Double amount) {
            Order.this.amount = amount;
            return this;
        }

        public Builder orderNum(Integer orderNum) {
            Order.this.orderNum = orderNum;
            return this;
        }

        public Builder orderList(List<OrderList> list) {
            Order.this.orderList = list;
            return this;
        }

        public Builder datetime(Date datetime) {
            Order.this.datetime = datetime;
            return this;
        }

        public Order build() {
            return Order.this;
        }
    }
}
