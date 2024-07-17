package infrastructure.database;

import domain.models.Articulo;
import infrastructure.IPersistenciaCarrito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLPersCarritoImpl implements IPersistenciaCarrito {

    private Connection conexion;

    public MySQLPersCarritoImpl() {
        // Aquí se asigna al atributo conexion la conexión ya inicalizada en DataBaseConnection //
        this.conexion = DataBaseConnection.getConnection();
    }


    @Override
    public void saveArticulo(Articulo articulo) {
        // QUERY para insertar un articulo en la tabla carritoDB //
        String sql = "INSERT INTO carrito (nombreArticulo, nombreVendedor, descripcion, precio, precioEnvio) VALUES (?, ?, ?, ? , ?)";
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

    /**
     * Método que busca un articulo por nombre en la tabla carrito
     * @return Articulo - Articulo encontrado
     */
    @Override
    public Articulo findByname(String nombreArticulo) {
        // QUERY para buscar un articulo por nombre en la tabla carrito //
        String sql = "SELECT * FROM carrito WHERE nombreArticulo = ?";
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

    /**
     * Método que obtiene todos los articulos del carrito
     * @return ArrayList<Articulo> - Lista de articulos del carrito
     */
    @Override
    public ArrayList<Articulo> getCarrito() {
        String sql = "SELECT * FROM carrito";
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
                articulo.setPrecioEnvio(resultados.getDouble("precioEnvio"));
                articulos.add(articulo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return articulos;
    }

    /**
     * Método que elimina un articulo de la tabla carrito mediante el id
     */
    @Override
    public void deleteArticulo(String nombreArticulo) {
        String sql = "DELETE FROM carrito WHERE nombreArticulo = ?";

        try {

            PreparedStatement preparador = this.conexion.prepareStatement(sql);
            preparador.setString(1, nombreArticulo);
            preparador.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
