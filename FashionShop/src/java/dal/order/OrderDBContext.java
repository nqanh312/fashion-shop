/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.order;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.order.Customer;
import model.order.Order;
import model.order.OrderDetail;
import model.order.OrderState;

/**
 *
 * @author LENOVO
 */
public class OrderDBContext extends DBContext<Order> {

    public ArrayList<Order> findByUser(int userId) {
        ArrayList<Order> orders = new ArrayList<>();
        OrderDetailDBContext orderDetailDB = new OrderDetailDBContext();
        CustomerDBContext customerDB = new CustomerDBContext();
        String sql = "SELECT [order].[id]\n"
                + "      ,[order].[userId]\n"
                + "      ,[order].[customerId]\n"
                + "      ,[order].[stateId]\n"
                + "      ,[order].[created_at]\n"
                + "      ,[order].[updated_at]\n"
                + "	 ,[customer].[first_name]\n"
                + "      ,[customer].[last_name]\n"
                + "      ,[customer].[email]\n"
                + "      ,[customer].[phone]\n"
                + "      ,[customer].[address]\n"
                + "	  ,[order_state].[name] as 'order_state_name'\n"
                + "  FROM [order]\n"
                + "  INNER JOIN [customer] ON [customer].[id] = [order].[customerId]\n"
                + "  INNER JOIN [order_state] ON [order_state].[id] = [order].[stateId]\n"
                + " WHERE [order].[userId] = ? \n"
                + " ORDER BY id DESC";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("id"));
                order.setUserId(result.getInt("userId"));
                order.setCustomerId(result.getInt("customerId"));
                order.setStateId(result.getInt("stateId"));
                order.setCreated_at(result.getTimestamp("created_at"));
                order.setUpdated_at(result.getTimestamp("updated_at"));

                ArrayList<OrderDetail> orderDetails = orderDetailDB.findByOrderId(order.getId());
                order.setOrderDetails(orderDetails);

                OrderState orderState = new OrderState();
                orderState.setId(result.getInt("stateId"));
                orderState.setName(result.getString("order_state_name"));
                order.setState(orderState);

