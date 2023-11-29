package com.example.donimusic.modelo;

import com.example.donimusic.controlador.Home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public ArrayList<ListaDeCanciones> obtenerListasUsuario(){
        ArrayList<ListaDeCanciones> arrayListaDeCanciones = new ArrayList<>();
        try {
            String sql = "SELECT lista.nombreUsuario, lista.nombre " +
                    "FROM lista " +
                    "JOIN playListUsuarios ON lista.listaId = playListUsuarios.listaId " +
                    "WHERE playListUsuarios.nombreUsuario = ?;";  // Quité las comillas alrededor del signo de interrogación
            try (PreparedStatement stm = c.prepareStatement(sql)) {
                stm.setString(1, Home.usuario.getNombre());
                String nombreLista="",autorLista="";

                try (ResultSet resultSet = stm.executeQuery()) {
                    while (resultSet.next()) {

                        autorLista = resultSet.getString("nombreUsuario");
                        nombreLista = resultSet.getString("nombre");
                        arrayListaDeCanciones.add(new ListaDeCanciones(nombreLista,autorLista));

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arrayListaDeCanciones;
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
