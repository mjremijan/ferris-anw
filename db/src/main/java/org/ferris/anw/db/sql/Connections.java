/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ferris.anw.db.sql;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Michael
 */
public class Connections {
    
    private Connection connSqlite;
    public Connection sqlite() {
        return connSqlite;
    }

    private Connection connAccess;
    public Connection access() {
        return connAccess;                
    }
    
    public Connections() throws Exception {
//        connSqlite 
//            = DriverManager.getConnection("jdbc:sqlite:D:/Documents/Databases/SQLite/anw.db");
//        if (connSqlite != null) {
//            System.out.println("Connected to SQLite database.");
//        } else {
//            throw new RuntimeException("Failed to connect to SQLite");
//        }
        

        connAccess
            = DriverManager.getConnection("jdbc:ucanaccess://D://Documents//Databases//Access//ANW.accdb");
        if (connAccess != null) {
            System.out.println("Connected to Access database.");
        } else {
            throw new RuntimeException("Failed to connect to Access");
        } 
    }
}