                Customer customer = new Customer();
                customer.setId(result.getInt("customerId"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setAddress(result.getString("address"));

                order.setCustomer(customer);
                orders.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    public ArrayList<Order> getOrders(int pageIndex, int pageSize) {
        ArrayList<Order> orders = new ArrayList<>();
        OrderDetailDBContext orderDetailDB = new OrderDetailDBContext();
        CustomerDBContext customerDB = new CustomerDBContext();
        String sql = "SELECT * FROM (SELECT [order].[id]\n"
                + "      ,[order].[userId]\n"
                + "      ,[order].[customerId]\n"
                + "      ,[order].[stateId]\n"
                + "      ,[order].[created_at]\n"
                + "      ,[order].[updated_at]\n"
                + "	 ,[customer].[first_name]\n"
                + "      ,[customer].[last_name]\n"
                + "      ,[customer].[email]\n"
                + "      ,[customer].[phone]\n"
                + "      ,[customer].[address]\n"
                + "	 ,[order_state].[name] as 'order_state_name'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [order].[id] DESC) as row_index\n"
                + "  FROM [order] "
                + "  INNER JOIN [customer] ON [customer].[id] = [order].[customerId]\n"
                + "  INNER JOIN [order_state] ON [order_state].[id] = [order].[stateId]) [order]"
                + " WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, pageIndex);
            statement.setInt(2, pageSize);
            statement.setInt(3, pageIndex);
            statement.setInt(4, pageSize);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("id"));
                order.setUserId(result.getInt("userId"));
                order.setCustomerId(result.getInt("customerId"));
                order.setStateId(result.getInt("stateId"));
                order.setCreated_at(result.getTimestamp("created_at"));
                order.setUpdated_at(result.getTimestamp("updated_at"));

                ArrayList<OrderDetail> orderDetails = orderDetailDB.findByOrderId(order.getId());
                order.setOrderDetails(orderDetails);

                OrderState orderState = new OrderState();
                orderState.setId(result.getInt("stateId"));
                orderState.setName(result.getString("order_state_name"));
                order.setState(orderState);

                Customer customer = new Customer();
                customer.setId(result.getInt("customerId"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setAddress(result.getString("address"));

                order.setCustomer(customer);
                orders.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    @Override
    public ArrayList<Order> list() {
        ArrayList<Order> orders = new ArrayList<>();
        OrderDetailDBContext orderDetailDB = new OrderDetailDBContext();
        String sql = "SELECT [order].[id]\n"
                + "      ,[order].[userId]\n"
                + "      ,[order].[customerId]\n"
                + "      ,[order].[stateId]\n"
                + "      ,[order].[created_at]\n"
                + "      ,[order].[updated_at]\n"
                + "	  ,[customer].[first_name]\n"
                + "      ,[customer].[last_name]\n"
                + "      ,[customer].[email]\n"
                + "      ,[customer].[phone]\n"
                + "      ,[customer].[address]\n"
                + "	 ,[order_state].[name] as 'order_state_name'\n"
                + "  FROM [order]\n"
                + "  INNER JOIN [customer] ON [customer].[id] = [order].[customerId]\n"
                + "  INNER JOIN [order_state] ON [order_state].[id] = [order].[stateId]\n"
                + " ORDER BY [order].[id] DESC";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("id"));
                order.setUserId(result.getInt("userId"));
                order.setCustomerId(result.getInt("customerId"));
                order.setStateId(result.getInt("stateId"));
                order.setCreated_at(result.getTimestamp("created_at"));
                order.setUpdated_at(result.getTimestamp("updated_at"));

                ArrayList<OrderDetail> orderDetails = orderDetailDB.findByOrderId(order.getId());
                order.setOrderDetails(orderDetails);

                OrderState orderState = new OrderState();
                orderState.setId(result.getInt("stateId"));
                orderState.setName(result.getString("order_state_name"));
                order.setState(orderState);

                Customer customer = new Customer();
                customer.setId(result.getInt("customerId"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setAddress(result.getString("address"));

                order.setCustomer(customer);
                orders.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    @Override
    public Order get(int id) {
        OrderDetailDBContext orderDetailDB = new OrderDetailDBContext();
        CustomerDBContext customerDB = new CustomerDBContext();
        String sql = "SELECT [order].[id]\n"
                + "      ,[order].[userId]\n"
                + "      ,[order].[customerId]\n"
                + "      ,[order].[stateId]\n"
                + "      ,[order].[created_at]\n"
                + "      ,[order].[updated_at]\n"
                + "	 ,[customer].[first_name]\n"
                + "      ,[customer].[last_name]\n"
                + "      ,[customer].[email]\n"
                + "      ,[customer].[phone]\n"
                + "      ,[customer].[address]\n"
                + "	 ,[order_state].[name] as 'order_state_name'\n"
                + "  FROM [order]\n"
                + "  INNER JOIN [customer] ON [customer].[id] = [order].[customerId]\n"
                + "  INNER JOIN [order_state] ON [order_state].[id] = [order].[stateId]\n"
                + " WHERE [order].[id] = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("id"));
                order.setUserId(result.getInt("userId"));
                order.setCustomerId(result.getInt("customerId"));
                order.setStateId(result.getInt("stateId"));
                order.setCreated_at(result.getTimestamp("created_at"));
                order.setUpdated_at(result.getTimestamp("updated_at"));

                ArrayList<OrderDetail> orderDetails = orderDetailDB.findByOrderId(order.getId());
                order.setOrderDetails(orderDetails);

                OrderState orderState = new OrderState();
                orderState.setId(result.getInt("stateId"));
                orderState.setName(result.getString("order_state_name"));
                order.setState(orderState);

                Customer customer = new Customer();
                customer.setId(result.getInt("customerId"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setAddress(result.getString("address"));

                order.setCustomer(customer);
                return order;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Order getLastHaveUser(int userId) {
        OrderDetailDBContext orderDetailDB = new OrderDetailDBContext();
        String sql = "SELECT TOP 1 [order].[id]\n"
                + "      ,[order].[userId]\n"
                + "      ,[order].[customerId]\n"
                + "      ,[order].[stateId]\n"
                + "      ,[order].[created_at]\n"
                + "      ,[order].[updated_at]\n"
                + "	 ,[customer].[first_name]\n"
                + "      ,[customer].[last_name]\n"
                + "      ,[customer].[email]\n"
                + "      ,[customer].[phone]\n"
                + "      ,[customer].[address]\n"
                + "	 ,[order_state].[name] as 'order_state_name'\n"
                + "  FROM [order]\n"
                + "  INNER JOIN [customer] ON [customer].[id] = [order].[customerId]\n"
                + "  INNER JOIN [order_state] ON [order_state].[id] = [order].[stateId]\n"
                + "  WHERE [order].[userId] = ? \n"
                + "  ORDER BY [order].[id] DESC";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("id"));
                order.setUserId(result.getInt("userId"));
                order.setCustomerId(result.getInt("customerId"));
                order.setStateId(result.getInt("stateId"));
                order.setCreated_at(result.getTimestamp("created_at"));
                order.setUpdated_at(result.getTimestamp("updated_at"));

                ArrayList<OrderDetail> orderDetails = orderDetailDB.findByOrderId(order.getId());
                order.setOrderDetails(orderDetails);

                OrderState orderState = new OrderState();
                orderState.setId(result.getInt("stateId"));
                orderState.setName(result.getString("order_state_name"));
                order.setState(orderState);

                Customer customer = new Customer();
                customer.setId(result.getInt("customerId"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setAddress(result.getString("address"));

                order.setCustomer(customer);
                return order;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public Order getLastByCreated(Timestamp created_at) {
        OrderDetailDBContext orderDetailDB = new OrderDetailDBContext();
        String sql = "SELECT TOP 1 [order].[id]\n"
                + "      ,[order].[userId]\n"
                + "      ,[order].[customerId]\n"
                + "      ,[order].[stateId]\n"
                + "      ,[order].[created_at]\n"
                + "      ,[order].[updated_at]\n"
                + "	  ,[customer].[first_name]\n"
                + "      ,[customer].[last_name]\n"
                + "      ,[customer].[email]\n"
                + "      ,[customer].[phone]\n"
                + "      ,[customer].[address]\n"
                + "	  ,[order_state].[name] as 'order_state_name'\n"
                + "  FROM [order]\n"
                + "  INNER JOIN [customer] ON [customer].[id] = [order].[customerId]\n"
                + "  INNER JOIN [order_state] ON [order_state].[id] = [order].[stateId]\n"
                + "  WHERE [order].[created_at] = ? \n"
                + "  ORDER BY [order].[id] DESC";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            String created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(created_at);
            statement.setString(1, created);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("id"));
                order.setUserId(result.getInt("userId"));
                order.setCustomerId(result.getInt("customerId"));
                order.setStateId(result.getInt("stateId"));
                order.setCreated_at(result.getTimestamp("created_at"));
                order.setUpdated_at(result.getTimestamp("updated_at"));

                ArrayList<OrderDetail> orderDetails = orderDetailDB.findByOrderId(order.getId());
                order.setOrderDetails(orderDetails);

                OrderState orderState = new OrderState();
                orderState.setId(result.getInt("stateId"));
                orderState.setName(result.getString("order_state_name"));
                order.setState(orderState);

                Customer customer = new Customer();
                customer.setId(result.getInt("customerId"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setAddress(result.getString("address"));

                order.setCustomer(customer);
                return order;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Order insert(Order model) {
        String sql = "INSERT INTO [order]\n"
                + "           ([userId]\n"
                + "           ,[customerId]\n"
                + "           ,[stateId]\n"
                + "           ,[created_at]\n"
                + "           ,[updated_at])\n"
                + "     VALUES(?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getUserId());
            statement.setInt(2, model.getCustomerId());
            statement.setInt(3, model.getStateId());
            statement.setTimestamp(4, model.getCreated_at());
            statement.setTimestamp(5, model.getUpdated_at());
            statement.executeUpdate();
            return getLastHaveUser(model.getUserId());
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public Order insertNotUser(Order model) {
        String sql = "INSERT INTO [order]\n"
                + "           ([customerId]\n"
                + "           ,[stateId]\n"
                + "           ,[created_at]\n"
                + "           ,[updated_at])\n"
                + "     VALUES(?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getCustomerId());
            statement.setInt(2, model.getStateId());
            statement.setTimestamp(3, model.getCreated_at());
            statement.setTimestamp(4, model.getUpdated_at());
            statement.executeUpdate();
            return getLastByCreated(model.getCreated_at());
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Order model) {
        String sql = "UPDATE [order]\n"
                + "        SET [stateId]=?\n"
                + "           ,[updated_at]=?\n"
                + "     WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getStateId());
            statement.setTimestamp(2, model.getUpdated_at());
            statement.setInt(3, model.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM [order]\n"
                    + "WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public int getSize() {
        String sql = "SELECT COUNT([order].[id]) as 'size' FROM [order]";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int size = result.getInt("size");
                return size;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}
