package BankManagementSystem;

import java.sql.*;

public class DBConn {

    Connection con;
    Statement stmt;

    public DBConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb","root","");
            stmt = con.createStatement();
        }
        catch(SQLException | ClassNotFoundException sqle) {
            System.out.println("Error: " + sqle);
        }
    }

    public void close() throws SQLException {
        stmt.close();
        con.close();
    }
}