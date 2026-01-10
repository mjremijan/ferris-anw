/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ferris.anw.db.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Michael
 */
public class SqlHelper {
    
    private Connection connSqlite;
    public Connection sqlite() {
        return connSqlite;
    }

    private Connection connAccess;
    public Connection access() {
        return connAccess;                
    }
    
    public void close() throws Exception {
        try {
            connSqlite.close();
        } catch (Exception e) {}
        try {
            connAccess.close();
        } catch (Exception e) {}
    }
    
    public SqlHelper() throws Exception {
        connSqlite 
            = DriverManager.getConnection("jdbc:sqlite:D:/Documents/Databases/SQLite/anw.db");
        if (connSqlite != null) {
            System.out.println("Connected to SQLite database.");
        } else {
            throw new RuntimeException("Failed to connect to SQLite");
        }
        

        connAccess
            = DriverManager.getConnection("jdbc:ucanaccess://D://Documents//Databases//Access//ANW.accdb");
        if (connAccess != null) {
            System.out.println("Connected to Access database.");
        } else {
            throw new RuntimeException("Failed to connect to Access");
        } 
    }
    private static final Pattern urlPattern = Pattern.compile("^(.*?)#(.*?)#");
    public void setUrl(ResultSet rs, String columnLabel, PreparedStatement stmt, int parameterIndex)
    throws Exception {
        String val = rs.getString(columnLabel);
        if (rs.wasNull()) {
            stmt.setNull(parameterIndex, Types.VARCHAR);
        } else {
            Matcher matcher = urlPattern.matcher(val);
            if (matcher.find()) {
                stmt.setString(parameterIndex, matcher.group(2));
            } else {
                throw new RuntimeException("Error! Do not know what this url pattern is: \""+val+"\" ");
            }
        }
    }
}
