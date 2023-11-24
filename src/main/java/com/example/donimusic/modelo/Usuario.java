package com.example.donimusic.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    String nombre;
    String password;
    private static Connection c= Conexion.con;
    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    public Usuario() {
    }

    public boolean crearUsuario(){
        PreparedStatement stm;
        try {

            stm=c.prepareStatement("insert into usuario values(?,?);");
            stm.setString(1,nombre);
            stm.setString(2, password);
            stm.execute();

        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                System.out.println("El nombre de usuario ya existe en la base de datos.");
                return false;
            } else {
                e.printStackTrace();
            }
        }
        return true;
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
    public void crearLista(ListaDeCanciones listaDeCanciones) {
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
