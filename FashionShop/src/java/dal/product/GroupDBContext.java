package dal.product;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class GroupDBContext extends DBContext<Group> {

    @Override
    public ArrayList<Group> list() {
        ArrayList<Group> groups = new ArrayList<>();
        CategoryDBContext categoryDB = new CategoryDBContext();
        String sql = "SELECT id, name FROM [group]";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Group group = new Group();
                group.setId(result.getInt("id"));
                group.setName(result.getString("name"));
                ArrayList<Category> categories = categoryDB.getListByGroup(group.getId());
                group.setCategories(categories);
                groups.add(group);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups;
    }

    @Override
    public Group get(int id) {
        CategoryDBContext categoryDB = new CategoryDBContext();
        String sql = "SELECT id, name FROM [group]\n"
                + " WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Group group = new Group();
                group.setId(result.getInt("id"));
                group.setName(result.getString("name"));
                ArrayList<Category> categories = categoryDB.getListByGroup(group.getId());
                group.setCategories(categories);
                return group;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Group insert(Group model) {
        String sql = "INSERT INTO [Group]\n"
                + " ([name])\n"
                + " VALUES(?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, model.getName());
            statement.executeUpdate();
            ArrayList<Group> groups = list();
            return groups.get(groups.size()-1);
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Group model) {
        String sql = "UPDATE [group]\n"
                + " SET name = ?\n"
                + " WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, model.getName());
            statement.setInt(2, model.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        CategoryDBContext categoryDB = new CategoryDBContext();
        categoryDB.deleteByGroup(id);
        try {
            String sql = "DELETE FROM [group]\n"
                    + "WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
