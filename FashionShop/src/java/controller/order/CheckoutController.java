/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.order;

import com.google.gson.Gson;
import dal.cart.CartDBContext;
import dal.order.CustomerDBContext;
import dal.order.OrderDBContext;
import dal.order.OrderDetailDBContext;
import dal.order.OrderStateDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.auth.User;
import model.cart.Cart;
import model.order.Customer;
import model.order.Order;
import model.order.OrderDetail;
import model.order.OrderState;
import utils.Validate;

/**
 *
 * @author LENOVO
 */
public class CheckoutController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            
            Validate validate = new Validate();
            String first_name = validate.getFieldAjax(request, "first_name", true);
            String last_name = validate.getFieldAjax(request, "last_name", true);
            String phoneString = validate.getFieldAjax(request, "phone", true);
            String emailString = validate.getFieldAjax(request, "email", true);
            String address = validate.getFieldAjax(request, "address", true);

            String phone = validate.fieldString(phoneString, "(\\+84|0)([3|5|7|8|9])+([0-9]{8})", "Phone is wrong please enter new phone!");
            String email = validate.fieldString(emailString, "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", "Email wrong! please enter new email");

            Timestamp created_at = new Timestamp(System.currentTimeMillis());
            Timestamp updated_at = new Timestamp(System.currentTimeMillis());

            Customer customer = new Customer();
            customer.setFirst_name(first_name);
            customer.setLast_name(last_name);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setAddress(address);
            CustomerDBContext customerDB = new CustomerDBContext();
            customer = customerDB.insert(customer);

            OrderStateDBContext orderStateDB = new OrderStateDBContext();
            OrderState orderState = orderStateDB.get(1);

            OrderDBContext orderDB = new OrderDBContext();
            Order order = new Order();
            order.setCustomerId(customer.getId());
            order.setStateId(orderState.getId());
            order.setCreated_at(created_at);
            order.setUpdated_at(updated_at);
            
            User user = (User) session.getAttribute("user");
            if(user!=null){
                order.setUserId(user.getId());
                order = orderDB.insert(order);
            }else{
                order= orderDB.insertNotUser(order);
            }
           
            
            CartDBContext cartDB = new CartDBContext();
            ArrayList<Cart> carts = new ArrayList<Cart>();
            if (user != null) {
                carts = cartDB.findByUser(user.getId());
                if (carts == null) {
                    carts = new ArrayList<Cart>();
                }
            } else {
                carts = (ArrayList<Cart>) session.getAttribute("carts");
                if (carts == null) {
                    carts = new ArrayList<Cart>();
                }
            }
                        
            if(carts.size()>0){
                for (Cart cart : carts) {
                    OrderDetailDBContext orderDetailDB = new OrderDetailDBContext();
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(order.getId());
                    orderDetail.setProductId(cart.getProduct().getId());
                    orderDetail.setPrice(cart.getProduct().getPrice());
                    if(cart.getProduct().isIsSale()){
                        orderDetail.setDiscount(cart.getProduct().getDiscount());
                    }else{
                        orderDetail.setDiscount(0);
                    }
                    orderDetail.setQuantity(cart.getQuantity());
                    orderDetail.setCreated_at(created_at);
                    orderDetail.setUpdated_at(updated_at);
                    orderDetailDB.insert(orderDetail);
                    if(user!=null){
                        cartDB.delete(cart.getId());
                    }
                }
            }
            
            OrderDBContext new_orderDB = new OrderDBContext();
            Order new_order = new_orderDB.get(order.getId());
                       
            session.removeAttribute("quantity");
            session.removeAttribute("carts");
            
            ArrayList<Order> orders = (ArrayList<Order>)session.getAttribute("orders");
            if(orders==null){
                orders = new ArrayList<>();
            }
            orders.add(new_order);
            if(user!=null){
                orders = new_orderDB.findByUser(user.getId());
            }
            
            session.setAttribute("orders", orders);

            String json = new Gson().toJson("Order success!");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception ex) {
            String json = new Gson().toJson(new Error(ex.getMessage()));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
