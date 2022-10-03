/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin.product;

import controller.admin.auth.BaseAuthAdminController;
import controller.admin.group.DeleteGroupController;
import dal.auth.UserDBContext;
import dal.product.GroupDBContext;
import dal.product.ImageDBContext;
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
import model.product.Image;
import utils.FileManage;
import utils.Validate;

/**
 *
 * @author LENOVO
 */
public class DeleteProductImageController extends BaseAuthAdminController {
    
     @Override
    protected boolean isPermissionGet(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("admin");
        int numEdit = userDB.getNumberOfPermission(user.getId(), "PRODUCT", "EDIT");
        return numEdit >= 1;
    }

    @Override
    protected boolean isPermissionPost(HttpServletRequest request) {
         UserDBContext userDB = new UserDBContext();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("admin");
        int numEdit = userDB.getNumberOfPermission(user.getId(), "PRODUCT", "EDIT");
        return numEdit >= 1;
    }

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Validate validate = new Validate();
        try {
            String idString = validate.getFieldAjax(request, "id", true);
            int id = validate.fieldInt(idString, "Error get field id");
            ImageDBContext imageDB = new ImageDBContext();
            Image image =  imageDB.get(id);
            FileManage fileManage = new FileManage();
            String folder = request.getServletContext().getRealPath("/assets/images/product");
            try {
                fileManage.delete(image.getImage(), folder);
            } catch (Exception e) {
            }
            imageDB.delete(id);
            response.sendRedirect(request.getHeader("referer"));
        } catch (Exception ex) {
            Logger.getLogger(DeleteGroupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
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
