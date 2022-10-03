/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import model.BaseModel;

/**
 *
 * @author LENOVO
 */
public class Order extends BaseModel {

    private int userId;
    private int customerId;
    private int stateId;
    private ArrayList<OrderDetail> orderDetails;
    private OrderState state;
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public BigDecimal total() {
        BigDecimal total = new BigDecimal(0);
        for (OrderDetail orderDetail : orderDetails) {
            total = total.add(orderDetail.getRealPrice());
        }
        return total;
    }

}
