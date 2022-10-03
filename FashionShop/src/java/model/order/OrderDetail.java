/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.order;

import java.math.BigDecimal;
import model.BaseModel;
import model.product.Product;

/**
 *
 * @author LENOVO
 */
public class OrderDetail extends BaseModel{
    private int orderId;
    private int productId;
    private int quantity;
    private double price;
    private int discount;
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
    
    public BigDecimal getTotal(){
        BigDecimal total = new BigDecimal(price*quantity);
        return total;
    }
    
    public BigDecimal getRealPrice(){
        BigDecimal total = new BigDecimal(price*quantity - (price*quantity*(double)discount/100));
        return total;
    }
    
}
