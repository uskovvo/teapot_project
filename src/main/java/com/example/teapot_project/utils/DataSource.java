package com.example.teapot_project.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource{
    //todo Hikari config was hardcoded. Find a way to use hikari.properties file in resources
    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.username";
    private static final String URL_KEY = "db.url";
    private static final String DRIVER_KEY = "db.driver";

    static {
        loadDriver();
    }

    private DataSource() {}

    public static Connection getConnection(){
        try{
            return DriverManager
                    .getConnection(PropertiesUtil.get(URL_KEY),
                            PropertiesUtil.get(USERNAME_KEY),
                            PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void loadDriver(){
        try {
            Class.forName(PropertiesUtil.get(DRIVER_KEY));
        } catch (ClassNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }
}