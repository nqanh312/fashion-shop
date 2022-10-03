/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.cart;

import java.math.BigDecimal;
import model.BaseModel;
import model.auth.User;
import model.product.Product;

/**
 *
 * @author LENOVO
 */
public class Cart extends BaseModel{
    private int userId;
    private User user;
    private int productId;
    private Product product;
    private int quantity;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getTotal(){
        BigDecimal total = new BigDecimal(product.getPrice()*quantity);
        return total;
    }
    
    public BigDecimal getRealPrice(){
        BigDecimal total = new BigDecimal(product.getPrice()*quantity - (product.getPrice()*quantity*(double)product.getDiscount()/100));
        return total;
    }
    
}
