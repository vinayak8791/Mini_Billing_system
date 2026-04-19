package Database.DAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.DB_connection;
import Database.GetterSetter.Product;

public class productDao {

    // Add Product
    public void addProduct(Product p) {
        try {
            Connection con = DB_connection.getConnection();

            String query = "INSERT INTO product(name, price) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());

            ps.executeUpdate();

            System.out.println("Product Added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  Get All Products
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();

        try {
            Connection con = DB_connection.getConnection();

            String query = "SELECT * FROM product";
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                );
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}