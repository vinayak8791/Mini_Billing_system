package Database.DAO;

import java.sql.*;

import Database.DB_connection;

public class BillDao {

    //  Create Bill (returns bill ID)
    public int createBill(double total) {
        int billId = -1;

        String query = "INSERT INTO bills(total) VALUES (?)";

        try (Connection con = DB_connection.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, total);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    billId = rs.getInt(1);
                }
            }

            System.out.println("Bill Created! ID: " + billId);

        } catch (Exception e) {
            System.out.println("Error creating bill!");
            e.printStackTrace();
        }

        return billId;
    }


    //  Add Items to Bill
    public void addBillItem(int billId, int productId, int quantity) {

        if (billId == -1) {
            System.out.println("Invalid Bill ID!");
            return;
        }

        String query = "INSERT INTO bill_items(bill_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection con = DB_connection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, billId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);

            ps.executeUpdate();

            System.out.println("Item added to Bill!");

        } catch (Exception e) {
            System.out.println("Error adding item!");
            e.printStackTrace();
        }
    }
}