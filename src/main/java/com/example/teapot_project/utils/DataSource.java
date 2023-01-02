package com.example.teapot_project.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource{
    //todo Hikari config was hardcoded. Find a way to use hikari.properties file in resources
    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.username";
    private static final String URL_KEY = "db.url";

    static {
        loadDriver();
    }

    private DataSource() {}

    public static Connection getConnection(){
        try{
            return DriverManager
                    .getConnection(
                            PropertiesUtil.get(URL_KEY),
                            PropertiesUtil.get(USERNAME_KEY),
                            PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void loadDriver(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }
}

