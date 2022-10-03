/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.product;

import dal.DBContext;
import dal.order.OrderDetailDBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.product.Category;
import model.product.Group;
import model.product.Image;
import model.product.Product;
import model.product.State;
import utils.FileManage;

/**
 *
 * @author LENOVO
 */
public class ProductDBContext extends DBContext<Product> {

    public ArrayList<Product> getProducts(String search, int idGroup, int idCategory, int pageIndex, int pageSize) {
        ArrayList<Product> products = new ArrayList<>();
        ImageDBContext imageDB = new ImageDBContext();
        String sql = "SELECT * FROM (SELECT [product].[id]\n"
                + "      ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price]\n"
                + "      ,[product].[quantity]\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount]\n"
                + "      ,[product].[created_at] \n"
                + "      ,[product].[updated_at] \n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "	 ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "	 ,[state].[name] as 'stateName'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [product].[id] DESC) as row_index\n"
                + "  FROM [product]\n"
                + "  INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + "  INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + "  INNER JOIN [state] ON [state].[id] = [product].[state]\n"
                + " WHERE [product].[name] LIKE ? ";
        if (idGroup != -1) {
            sql += " AND [group].[id] = ? ";
            if (idCategory != -1) {
                sql += " AND [category].[id] = ? ";
            }
        } else {
            if (idCategory != -1) {
                sql += " AND [category].[id] = ? ";
            }
        }

        sql += ") [product] \n"
                + " WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + search + "%");
            if (idGroup != -1) {
                statement.setInt(2, idGroup);
                if (idCategory != -1) {
                    statement.setInt(3, idCategory);
                    statement.setInt(4, pageIndex);
                    statement.setInt(5, pageSize);
                    statement.setInt(6, pageIndex);
                    statement.setInt(7, pageSize);
                } else {
                    statement.setInt(3, pageIndex);
                    statement.setInt(4, pageSize);
                    statement.setInt(5, pageIndex);
                    statement.setInt(6, pageSize);
                }
            } else {
                if (idCategory != -1) {
                    statement.setInt(2, idCategory);
                    statement.setInt(3, pageIndex);
                    statement.setInt(4, pageSize);
                    statement.setInt(5, pageIndex);
                    statement.setInt(6, pageSize);
                } else {
                    statement.setInt(2, pageIndex);
                    statement.setInt(3, pageSize);
                    statement.setInt(4, pageIndex);
                    statement.setInt(5, pageSize);
                }
            }
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getInt("id"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("price"));
                product.setQuantity(result.getInt("quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("discount"));
                product.setCreated_at(result.getTimestamp("created_at"));
                product.setUpdated_at(result.getTimestamp("updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                Category category = new Category();
                category.setId(result.getInt("categoryId"));
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);
                State state = new State();
                state.setId(result.getInt("state"));
                state.setName(result.getString("stateName"));
                product.setState(state);
                ArrayList<Image> images = imageDB.findByProduct(product.getId());
                product.setImages(images);
                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    public int getSizeFromSearch(String search, int idGroup, int idCategory) {
        String sql = "SELECT COUNT([product].[id]) as 'size' \n"
                + "  FROM [product] INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + "  INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + " WHERE [product].[name] LIKE ? ";
        if (idGroup != -1) {
            sql += " AND [group].[id] = ?";
            if (idCategory != -1) {
                sql += " AND [category].[id] = ?";
            }
        } else {
            if (idCategory != -1) {
                sql += " AND [category].[id] = ?";
            }
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + search + "%");
            if (idGroup != -1) {
                statement.setInt(2, idGroup);
                if (idCategory != -1) {
                    sql += " AND [category].[id] = ? ";
                    statement.setInt(3, idCategory);
                }
            } else {
                if (idCategory != -1) {
                    sql += " [category].[id] = ? ";
                    statement.setInt(2, idCategory);
                }
            }
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int size = result.getInt("size");
                return size;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Product> findByCategory(int categoryId, int pageIndex, int pageSize) {
        ArrayList<Product> products = new ArrayList<>();
        ImageDBContext imageDB = new ImageDBContext();
        String sql = "SELECT * FROM (SELECT [product].[id]\n"
                + "      ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price]\n"
                + "      ,[product].[quantity]\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount]\n"
                + "      ,[product].[created_at] \n"
                + "      ,[product].[updated_at] \n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "	 ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "	 ,[state].[name] as 'stateName'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [product].[id] DESC) as row_index\n"
                + "  FROM [product]\n"
                + "  INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + "  INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + "  INNER JOIN [state] ON [state].[id] = [product].[state]\n"
                + " WHERE [product].[categoryId] = ?) [product]\n"
                + " WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);
            statement.setInt(2, pageIndex);
            statement.setInt(3, pageSize);
            statement.setInt(4, pageIndex);
            statement.setInt(5, pageSize);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getInt("id"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("price"));
                product.setQuantity(result.getInt("quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("discount"));
                product.setCreated_at(result.getTimestamp("created_at"));
                product.setUpdated_at(result.getTimestamp("updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));
                
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                
                Category category = new Category();
                category.setId(result.getInt("categoryId"));
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);
                
                State state = new State();
                state.setId(result.getInt("state"));
                state.setName(result.getString("stateName"));
                product.setState(state);
                
                ArrayList<Image> images = imageDB.findByProduct(product.getId());
                product.setImages(images);
                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    @Override
    public ArrayList<Product> list() {
        ArrayList<Product> products = new ArrayList<>();
        CategoryDBContext categoryDB = new CategoryDBContext();
        ImageDBContext imageDB = new ImageDBContext();
        String sql = "SELECT [product].[id]\n"
                + "      ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price]\n"
                + "      ,[product].[quantity]\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount]\n"
                + "      ,[product].[created_at] \n"
                + "      ,[product].[updated_at] \n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "	  ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "	  ,[state].[name] as 'stateName'\n"
                + "  FROM [product]\n"
                + "  INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + "  INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + "  INNER JOIN [state] ON [state].[id] = [product].[state]";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getInt("id"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("price"));
                product.setQuantity(result.getInt("quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("discount"));
                product.setCreated_at(result.getTimestamp("created_at"));
                product.setUpdated_at(result.getTimestamp("updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                Category category = new Category();
                category.setId(result.getInt("categoryId"));
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);
                State state = new State();
                state.setId(result.getInt("state"));
                state.setName(result.getString("stateName"));
                product.setState(state);
                ArrayList<Image> images = imageDB.findByProduct(product.getId());
                product.setImages(images);
                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    @Override
    public Product get(int id) {
        CategoryDBContext categoryDB = new CategoryDBContext();
        ImageDBContext imageDB = new ImageDBContext();
        StateDBContext stateDB = new StateDBContext();
        String sql = "SELECT [product].[id]\n"
                + "      ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price]\n"
                + "      ,[product].[quantity]\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount]\n"
                + "      ,[product].[created_at] \n"
                + "      ,[product].[updated_at] \n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "	 ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "	  ,[state].[name] as 'stateName'\n"
                + "  FROM [product]\n"
                + "  INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + "  INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + "  INNER JOIN [state] ON [state].[id] = [product].[state]\n"
                + " WHERE [product].[id] = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getInt("id"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("price"));
                product.setQuantity(result.getInt("quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("discount"));
                product.setCreated_at(result.getTimestamp("created_at"));
                product.setUpdated_at(result.getTimestamp("updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                Category category = new Category();
                category.setId(result.getInt("categoryId"));
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);
                State state = new State();
                state.setId(result.getInt("state"));
                state.setName(result.getString("stateName"));
                product.setState(state);
                ArrayList<Image> images = imageDB.findByProduct(product.getId());
                product.setImages(images);

                return product;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Product getLast() {
        ArrayList<Product> products = new ArrayList<>();
        CategoryDBContext categoryDB = new CategoryDBContext();
        ImageDBContext imageDB = new ImageDBContext();
        StateDBContext stateDB = new StateDBContext();
        String sql = "SELECT TOP 1 [product].[id]\n"
                + "      ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price]\n"
                + "      ,[product].[quantity]\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount]\n"
                + "      ,[product].[created_at] \n"
                + "      ,[product].[updated_at] \n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "	 ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "	 ,[state].[name] as 'stateName'\n"
                + "  FROM [product]\n"
                + "  INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + "  INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + "  INNER JOIN [state] ON [state].[id] = [product].[state]\n"
                + " ORDER BY id DESC";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                 Product product = new Product();
                product.setId(result.getInt("id"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("price"));
                product.setQuantity(result.getInt("quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("discount"));
                product.setCreated_at(result.getTimestamp("created_at"));
                product.setUpdated_at(result.getTimestamp("updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                Category category = new Category();
                category.setId(result.getInt("categoryId"));
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);
                State state = new State();
                state.setId(result.getInt("state"));
                state.setName(result.getString("stateName"));
                product.setState(state);
                ArrayList<Image> images = imageDB.findByProduct(product.getId());
                product.setImages(images);
                return product;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Product insert(Product model) {
        String sql = "INSERT INTO [product]\n"
                + "           ([name]\n"
                + "           ,[brand]\n"
                + "           ,[price]\n"
                + "           ,[quantity]\n"
                + "           ,[description]\n"
                + "           ,[isSale]\n"
                + "           ,[discount]\n"
                + "           ,[created_at]\n"
                + "           ,[updated_at]\n"
                + "           ,[categoryId]\n"
                + "           ,[state])\n"
                + "     VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, model.getName());
            statement.setString(2, model.getBrand());
            statement.setDouble(3, model.getPrice());
            statement.setInt(4, model.getQuantity());
            statement.setString(5, model.getDescription());
            statement.setBoolean(6, model.isIsSale());
            statement.setInt(7, model.getDiscount());
            statement.setTimestamp(8, model.getCreated_at());
            statement.setTimestamp(9, model.getUpdated_at());
            statement.setInt(10, model.getCategoryId());
            statement.setInt(11, model.getStateId());
            statement.executeUpdate();
            return getLast();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Product model) {
        String sql = "UPDATE [product]\n"
                + "      SET  [name] = ?\n"
                + "           ,[brand] = ?\n"
                + "           ,[price] = ?\n"
                + "           ,[quantity] = ?\n"
                + "           ,[description] = ?\n"
                + "           ,[isSale] = ?\n"
                + "           ,[discount] = ?\n"
                + "           ,[created_at] = ?\n"
                + "           ,[updated_at] = ?\n"
                + "           ,[categoryId] = ?\n"
                + "           ,[state] = ?\n"
                + " WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, model.getName());
            statement.setString(2, model.getBrand());
            statement.setDouble(3, model.getPrice());
            statement.setInt(4, model.getQuantity());
            statement.setString(5, model.getDescription());
            statement.setBoolean(6, model.isIsSale());
            statement.setInt(7, model.getDiscount());
            statement.setTimestamp(8, model.getCreated_at());
            statement.setTimestamp(9, model.getUpdated_at());
            statement.setInt(10, model.getCategoryId());
            statement.setInt(11, model.getStateId());
            statement.setInt(12, model.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void deleteByCategory(int id) {
        try {
            String sql = "DELETE FROM [product]\n"
                    + "WHERE categoryId = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        ImageDBContext imageDB = new ImageDBContext();
        OrderDetailDBContext orderDetailDB = new OrderDetailDBContext();
        imageDB.deleteByProduct(id);
        orderDetailDB.deleteByProduct(id);
        try {
            String sql = "DELETE FROM [product]\n"
                    + "WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getSize() {
        String sql = "SELECT COUNT([product].[id]) as 'size'  FROM [product]";
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
