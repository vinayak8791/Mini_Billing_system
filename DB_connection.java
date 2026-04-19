package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_connection {

    private static final String URL = "jdbc:mysql://localhost:3306/billing_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "8791";

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            if (con != null && !con.isClosed()) {
                System.out.println(" Database Connected Successfully!");
            }

            return con;

        } catch (Exception e) {
            System.out.println(" Database Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

}
