package app.model.cart;

import app.model.enums.DeliveryMethod;
import app.model.enums.OrderStatus;
import app.model.enums.PaymentMethod;
import app.model.enums.PaymentState;

import javax.persistence.*;
import java.util.List;

public class OrderDTO {
    private Integer id;
    private PaymentMethod paymentMethod;
    private DeliveryMethod deliveryMethod;
    private OrderStatus orderStatus;
    private PaymentState paymentState;
    private Integer addressId;
    private Integer clientId;
    private double amount;
    private Integer orderNum;
    private List<OrderListDTO> list;

    public OrderDTO() {
    }

    public OrderDTO(Integer id, Integer addressId,
                    Integer clientId, double amount, Integer orderNum) {
        this.id = id;
        this.addressId = addressId;
        this.clientId = clientId;
        this.amount = amount;
        this.orderNum = orderNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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

    public List<OrderListDTO> getList() {
        return list;
    }

    public void setList(List<OrderListDTO> list) {
        this.list = list;
    }
}
