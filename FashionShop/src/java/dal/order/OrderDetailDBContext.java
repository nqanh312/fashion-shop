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
import model.order.OrderDetail;
import model.product.Category;
import model.product.Group;
import model.product.Product;
import model.product.State;

/**
 *
 * @author LENOVO
 */
public class OrderDetailDBContext extends DBContext<OrderDetail> {

    public ArrayList<OrderDetail> findByOrderId(int orderId) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT [order_detail].[id]\n"
                + "      ,[order_detail].[quantity]\n"
                + "      ,[order_detail].[price]\n"
                + "      ,[order_detail].[discount]\n"
                + "      ,[order_detail].[created_at]\n"
                + "      ,[order_detail].[updated_at]\n"
                + "      ,[order_detail].[productId]\n"
                + "      ,[order_detail].[orderId]\n"
                + "	  ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price] as 'product_price'\n"
                + "      ,[product].[quantity] as 'product_quantity'\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount] as 'product_discount'\n"
                + "      ,[product].[created_at] as 'product_created_at'\n"
                + "      ,[product].[updated_at] as 'product_updated_at'\n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "      ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "      ,[state].[name] as 'stateName'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [product].[id] DESC) as row_index\n"
                + "  FROM [order_detail]\n"
                + "  INNER JOIN [product] ON [product].[id] = [order_detail].[productId]\n"
                + "  INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + "  INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + "  INNER JOIN [state] ON [state].[id] = [product].[state]\n"
                + " WHERE [order_detail].[orderId] = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, orderId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(result.getInt("id"));
                orderDetail.setOrderId(result.getInt("orderId"));
                orderDetail.setProductId(result.getInt("productId"));
                orderDetail.setQuantity(result.getInt("quantity"));
                orderDetail.setDiscount(result.getInt("discount"));
                orderDetail.setPrice(result.getDouble("price"));
                orderDetail.setCreated_at(result.getTimestamp("created_at"));
                orderDetail.setUpdated_at(result.getTimestamp("updated_at"));

