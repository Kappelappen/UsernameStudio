package com.usernamestudio.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class Database {

    private boolean initialized = false;

    private static final String DB_NAME = "usernamestudio.db";

    public void doCheckDb() {

        loadDriver();

        File dbFile = getDatabaseFile();
        File dbDir = dbFile.getParentFile();

        if (!dbDir.exists()) {
            if (!dbDir.mkdirs()) {
                System.err.println("Kunde inte skapa katalog: " + dbDir.getAbsolutePath());
                return;
            }
        }
        
        String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();       
        
        try (Connection conn = DriverManager.getConnection(url)) {
        	
            if (dbFile.exists()) {
                System.out.println("Databas finns: " + dbFile.getAbsolutePath());
            } else {
                System.out.println("Skapar databas: " + dbFile.getAbsolutePath());
            }

            initTables(conn);

        } catch (SQLException e) {
            System.err.println("Fel vid anslutning till SQLite: " + e.getMessage());
        }
    }

    private void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite driver laddad");
        } catch (ClassNotFoundException e) {
            System.err.println("Kunde inte ladda SQLite JDBC driver");
            e.printStackTrace();
        }
    } 

    private File getDatabaseFile() {

        // enkel och stabil lösning (ingen reflection)
        String userHome = System.getProperty("user.home");

        File dir = new File(userHome, "usernamestudio");
        return new File(dir, DB_NAME);
    }

    private void initTables(Connection conn) {

        if (initialized) return;

        try (Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='usernames';"
            );

            if (rs.next()) {
                System.out.println("Tabeller finns redan, hoppar init.");
                initialized = true;
                return;
            }

            System.out.println("Skapar tabeller...");

            executeSqlFromClasspath(conn, "sql/main/create.sql");
            executeSqlFromClasspath(conn, "sql/main/insert.sql");

            initialized = true;

        } catch (SQLException e) {
            System.err.println("Fel vid init av tabeller: " + e.getMessage());
        }
    }

    private void executeSqlFromClasspath(Connection conn, String path) {

        InputStream is = getClass().getClassLoader().getResourceAsStream(path);

        if (is == null) {
            System.err.println("Hittar inte SQL-fil: " + path);
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(is));
             Statement stmt = conn.createStatement()) {

            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) continue;
                if (line.startsWith("--")) continue;

                sql.append(line).append(" ");

                if (line.endsWith(";")) {

                    String finalSql = sql.toString().trim();

                    // ta bort sista semicolon
                    if (finalSql.endsWith(";")) {
                        finalSql = finalSql.substring(0, finalSql.length() - 1);
                    }

                    try {
                        stmt.execute(finalSql);
                    } catch (SQLException e) {
                        System.err.println("SQL fel: " + finalSql);
                        System.err.println(e.getMessage());
                    }

                    sql.setLength(0);
                }
            }

        } catch (Exception e) {
            System.err.println("Fel vid SQL-exekvering: " + e.getMessage());
        }
    }
}