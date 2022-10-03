/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.order;

import dal.DBContext;
import dal.product.ProductDBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.order.Customer;

/**
 *
 * @author LENOVO
 */
public class CustomerDBContext extends DBContext<Customer> {

    @Override
    public ArrayList<Customer> list() {
        ArrayList<Customer> customers = new ArrayList<>();
        ProductDBContext ProductDB = new ProductDBContext();
        String sql = "SELECT [id]\n"
                + "      ,[first_name]\n"
                + "      ,[last_name]\n"
                + "      ,[email]\n"
                + "      ,[phone]\n"
                + "      ,[address]\n"
                + "  FROM [customer]";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Customer customer = new Customer();
                customer.setId(result.getInt("id"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setAddress(result.getString("address"));
                customers.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customers;
    }

    @Override
    public Customer get(int id) {
        ProductDBContext ProductDB = new ProductDBContext();
        String sql = "SELECT [id]\n"
                + "      ,[first_name]\n"
                + "      ,[last_name]\n"
                + "      ,[email]\n"
                + "      ,[phone]\n"
                + "      ,[address]\n"
                + "  FROM [customer]";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Customer customer = new Customer();
                customer.setId(result.getInt("id"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setAddress(result.getString("address"));
                return customer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Customer getLast() {
        ProductDBContext ProductDB = new ProductDBContext();
        String sql = "SELECT [id]\n"
                + "      ,[first_name]\n"
                + "      ,[last_name]\n"
                + "      ,[email]\n"
                + "      ,[phone]\n"
                + "      ,[address]\n"
                + "  FROM [customer] ORDER BY [id] DESC";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Customer customer = new Customer();
                customer.setId(result.getInt("id"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setAddress(result.getString("address"));
                return customer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Customer insert(Customer model) {
        String sql = "INSERT INTO [customer]\n"
                + "           ([first_name]\n"
                + "           ,[last_name]\n"
                + "           ,[email]\n"
                + "           ,[phone]\n"
                + "           ,[address])\n"
                + "     VALUES(?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, model.getFirst_name());
            statement.setString(2, model.getLast_name());
            statement.setString(3, model.getEmail());
            statement.setString(4, model.getPhone());
            statement.setString(5, model.getAddress());
            statement.executeUpdate();
            return getLast();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Customer model) {
        String sql = "UPDATE [customer]\n"
                + "       SET [first_name]=?\n"
                + "           ,[last_name]=?\n"
                + "           ,[email]=?\n"
                + "           ,[phone]=?\n"
                + "           ,[address]=?\n"
                + "     WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, model.getFirst_name());
            statement.setString(2, model.getLast_name());
            statement.setString(3, model.getEmail());
            statement.setString(4, model.getPhone());
            statement.setString(5, model.getAddress());
            statement.setInt(6, model.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
         try {
            String sql = "DELETE FROM [customer]\n"
                    + "WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
