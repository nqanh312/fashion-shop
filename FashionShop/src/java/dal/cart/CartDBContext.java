/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.cart;

import dal.DBContext;
import dal.product.CategoryDBContext;
import dal.product.GroupDBContext;
import dal.product.ImageDBContext;
import dal.product.ProductDBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.auth.Permission;
import model.auth.User;
import model.cart.Cart;
import model.product.Category;
import model.product.Group;
import model.product.Image;
import model.product.Product;

/**
 *
 * @author LENOVO
 */
public class CartDBContext extends DBContext<Cart> {

    public ArrayList<Cart> findByUser(int userId) {
        ArrayList<Cart> carts = new ArrayList<>();
        CategoryDBContext categoryDB = new CategoryDBContext();
        ImageDBContext imageDB = new ImageDBContext();
        ProductDBContext productDB = new ProductDBContext();
        String sql = "SELECT [cart].[id]\n"
                + "      ,[cart].[userId]\n"
                + "      ,[cart].[productId]\n"
                + "      ,[cart].[quantity]\n"
                + "      ,[cart].[created_at]\n"
                + "      ,[cart].[updated_at]\n"
                + "	 ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price]\n"
                + "      ,[product].[quantity] as 'product_quantity'\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount]\n"
                + "      ,[product].[created_at] as 'product_created_at' \n"
                + "      ,[product].[updated_at] as 'product_updated_at'\n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "	 ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "	 ,[user].[username]\n"
                + "      ,[user].[password]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[birthday]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[is_active]\n"
                + "      ,[user].[permission]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[created_at] as 'user_created_at'\n"
                + "      ,[user].[updated_at] as 'user_updated_at'\n"
                + "	  ,[permission].[id] as 'permissionId'\n"
                + "      ,[permission].[name] as 'permissionName'\n"
                + " FROM [cart]\n"
                + " INNER JOIN [product] ON [product].[id] = [cart].[productId]\n"
                + " INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + " INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + " LEFT JOIN  [user] ON [user].[id] = [cart].[userId]\n"
                + " INNER JOIN [user_permission] ON [user_permission].[userId] = [user].[id]\n"
                + " INNER JOIN [permission]  ON [permission].[id] = [user_permission].[permissionId]\n"
                + " WHERE [cart].[userId] = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Cart cart = new Cart();
                cart.setId(result.getInt("id"));
                cart.setProductId(result.getInt("productId"));
                cart.setQuantity(result.getInt("quantity"));
                cart.setUpdated_at(result.getTimestamp("updated_at"));
                cart.setCreated_at(result.getTimestamp("created_at"));

                User user = new User();
                user.setId(result.getInt("userId"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirst_name(result.getString("first_name"));
                user.setLast_name(result.getString("last_name"));
                user.setBirthday(result.getDate("birthday"));
                user.setPhone(result.getString("phone"));
                user.setGender(result.getBoolean("gender"));
                user.setPermission(result.getString("permission"));
                user.setIs_active(result.getBoolean("is_active"));
                user.setIs_super(result.getBoolean("is_super"));
                user.setCreated_at(result.getTimestamp("created_at"));
                user.setUpdated_at(result.getTimestamp("updated_at"));
                user.setAvatar(result.getString("avatar"));

                Permission permission = new Permission();
                permission.setId(result.getInt("permissionId"));
                permission.setName(result.getString("permissionName"));
                user.setUser_permission(permission);

                Product product = new Product();
                product.setId(result.getInt("productId"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("price"));
                product.setQuantity(result.getInt("product_quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("discount"));
                product.setCreated_at(result.getTimestamp("product_created_at"));
                product.setUpdated_at(result.getTimestamp("product_updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));
                
                ArrayList<Image> images = imageDB.findByProduct(product.getId());
                product.setImages(images);
                
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                
                Category category = new Category();
                category.setId(product.getId());
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);
                
                cart.setProduct(product);
                carts.add(cart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return carts;
    }

    @Override
    public ArrayList<Cart> list() {
        ArrayList<Cart> carts = new ArrayList<>();
        ImageDBContext imageDB = new ImageDBContext();
        CategoryDBContext categoryDB = new CategoryDBContext();
        String sql = "SELECT [cart].[id]\n"
                + "      ,[cart].[userId]\n"
                + "      ,[cart].[productId]\n"
                + "      ,[cart].[quantity]\n"
                + "      ,[cart].[created_at]\n"
                + "      ,[cart].[updated_at]\n"
                + "	 ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price]\n"
                + "      ,[product].[quantity] as 'product_quantity'\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount]\n"
                + "      ,[product].[created_at] as 'product_created_at' \n"
                + "      ,[product].[updated_at] as 'product_updated_at'\n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "	 ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "	 ,[user].[username]\n"
                + "      ,[user].[password]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[birthday]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[is_active]\n"
                + "      ,[user].[permission]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[created_at] as 'user_created_at'\n"
                + "      ,[user].[updated_at] as 'user_updated_at'\n"
                + "	  ,[permission].[id] as 'permissionId'\n"
                + "      ,[permission].[name] as 'permissionName'\n"
                + " FROM [cart]\n"
                + " INNER JOIN [product] ON [product].[id] = [cart].[productId]\n"
                + " INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + " INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + " LEFT JOIN  [user] ON [user].[id] = [cart].[userId]\n"
                + " INNER JOIN [user_permission] ON [user_permission].[userId] = [user].[id]\n"
                + " INNER JOIN [permission]  ON [permission].[id] = [user_permission].[permissionId]\n";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Cart cart = new Cart();
                cart.setId(result.getInt("id"));
                cart.setProductId(result.getInt("productId"));
                cart.setQuantity(result.getInt("quantity"));
                cart.setUpdated_at(result.getTimestamp("updated_at"));
                cart.setCreated_at(result.getTimestamp("created_at"));

                User user = new User();
                user.setId(result.getInt("userId"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirst_name(result.getString("first_name"));
                user.setLast_name(result.getString("last_name"));
                user.setBirthday(result.getDate("birthday"));
                user.setPhone(result.getString("phone"));
                user.setGender(result.getBoolean("gender"));
                user.setPermission(result.getString("permission"));
                user.setIs_active(result.getBoolean("is_active"));
                user.setIs_super(result.getBoolean("is_super"));
                user.setCreated_at(result.getTimestamp("created_at"));
                user.setUpdated_at(result.getTimestamp("updated_at"));
                user.setAvatar(result.getString("avatar"));

                Permission permission = new Permission();
                permission.setId(result.getInt("permissionId"));
                permission.setName(result.getString("permissionName"));
                user.setUser_permission(permission);

                Product product = new Product();
                product.setId(result.getInt("productId"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("price"));
                product.setQuantity(result.getInt("product_quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("discount"));
                product.setCreated_at(result.getTimestamp("product_created_at"));
                product.setUpdated_at(result.getTimestamp("product_updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));
                
                ArrayList<Image> images = imageDB.findByProduct(product.getId());
                product.setImages(images);
                
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                
                Category category = new Category();
                category.setId(product.getId());
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);
                
                cart.setProduct(product);
                carts.add(cart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return carts;
    }

    @Override
    public Cart get(int id) {
        ArrayList<Cart> carts = new ArrayList<>();
         ImageDBContext imageDB = new ImageDBContext();
        CategoryDBContext categoryDB = new CategoryDBContext();
        String sql = "SELECT [cart].[id]\n"
                + "      ,[cart].[userId]\n"
                + "      ,[cart].[productId]\n"
                + "      ,[cart].[quantity]\n"
                + "      ,[cart].[created_at]\n"
                + "      ,[cart].[updated_at]\n"
                + "	 ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price]\n"
                + "      ,[product].[quantity] as 'product_quantity'\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount]\n"
                + "      ,[product].[created_at] as 'product_created_at' \n"
                + "      ,[product].[updated_at] as 'product_updated_at'\n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "	 ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "	 ,[user].[username]\n"
                + "      ,[user].[password]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[birthday]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[is_active]\n"
                + "      ,[user].[permission]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[created_at] as 'user_created_at'\n"
                + "      ,[user].[updated_at] as 'user_updated_at'\n"
                + "	  ,[permission].[id] as 'permissionId'\n"
                + "      ,[permission].[name] as 'permissionName'\n"
                + " FROM [cart]\n"
                + " INNER JOIN [product] ON [product].[id] = [cart].[productId]\n"
                + " INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + " INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + " LEFT JOIN  [user] ON [user].[id] = [cart].[userId]\n"
                + " INNER JOIN [user_permission] ON [user_permission].[userId] = [user].[id]\n"
                + " INNER JOIN [permission]  ON [permission].[id] = [user_permission].[permissionId]\n"
                + " WHERE [cart].[id] = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Cart cart = new Cart();
                cart.setId(result.getInt("id"));
                cart.setProductId(result.getInt("productId"));
                cart.setQuantity(result.getInt("quantity"));
                cart.setUpdated_at(result.getTimestamp("updated_at"));
                cart.setCreated_at(result.getTimestamp("created_at"));

                User user = new User();
                user.setId(result.getInt("userId"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirst_name(result.getString("first_name"));
                user.setLast_name(result.getString("last_name"));
                user.setBirthday(result.getDate("birthday"));
                user.setPhone(result.getString("phone"));
                user.setGender(result.getBoolean("gender"));
                user.setPermission(result.getString("permission"));
                user.setIs_active(result.getBoolean("is_active"));
                user.setIs_super(result.getBoolean("is_super"));
                user.setCreated_at(result.getTimestamp("created_at"));
                user.setUpdated_at(result.getTimestamp("updated_at"));
                user.setAvatar(result.getString("avatar"));

                Permission permission = new Permission();
                permission.setId(result.getInt("permissionId"));
                permission.setName(result.getString("permissionName"));
                user.setUser_permission(permission);

                Product product = new Product();
                product.setId(result.getInt("productId"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("price"));
                product.setQuantity(result.getInt("product_quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("discount"));
                product.setCreated_at(result.getTimestamp("product_created_at"));
                product.setUpdated_at(result.getTimestamp("product_updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));
                
                ArrayList<Image> images = imageDB.findByProduct(product.getId());
                product.setImages(images);
                
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                
                Category category = new Category();
                category.setId(product.getId());
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);
                
                cart.setProduct(product);
                return cart;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Cart findByProductUser(int productId, int userId) {
        ArrayList<Cart> carts = new ArrayList<>();
         ImageDBContext imageDB = new ImageDBContext();
        CategoryDBContext categoryDB = new CategoryDBContext();
        String sql = "SELECT [cart].[id]\n"
                + "      ,[cart].[userId]\n"
                + "      ,[cart].[productId]\n"
                + "      ,[cart].[quantity]\n"
                + "      ,[cart].[created_at]\n"
                + "      ,[cart].[updated_at]\n"
                + "	 ,[product].[name]\n"
                + "      ,[product].[brand]\n"
                + "      ,[product].[price]\n"
                + "      ,[product].[quantity] as 'product_quantity'\n"
                + "      ,[product].[description]\n"
                + "      ,[product].[isSale]\n"
                + "      ,[product].[discount]\n"
                + "      ,[product].[created_at] as 'product_created_at' \n"
                + "      ,[product].[updated_at] as 'product_updated_at'\n"
                + "      ,[product].[categoryId]\n"
                + "      ,[product].[state]\n"
                + "	 ,[category].[name] as 'categoryName'\n"
                + "      ,[group].[id] as 'groupId'\n"
                + "      ,[group].[name] as 'groupName'\n"
                + "	 ,[user].[username]\n"
                + "      ,[user].[password]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[birthday]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[is_active]\n"
                + "      ,[user].[permission]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[created_at] as 'user_created_at'\n"
                + "      ,[user].[updated_at] as 'user_updated_at'\n"
                + "	  ,[permission].[id] as 'permissionId'\n"
                + "      ,[permission].[name] as 'permissionName'\n"
                + " FROM [cart]\n"
                + " INNER JOIN [product] ON [product].[id] = [cart].[productId]\n"
                + " INNER JOIN [category] ON [category].[id] = [product].[categoryId]\n"
                + " INNER JOIN [group] ON [group].[id] = [category].[groupId]\n"
                + " LEFT JOIN  [user] ON [user].[id] = [cart].[userId]\n"
                + " INNER JOIN [user_permission] ON [user_permission].[userId] = [user].[id]\n"
                + " INNER JOIN [permission]  ON [permission].[id] = [user_permission].[permissionId]\n"
                + " WHERE [cart].[productId] = ? and [cart].[userId] = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);
            statement.setInt(2, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Cart cart = new Cart();
                cart.setId(result.getInt("id"));
                cart.setProductId(result.getInt("productId"));
                cart.setQuantity(result.getInt("quantity"));
                cart.setUpdated_at(result.getTimestamp("updated_at"));
                cart.setCreated_at(result.getTimestamp("created_at"));

                User user = new User();
                user.setId(result.getInt("userId"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setFirst_name(result.getString("first_name"));
                user.setLast_name(result.getString("last_name"));
                user.setBirthday(result.getDate("birthday"));
                user.setPhone(result.getString("phone"));
                user.setGender(result.getBoolean("gender"));
                user.setPermission(result.getString("permission"));
                user.setIs_active(result.getBoolean("is_active"));
                user.setIs_super(result.getBoolean("is_super"));
                user.setCreated_at(result.getTimestamp("created_at"));
                user.setUpdated_at(result.getTimestamp("updated_at"));
                user.setAvatar(result.getString("avatar"));

                Permission permission = new Permission();
                permission.setId(result.getInt("permissionId"));
                permission.setName(result.getString("permissionName"));
                user.setUser_permission(permission);

                Product product = new Product();
                product.setId(result.getInt("productId"));
                product.setName(result.getString("name"));
                product.setBrand(result.getString("brand"));
                product.setPrice(result.getDouble("price"));
                product.setQuantity(result.getInt("product_quantity"));
                product.setDescription(result.getString("description"));
                product.setIsSale(result.getBoolean("isSale"));
                product.setDiscount(result.getInt("discount"));
                product.setCreated_at(result.getTimestamp("product_created_at"));
                product.setUpdated_at(result.getTimestamp("product_updated_at"));
                product.setCategoryId(result.getInt("categoryId"));
                product.setStateId(result.getInt("state"));
                
                ArrayList<Image> images = imageDB.findByProduct(product.getId());
                product.setImages(images);
                
                Group group = new Group();
                group.setId(result.getInt("groupId"));
                group.setName(result.getString("groupName"));
                
                Category category = new Category();
                category.setId(product.getId());
                category.setName(result.getString("categoryName"));
                category.setGroup(group);
                product.setCategory(category);
                
                cart.setProduct(product);
                return cart;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Cart insert(Cart model) {
        String sql = "INSERT INTO [cart]\n"
                + "           ([userId]\n"
                + "           ,[productId]\n"
                + "           ,[quantity]\n"
                + "           ,[created_at]\n"
                + "           ,[updated_at])\n"
                + "     VALUES(?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getUserId());
            statement.setInt(2, model.getProductId());
            statement.setInt(3, model.getQuantity());
            statement.setTimestamp(4, model.getCreated_at());
            statement.setTimestamp(5, model.getUpdated_at());
            statement.executeUpdate();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Cart model) {
        String sql = "UPDATE [cart]\n"
                + "        SET [quantity] = ?\n"
                + "           ,[updated_at] = ?\n"
                + "     WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getQuantity());
            statement.setTimestamp(2, model.getUpdated_at());
            statement.setInt(3, model.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void deleteByProduct(int productId, int userId) {
        try {
            String sql = "DELETE FROM [cart]\n"
                    + "WHERE productId = ? and userId = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM [cart]\n"
                    + "WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
