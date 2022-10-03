/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.cart;

import java.math.BigDecimal;

/**
 *
 * @author LENOVO
 */
public class CartResponsive {

    int quantity;
    BigDecimal totalPrice;
    BigDecimal realPrice;

    public CartResponsive(int quantity, BigDecimal totalPrice, BigDecimal realPrice) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.realPrice = realPrice;
    }
}
