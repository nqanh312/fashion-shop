/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.user;

import com.google.gson.Gson;
import controller.auth.BaseAuthController;
import dal.auth.PermissionDBContext;
import dal.auth.UserDBContext;
import dal.product.GroupDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.auth.Permission;
import model.auth.User;
import model.product.Group;
import utils.Validate;

/**
 *
 * @author LENOVO
 */
public class ProfileController extends BaseAuthController {

    private final Validate validate = new Validate();
     
    @Override
    protected boolean isPermissionGet(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int numCreate = userDB.getNumberOfPermission(user.getId(), "USER", "READ");
        return numCreate >= 1;
    }

    @Override
    protected boolean isPermissionPost(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int numCreate = userDB.getNumberOfPermission(user.getId(), "USER", "READ");
        return numCreate >= 1;
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GroupDBContext groupDB = new GroupDBContext();
        ArrayList<Group> groups = groupDB.list();
        request.setAttribute("groups", groups);
        request.getRequestDispatcher("/views/user/profile.jsp").forward(request, response);
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try {
            String idString = validate.getFieldAjax(request, "id", true);
            String email = validate.getFieldAjax(request, "email", true);
            String first_name = validate.getFieldAjax(request, "first_name", true);
            String last_name = validate.getFieldAjax(request, "last_name", true);
            String phone = validate.getFieldAjax(request, "phone", true);
            String gender = validate.getFieldAjax(request, "gender", true);
            String birthday = validate.getFieldAjax(request, "birthday", true);

            UserDBContext db = new UserDBContext();
            try {
                int field_id = validate.fieldInt(idString, "Error find id user");
                User user = db.get(field_id);

                String field_email = validate.fieldString(email, "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", "Email wrong! please enter new email");
                if (!user.getEmail().equalsIgnoreCase(field_email) && db.findOne("email", field_email) != null) {
                    String json = new Gson().toJson(new Error("Email has exist! Please try new email!"));
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    return;
                }
                String field_phone = validate.fieldString(phone, "(\\+84|0)([3|5|7|8|9])+([0-9]{8})", "Phone is wrong please enter new phone!");
                Boolean field_gender = gender.equals("male") ? true : false;
                Date field_birthday = validate.fieldDate(birthday, "Birthday is wrong! Please try again");
                user.setEmail(field_email);
                user.setPhone(field_phone);
                user.setFirst_name(first_name);
                user.setLast_name(last_name);
                user.setGender(field_gender);
                user.setBirthday(field_birthday);
                Timestamp updated_at = new Timestamp(System.currentTimeMillis());
                user.setUpdated_at(updated_at);
                db.update(user);
                request.getSession().setAttribute("user", user);
                String json = new Gson().toJson(user);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (Exception e) {
                String json = new Gson().toJson(new Error(e.getMessage()));
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            }
        } catch (Exception e) {
            String json = new Gson().toJson(new Error(e.getMessage()));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
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
