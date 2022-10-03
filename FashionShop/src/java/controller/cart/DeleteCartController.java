/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.cart;

import dal.cart.CartDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.auth.User;
import model.cart.Cart;
import utils.Validate;

/**
 *
 * @author LENOVO
 */
public class DeleteCartController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idString = validate.getField(request, "id", true);
            int id = validate.fieldInt(idString, "error delete cart");
            HttpSession session = request.getSession();
            CartDBContext cartDB = new CartDBContext();
            ArrayList<Cart> carts = new ArrayList<Cart>();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                carts = cartDB.findByUser(user.getId());
                cartDB.deleteByProduct(id, user.getId());
            } else {
                carts = (ArrayList<Cart>) session.getAttribute("carts");
                if (carts == null) {
                    carts = new ArrayList<Cart>();
                }
            }
            carts.removeIf(cart -> cart.getProduct().getId() == id);
            session.setAttribute("carts", carts);
            int quantity_cart = 0;

            for (Cart item : carts) {
                quantity_cart += item.getQuantity();
            }
            System.out.println(quantity_cart);
            session.setAttribute("quantity", quantity_cart);
            response.sendRedirect(request.getHeader("referer"));
        } catch (Exception ex) {
            Logger.getLogger(DeleteCartController.class.getName()).log(Level.SEVERE, null, ex);
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
