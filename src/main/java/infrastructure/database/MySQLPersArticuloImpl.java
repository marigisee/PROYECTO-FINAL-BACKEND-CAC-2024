package infrastructure.database;


import domain.models.Articulo;

import infrastructure.IPersistenciaArticulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
* En esta clase vamos a implementar los métodos de la interfaz IPersistenciaArticulo
* En los métodos se preparan las QUERYS correspondientes para interactuar con la base de datos
* */

public class MySQLPersArticuloImpl implements IPersistenciaArticulo {

    private Connection conexion;

    public MySQLPersArticuloImpl() {
        // Aquí se asigna al atributo conexion la conexión ya inicalizada en DataBaseConnection /
        this.conexion = DataBaseConnection.getConnection();
    }


    @Override
    public void saveArticulo(Articulo articulo) {
        String sql = "INSERT INTO articulos (nombreArticulo, nombreVendedor, descripcion, precio, precioEnvio) VALUES (?, ?, ?, ? , ?)";
        try {
            // Preparamos la sentencia SQL
            PreparedStatement preparador =  this.conexion.prepareStatement(sql);

            preparador.setString(1, articulo.getNombreArticulo());
            preparador.setString(2, articulo.getNombreVendedor());
            preparador.setString(3, articulo.getDescripcion());
            preparador.setDouble(4, articulo.getPrecio());
            preparador.setDouble(5, articulo.getPrecioEnvio());

            // Ejecutamos la sentencia SQL
            preparador.executeUpdate();
            // Cerramos la conexión
            preparador.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Articulo findByname(String nombreArticulo) {
        String sql = "SELECT * FROM articulos WHERE nombreArticulo = ?";
        try {
            PreparedStatement preparador = conexion.prepareStatement(sql);
            preparador.setString(1,nombreArticulo);

            ResultSet resultados = preparador.executeQuery();

            if (resultados.next()) {
                Articulo articulo = new Articulo();
                articulo.setId(resultados.getInt("id"));
                articulo.setNombreArticulo(resultados.getString("nombreArticulo"));
                articulo.setNombreVendedor(resultados.getString("nombreVendedor"));
                articulo.setDescripcion(resultados.getString("descripcion"));
                articulo.setPrecio(resultados.getDouble("precio"));
                articulo.setPrecioEnvio(resultados.getDouble("precioEnvio"));
                return articulo;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Articulo> getAllArticulos() {
        String sql = "SELECT * FROM articulos";
        ArrayList<Articulo> articulos = new ArrayList<>();
        try {

            PreparedStatement preparador = conexion.prepareStatement(sql);
            ResultSet resultados = preparador.executeQuery();

            while (resultados.next()) {
                Articulo articulo = new Articulo();
                articulo.setId(resultados.getInt("id"));
                articulo.setNombreArticulo(resultados.getString("nombreArticulo"));
                articulo.setNombreVendedor(resultados.getString("nombreVendedor"));
                articulo.setDescripcion(resultados.getString("descripcion"));
                articulo.setPrecio(resultados.getDouble("precio"));
                articulo.setPrecioEnvio(resultados.getDouble("precioenvio"));
                articulos.add(articulo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return articulos;
    }
}
