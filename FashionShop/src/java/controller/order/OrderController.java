/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.order;

import dal.order.OrderDBContext;
import dal.product.GroupDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.auth.User;
import model.order.Order;
import model.product.Group;

/**
 *
 * @author LENOVO
 */
public class OrderController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ArrayList<Order> orders = new ArrayList<>();
        OrderDBContext orderDB = new OrderDBContext();
        if (user != null) {
            orders = orderDB.findByUser(user.getId());
        } else {
            orders = (ArrayList<Order>) session.getAttribute("orders");
            if (orders != null) {
                for (Order order : orders) {
                    order = orderDB.get(order.getId());
                }
                orders.sort((o1, o2) -> {
                    return -(o1.getId()-o2.getId()) ; 
                });
                session.setAttribute("orders", orders);
            }else{
                orders = new ArrayList<>();
            }
        }
        request.setAttribute("orders", orders);

        GroupDBContext groupDB = new GroupDBContext();
        ArrayList<Group> groups = groupDB.list();
        request.setAttribute("groups", groups);
        request.getRequestDispatcher("/views/order/orders.jsp").forward(request, response);
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
