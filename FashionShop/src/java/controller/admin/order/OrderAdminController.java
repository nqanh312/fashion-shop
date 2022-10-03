/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin.order;

import com.google.gson.Gson;
import controller.admin.auth.BaseAuthAdminController;
import controller.admin.group.EditGroupController;
import dal.auth.UserDBContext;
import dal.order.OrderDBContext;
import dal.order.OrderStateDBContext;
import dal.product.GroupDBContext;
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
import model.common.Pagination;
import model.order.Order;
import model.order.OrderState;
import model.product.Group;
import utils.Validate;

/**
 *
 * @author LENOVO
 */
public class OrderAdminController extends BaseAuthAdminController {

    @Override
    protected boolean isPermissionGet(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("admin");
        int numRead = userDB.getNumberOfPermission(user.getId(), "ORDER", "READ");
        return numRead >= 1;
    }

    @Override
    protected boolean isPermissionPost(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("admin");
        int numRead = userDB.getNumberOfPermission(user.getId(), "ORDER", "READ");
        return numRead >= 1;
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Validate validate = new Validate();
        try {
            int pageSize = 12;
            String page = validate.getField(request, "page", false);
            if (page == null || page.trim().length() == 0) {
                page = "1";
            }
            int pageIndex = 0;
            try {
                pageIndex = validate.fieldInt(page, "Something error!");
                if (pageIndex <= 0) {
                    pageIndex = 1;
                }
            } catch (Exception e) {
                pageIndex = 1;
            }
            HttpSession session = request.getSession();
            ArrayList<Order> orders = new ArrayList<>();
            OrderDBContext orderDB = new OrderDBContext();
            orders = orderDB.getOrders(pageIndex, pageSize);
            Pagination pagination = new Pagination(pageIndex, pageSize, orderDB.getSize());
            request.setAttribute("orders", orders);
            OrderStateDBContext orderStateDB = new OrderStateDBContext();
            ArrayList<OrderState> orderStates = orderStateDB.list();
            request.setAttribute("orderStates", orderStates);
            request.setAttribute("pagination", pagination);
            request.getRequestDispatcher("/views/admin/order/orders.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(OrderAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Validate validate = new Validate();
        try {
            String orderIdString = validate.getField(request, "orderId", true);
            String orderStateIdtring = validate.getField(request, "orderState", true);
            int orderId = validate.fieldInt(orderIdString, "Error get field id");
            int orderStateId = validate.fieldInt(orderStateIdtring, "Error get field id");
            OrderDBContext orderDB = new OrderDBContext();
            Order order = orderDB.get(orderId);
            order.setStateId(orderStateId);
            Timestamp updated_at = new Timestamp(System.currentTimeMillis());
            order.setUpdated_at(updated_at);
            orderDB.update(order);
            String json = new Gson().toJson("Update success");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception ex) {
            Logger.getLogger(OrderAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
