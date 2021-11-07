package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class StaffDAO {


    public static boolean validateUser(String name, String password) {
        boolean status = false;
        String sql = "SELECT * FROM staff WHERE UserName = ? AND UserPass = ?";
        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    public static String getDesignation(String name) {
        String designation = "";
        String sql = "select * from Staff where UserName=?";

        try (Connection con = persistence.DBMySQL.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    designation = rs.getString("Designation");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            designation = "Exception";
        }
        return designation;

    }

    public static int addUser(String userName, String userPass, String userPin, String userEmail) {
        int status = 0;
        try {
            String designation = "User";
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("insert into Staff(UserName, UserPass,Designation, Pin, Email) values(?,?,?,?,?)");
            ps.setString(1, userName);
            ps.setString(2, userPass);
            ps.setString(3, designation);
            ps.setString(4, userPin);
            ps.setString(5, userEmail);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;

    }
}
