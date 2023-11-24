package com.example.donimusic.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario {
    String nombre;
    String password;
    private static Connection c= Conexion.con;
    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void eliminarLista(ListaDeCanciones listaDeCanciones){
    listaDeCanciones.eliminarLista();
    }
    public void addLista(ListaDeCanciones listaDeCanciones) {
         listaDeCanciones.crearLista(nombre);
    }

}
