/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.product;

import dal.product.GroupDBContext;
import dal.product.ProductDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.product.Group;
import model.product.Product;
import utils.Validate;

/**
 *
 * @author LENOVO
 */
public class ProductDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Validate validate = new Validate();
        try {
            String idString = validate.getField(request, "id", true);
            int id = validate.fieldInt(idString, "Error get field id");
            ProductDBContext productDB = new ProductDBContext();
            Product product = productDB.get(id);
            request.setAttribute("product", product);
            GroupDBContext groupDB = new GroupDBContext();
            ArrayList<Group> groups = groupDB.list();
            request.setAttribute("groups", groups);
            
            ArrayList<Product> sameProducts = productDB.findByCategory(product.getCategory().getId(), 1, 4);
            request.setAttribute("sameProducts", sameProducts);
            
            request.getRequestDispatcher("/views/product/productDetail.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(controller.admin.product.ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
