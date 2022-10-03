package controller.admin.group;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import controller.admin.auth.BaseAuthAdminController;
import dal.auth.UserDBContext;
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
import model.product.Group;
import utils.Validate;

/**
 *
 * @author LENOVO
 */
public class EditGroupController extends BaseAuthAdminController {
    
    @Override
    protected boolean isPermissionGet(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("admin");
        int numCreate = userDB.getNumberOfPermission(user.getId(), "GROUP", "EDIT");
        return numCreate >= 1;
    }

    @Override
    protected boolean isPermissionPost(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("admin");
        int numCreate = userDB.getNumberOfPermission(user.getId(), "GROUP", "EDIT");
        return numCreate >= 1;
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Validate validate = new Validate();
        try {
            String idString = validate.getField(request, "id", true);
            int id = validate.fieldInt(idString, "Error get field id");
            GroupDBContext groupDB = new GroupDBContext();
            Group group = groupDB.get(id);
            request.setAttribute("group", group);
            request.getRequestDispatcher("/views/admin/group/editGroup.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(EditGroupController.class.getName()).log(Level.SEVERE, null, ex);
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
            GroupDBContext groupDB = new GroupDBContext();
            Group group = new Group();
            group.setId(id);
            group.setName(name);
            groupDB.update(group);
            String json = new Gson().toJson(group);
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
