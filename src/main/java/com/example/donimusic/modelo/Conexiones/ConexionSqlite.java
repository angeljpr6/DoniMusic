package com.example.donimusic.modelo.Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionSqlite {

    private final static String url = "jdbc:sqlite:src/main/resources/BaseDeDatos/recuerdaUsuario.db";
    public static Connection con;

    public Connection conectar() {
        con = null;

        try {

            con = DriverManager.getConnection(url);
            if (con != null) {

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionSqlite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

}