package com.usernamestudio.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class Connector {

    private static final File dbFile;
    
    static {
    
    	String userHome = System.getProperty("user.home");
        File dbDir = new File(userHome, "UsernameStudio");
        
        if (!dbDir.exists()) dbDir.mkdirs();
        dbFile = new File(dbDir, "UsernameStudio.db");
    
    }

    public static Connection getConnection() throws SQLException {
        	
    	Connection conn = null;
    	
        String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();
        conn = DriverManager.getConnection(url);        
    	
        return conn;
    
    }

    public static File getDatabaseFile() {
        return dbFile;
    }
}