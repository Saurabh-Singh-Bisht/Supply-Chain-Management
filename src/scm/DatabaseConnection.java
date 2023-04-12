/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scm;

import com.sun.deploy.config.Platform;
import java.net.URL;
import java.sql.*;

/**
 *
 * @author SSB
 */
public class DatabaseConnection {
    
    static String URL = "jdbc:mysql://localhost:3306/supplychainmanagement";
    static String user = "root";
    static String password = "123456@Kd";
    
    public static Connection getStatement(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, user, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
