/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin.category;

import controller.admin.auth.BaseAuthAdminController;
import dal.auth.UserDBContext;
import dal.product.CategoryDBContext;
import dal.product.GroupDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.auth.User;
import model.product.Category;
import model.product.Group;
import utils.Validate;

/**
 *
 * @author LENOVO
 */
public class CategoryController extends BaseAuthAdminController {

   @Override
    protected boolean isPermissionGet(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("admin");
        int numRead = userDB.getNumberOfPermission(user.getId(), "CATEGORY", "DELETE");
        return numRead >= 1;
    }

    @Override
    protected boolean isPermissionPost(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("admin");
        int numEdit = userDB.getNumberOfPermission(user.getId(), "CATEGORY", "EDIT");
        return numEdit >= 1;
    }
   
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try {
           Validate validate = new Validate();
           String search = validate.getField(request, "q", false);
           String groupIdString = validate.getField(request, "group", false);
           if(search==null) search = "";
           int groupId = -1;
           if(groupIdString != null) {
               groupId = validate.fieldInt(groupIdString, "Error get field groupid");
           }
           CategoryDBContext categoryDB = new CategoryDBContext();
           ArrayList<Category> listCategory = categoryDB.findMany(search, groupId);
           GroupDBContext groupDB = new GroupDBContext();
           ArrayList<Group> groups = groupDB.list();
            
           request.setAttribute("groups", groups);
           request.setAttribute("listCategory", listCategory);
           request.getRequestDispatcher("/views/admin/category/category.jsp").forward(request, response);
       } catch (Exception ex) {
           Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
