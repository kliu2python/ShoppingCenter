package functions;

import java.sql.*;

import pool.*;

public class CheckStorages {
    static final String QUERY = "SELECT id, item, price, stock FROM Storage";

    public CheckStorages() {}

    public CheckStorages (String item) {

    }

    public void getStorage() {
        MyConnection myconn = new MyConnection();

        try(Connection conn = myconn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);) {
            System.out.println("Connected to database succeed, print the items...");
            while (rs.next()) {

                // Retrieve by column name
                System.out.println("----------------------------------------------------------");
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(" Item: " + rs.getString("item"));
                System.out.print(" Price: " + rs.getDouble("price"));
                System.out.println(" Stock Amount: " + rs.getInt("stock"));
                System.out.println("----------------------------------------------------------");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
