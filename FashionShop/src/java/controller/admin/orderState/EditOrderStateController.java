package controller.admin.orderState;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import controller.admin.auth.BaseAuthAdminController;
import dal.auth.UserDBContext;
import dal.order.OrderStateDBContext;
import dal.product.GroupDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.auth.User;
import model.order.OrderState;
import model.product.Group;
import utils.Validate;

/**
 *
 * @author LENOVO
 */
public class EditOrderStateController extends BaseAuthAdminController {
    
    @Override
    protected boolean isPermissionGet(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("admin");
        int numCreate = userDB.getNumberOfPermission(user.getId(), "ORDER_STATE", "EDIT");
        return numCreate >= 1;
    }

    @Override
    protected boolean isPermissionPost(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("admin");
        int numCreate = userDB.getNumberOfPermission(user.getId(), "ORDER_STATE", "EDIT");
        return numCreate >= 1;
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Validate validate = new Validate();
        try {
            String idString = validate.getField(request, "id", true);
            int id = validate.fieldInt(idString, "Error get field id");
            OrderStateDBContext orderStateDB = new OrderStateDBContext();
            GroupDBContext groupDB = new GroupDBContext();
            OrderState orderState = orderStateDB.get(id);
            request.setAttribute("orderState", orderState);
            request.getRequestDispatcher("/views/admin/order_state/editOrderState.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(EditOrderStateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Validate validate = new Validate();
        try {
            String name = validate.getFieldAjax(request, "name", true);
            String idString = validate.getFieldAjax(request, "id", true);
            int id = validate.fieldInt(idString, "Error get field id");
            OrderStateDBContext orderStateDB = new OrderStateDBContext();
            OrderState orderState = orderStateDB.get(id);
            orderState.setName(name);
            orderStateDB.update(orderState);
            String json = new Gson().toJson(orderState);
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
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
