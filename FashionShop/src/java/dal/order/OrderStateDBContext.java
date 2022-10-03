/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.order;

import dal.DBContext;
import dal.product.CategoryDBContext;
import dal.product.GroupDBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.order.OrderState;
import model.product.Category;
import model.product.Group;

/**
 *
 * @author LENOVO
 */
public class OrderStateDBContext extends DBContext<OrderState> {

    @Override
    public ArrayList<OrderState> list() {
        ArrayList<OrderState> orderStates = new ArrayList<>();
        String sql = "SELECT id, name FROM [order_state]";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                OrderState orderState = new OrderState();
                orderState.setId(result.getInt("id"));
                orderState.setName(result.getString("name"));
                orderStates.add(orderState);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderStateDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderStates;
    }

    @Override
    public OrderState get(int id) {
        String sql = "SELECT id, name FROM [order_state]\n"
                + " WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                OrderState orderState = new OrderState();
                orderState.setId(result.getInt("id"));
                orderState.setName(result.getString("name"));
                return orderState;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderStateDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public OrderState insert(OrderState model) {
        String sql = "INSERT INTO [order_state]\n"
                + " ([name])\n"
                + " VALUES(?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, model.getName());
            statement.executeUpdate();
            ArrayList<OrderState> orderStates = list();
            return orderStates.get(orderStates.size()-1);
        } catch (SQLException ex) {
            Logger.getLogger(OrderStateDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderStateDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderStateDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(OrderState model) {
        String sql = "UPDATE [order_state]\n"
                + " SET name = ?\n"
                + " WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, model.getName());
            statement.setInt(2, model.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderStateDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderStateDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderStateDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM [order_state]\n"
                    + "WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderStateDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
