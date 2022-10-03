/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.product;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.product.Category;
import model.product.Group;

/**
 *
 * @author LENOVO
 */
public class CategoryDBContext extends DBContext<Category> {

    public ArrayList<Category> findMany(String value, int id) {
        ArrayList<Category> categorys = new ArrayList<>();
        String sql = "SELECT  [category].[id]\n"
                + "      ,[category].[name]\n"
                + "      ,[category].[groupId]\n"
                + "	 ,[group].[name] as 'groupName'\n"
                + "  FROM [category]\n"
                + "  LEFT JOIN [group] ON [group].[id] = [category].[id]\n"
                + " WHERE [category].[name] LIKE ? ";
        if (id != -1) {
            sql += " AND [category].[groupId] = ? ";
        }
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + value + "%");
            if (id != -1) {
                statement.setInt(2, id);
            }
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Category category = new Category();
                category.setId(result.getInt("id"));
                category.setName(result.getString("name"));
                category.setGroupId(result.getInt("groupId"));
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                category.setGroup(group);
                categorys.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categorys;
    }

    public ArrayList<Category> getListByGroup(int id) {
        ArrayList<Category> categorys = new ArrayList<>();
        String sql = "SELECT  [category].[id]\n"
                + "      ,[category].[name]\n"
                + "      ,[category].[groupId]\n"
                + "	  ,[group].[name] as 'groupName'\n"
                + "  FROM [category]\n"
                + "  LEFT JOIN [group] ON [group].[id] = [category].[id]\n"
                + " WHERE [category].[groupId] = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Category category = new Category();
                category.setId(result.getInt("id"));
                category.setName(result.getString("name"));
                category.setGroupId(result.getInt("groupId"));
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                category.setGroup(group);
                categorys.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categorys;
    }

    @Override
    public ArrayList<Category> list() {
        ArrayList<Category> categorys = new ArrayList<>();
        String sql = "SELECT  [category].[id]\n"
                + "      ,[category].[name]\n"
                + "      ,[category].[groupId]\n"
                + "	  ,[group].[name] as 'groupName'\n"
                + "  FROM [category]\n"
                + "  LEFT JOIN [group] ON [group].[id] = [category].[id]";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Category category = new Category();
                category.setId(result.getInt("id"));
                category.setName(result.getString("name"));
                category.setGroupId(result.getInt("groupId"));
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                category.setGroup(group);
                categorys.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categorys;
    }

    @Override
    public Category get(int id) {
        String sql = "SELECT  [category].[id]\n"
                + "      ,[category].[name]\n"
                + "      ,[category].[groupId]\n"
                + "	  ,[group].[name] as 'groupName'\n"
                + "  FROM [category]\n"
                + "  LEFT JOIN [group] ON [group].[id] = [category].[id]\n"
                + " WHERE [category].[id] = ? ";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
               Category category = new Category();
                category.setId(result.getInt("id"));
                category.setName(result.getString("name"));
                category.setGroupId(result.getInt("groupId"));
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                category.setGroup(group);
                return category;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Category insert(Category model) {
        String sql = "INSERT INTO [dbo].[category]\n"
                + "           ([name]\n"
                + "           ,[groupId])\n"
                + "     VALUES(?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, model.getName());
            statement.setInt(2, model.getGroupId());
            statement.executeUpdate();
            ArrayList<Category> categories = list();
            return categories.get(categories.size() - 1);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Category model) {
        String sql = "UPDATE [category]\n"
                + " SET name = ?\n"
                + " ,groupId = ?\n"
                + " WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, model.getName());
            statement.setInt(2, model.getGroupId());
            statement.setInt(3, model.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void deleteByGroup(int id) {
        try {
            String sql = "DELETE FROM [category]\n"
                    + "WHERE groupId = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        ProductDBContext productDB = new ProductDBContext();
        productDB.deleteByCategory(id);
        try {
            String sql = "DELETE FROM [category]\n"
                    + "WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