                Product product = new Product();
                product.setId(result.getInt("productId"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("product_price"));
                product.setQuantity(result.getInt("product_quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("product_discount"));
                product.setCreated_at(result.getTimestamp("product_created_at"));
                product.setUpdated_at(result.getTimestamp("product_updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));

                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));

                Category category = new Category();
                category.setId(product.getId());
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);

                State state = new State();
                state.setId(result.getInt("state"));
                state.setName(result.getString("stateName"));
                product.setState(state);

                orderDetail.setProduct(product);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderDetails;
    }

    @Override
    public ArrayList<OrderDetail> list() {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT [order_detail].[id]\n"
                + "      ,[order_detail].[quantity]\n"
                + "      ,[order_detail].[price]\n"
                + "      ,[order_detail].[discount]\n"
                + "      ,[order_detail].[created_at]\n"
                + "      ,[order_detail].[updated_at]\n"
                + "      ,[order_detail].[productId]\n"
                + "      ,[order_detail].[orderId]\n"
                + "	  ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price] as 'product_price'\n"
                + "      ,[product].[quantity] as 'product_quantity'\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount] as 'product_discount'\n"
                + "      ,[product].[created_at] as 'product_created_at'\n"
                + "      ,[product].[updated_at] as 'product_updated_at'\n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "      ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "      ,[state].[name] as 'stateName'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [product].[id] DESC) as row_index\n"
                + "  FROM [order_detail]\n"
                + "  INNER JOIN [product] ON [product].[id] = [order_detail].[productId]\n"
                + "  INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + "  INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + "  INNER JOIN [state] ON [state].[id] = [product].[state]";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(result.getInt("id"));
                orderDetail.setOrderId(result.getInt("orderId"));
                orderDetail.setProductId(result.getInt("productId"));
                orderDetail.setQuantity(result.getInt("quantity"));
                orderDetail.setDiscount(result.getInt("discount"));
                orderDetail.setPrice(result.getDouble("price"));
                orderDetail.setCreated_at(result.getTimestamp("created_at"));
                orderDetail.setUpdated_at(result.getTimestamp("updated_at"));

                Product product = new Product();
                product.setId(result.getInt("productId"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("product_price"));
                product.setQuantity(result.getInt("product_quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("product_discount"));
                product.setCreated_at(result.getTimestamp("product_created_at"));
                product.setUpdated_at(result.getTimestamp("product_updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));

                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));

                Category category = new Category();
                category.setId(product.getId());
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);

                State state = new State();
                state.setId(result.getInt("state"));
                state.setName(result.getString("stateName"));
                product.setState(state);

                orderDetail.setProduct(product);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderDetails;
    }

    @Override
    public OrderDetail get(int id) {
        ProductDBContext productDB = new ProductDBContext();
        String sql = "SELECT [order_detail].[id]\n"
                + "      ,[order_detail].[quantity]\n"
                + "      ,[order_detail].[price]\n"
                + "      ,[order_detail].[discount]\n"
                + "      ,[order_detail].[created_at]\n"
                + "      ,[order_detail].[updated_at]\n"
                + "      ,[order_detail].[productId]\n"
                + "      ,[order_detail].[orderId]\n"
                + "	  ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price] as 'product_price'\n"
                + "      ,[product].[quantity] as 'product_quantity'\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount] as 'product_discount'\n"
                + "      ,[product].[created_at] as 'product_created_at'\n"
                + "      ,[product].[updated_at] as 'product_updated_at'\n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "      ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "      ,[state].[name] as 'stateName'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [product].[id] DESC) as row_index\n"
                + "  FROM [order_detail]\n"
                + "  INNER JOIN [product] ON [product].[id] = [order_detail].[productId]\n"
                + "  INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + "  INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + "  INNER JOIN [state] ON [state].[id] = [product].[state]\n"
                + " WHERE [order_detail].[id] = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(result.getInt("id"));
                orderDetail.setOrderId(result.getInt("orderId"));
                orderDetail.setProductId(result.getInt("productId"));
                orderDetail.setQuantity(result.getInt("quantity"));
                orderDetail.setDiscount(result.getInt("discount"));
                orderDetail.setPrice(result.getDouble("price"));
                orderDetail.setCreated_at(result.getTimestamp("created_at"));
                orderDetail.setUpdated_at(result.getTimestamp("updated_at"));

                Product product = new Product();
                product.setId(result.getInt("productId"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("product_price"));
                product.setQuantity(result.getInt("product_quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("product_discount"));
                product.setCreated_at(result.getTimestamp("product_created_at"));
                product.setUpdated_at(result.getTimestamp("product_updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));

                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));

                Category category = new Category();
                category.setId(product.getId());
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);

                State state = new State();
                state.setId(result.getInt("state"));
                state.setName(result.getString("stateName"));
                product.setState(state);

                orderDetail.setProduct(product);
                return orderDetail;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public OrderDetail getLast() {
        ProductDBContext productDB = new ProductDBContext();
        String sql = "SELECT TOP 1 [order_detail].[id]\n"
                + "      ,[order_detail].[quantity]\n"
                + "      ,[order_detail].[price]\n"
                + "      ,[order_detail].[discount]\n"
                + "      ,[order_detail].[created_at]\n"
                + "      ,[order_detail].[updated_at]\n"
                + "      ,[order_detail].[productId]\n"
                + "      ,[order_detail].[orderId]\n"
                + "	 ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price] as 'product_price'\n"
                + "      ,[product].[quantity] as 'product_quantity'\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount] as 'product_discount'\n"
                + "      ,[product].[created_at] as 'product_created_at'\n"
                + "      ,[product].[updated_at] as 'product_updated_at'\n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "      ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "      ,[state].[name] as 'stateName'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [product].[id] DESC) as row_index\n"
                + "  FROM [order_detail]\n"
                + "  INNER JOIN [product] ON [product].[id] = [order_detail].[productId]\n"
                + "  INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + "  INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + "  INNER JOIN [state] ON [state].[id] = [product].[state]\n"
                + "  ORDER BY [order_detail].[id] DESC";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(result.getInt("id"));
                orderDetail.setOrderId(result.getInt("orderId"));
                orderDetail.setProductId(result.getInt("productId"));
                orderDetail.setQuantity(result.getInt("quantity"));
                orderDetail.setDiscount(result.getInt("discount"));
                orderDetail.setPrice(result.getDouble("price"));
                orderDetail.setCreated_at(result.getTimestamp("created_at"));
                orderDetail.setUpdated_at(result.getTimestamp("updated_at"));

                Product product = new Product();
                product.setId(result.getInt("productId"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("product_price"));
                product.setQuantity(result.getInt("product_quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("product_discount"));
                product.setCreated_at(result.getTimestamp("product_created_at"));
                product.setUpdated_at(result.getTimestamp("product_updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));

                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));

                Category category = new Category();
                category.setId(product.getId());
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);

                State state = new State();
                state.setId(result.getInt("state"));
                state.setName(result.getString("stateName"));
                product.setState(state);
                orderDetail.setProduct(product);
                return orderDetail;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public OrderDetail insert(OrderDetail model) {
        String sql = "INSERT INTO [order_detail]\n"
                + "           ([quantity]\n"
                + "           ,[price]\n"
                + "           ,[discount]\n"
                + "           ,[created_at]\n"
                + "           ,[updated_at]\n"
                + "           ,[productId]\n"
                + "           ,[orderId])\n"
                + "     VALUES(?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getQuantity());
            statement.setDouble(2, model.getPrice());
            statement.setInt(3, model.getDiscount());
            statement.setTimestamp(4, model.getCreated_at());
            statement.setTimestamp(5, model.getUpdated_at());
            statement.setInt(6, model.getProductId());
            statement.setInt(7, model.getOrderId());
            statement.executeUpdate();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(OrderDetail model) {
        String sql = "UPDATE [order_detail]\n"
                + "        SET [quantity]=?\n"
                + "           ,[price]=?\n"
                + "           ,[discount]=?\n"
                + "           ,[created_at]=?\n"
                + "           ,[updated_at]=?\n"
                + "           ,[productId]=?\n"
                + "           ,[orderId]=?\n"
                + "     WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getQuantity());
            statement.setDouble(2, model.getPrice());
            statement.setInt(3, model.getDiscount());
            statement.setTimestamp(4, model.getCreated_at());
            statement.setTimestamp(5, model.getUpdated_at());
            statement.setInt(6, model.getProductId());
            statement.setInt(7, model.getOrderId());
            statement.setInt(8, model.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM [order_detail]\n"
                    + "WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteByProduct(int productId) {
        try {
            String sql = "DELETE FROM [order_detail]\n"
                    + "WHERE productId = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
