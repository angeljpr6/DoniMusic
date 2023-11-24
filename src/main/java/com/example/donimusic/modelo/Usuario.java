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
    public void addCancion(ListaDeCanciones listaDeCanciones,Cancion cancion) {
        listaDeCanciones.addCancion(cancion.getId());
    }
    public void eliminarCancion(ListaDeCanciones listaDeCanciones,Cancion cancion) {
        listaDeCanciones.eliminarCancion(cancion.getId());
    }
    public void buscarCancionEnLista(ListaDeCanciones listaDeCanciones,Cancion cancion){

        //Este metodo debería retornar un arrayList de canciones así que si lo usas recuerda guardar los resultados

        listaDeCanciones.buscarCancion(cancion.getNombre(),listaDeCanciones.getId());
    }

}
