package com.example.demo;

import java.sql.*;

public class DBManager
{
    private static Boolean connectionEstablished = false;
    private static Connection c;
    public static Connection GetConncection() throws SQLException {
        if(!connectionEstablished)
        {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/javadb", "root", "");
            connectionEstablished = true;
        }
        return c;
    }
}
