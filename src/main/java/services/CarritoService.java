package services;

import domain.models.Articulo;
import infrastructure.IPersistenciaCarrito;
import infrastructure.database.MySQLPersCarritoImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class CarritoService implements IPersistenciaCarrito {

    private IPersistenciaCarrito persistencia = new MySQLPersCarritoImpl();


    @Override
    public void saveArticulo(Articulo articulo) {
        persistencia.saveArticulo(articulo);
    }

    @Override
    public Articulo findByname(String name) {
        return persistencia.findByname(name);
    }

    @Override
    public ArrayList<Articulo> getCarrito() {
        return persistencia.getCarrito();
    }

    @Override
    public void deleteArticulo(String nombre) {
        persistencia.deleteArticulo(nombre);
    }
}


