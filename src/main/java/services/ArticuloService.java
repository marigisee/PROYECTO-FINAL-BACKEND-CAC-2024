package services;

import domain.models.Articulo;
import infrastructure.IPersistenciaArticulo;
import infrastructure.database.MySQLPersArticuloImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class ArticuloService implements IPersistenciaArticulo {

    private IPersistenciaArticulo persistencia = new MySQLPersArticuloImpl();


    @Override
    public void saveArticulo(Articulo articulo) {
        persistencia.saveArticulo(articulo);
    }

    @Override
    public Articulo findByname(String username) {
        return persistencia.findByname(username);
    }

    @Override
    public ArrayList<Articulo> getAllArticulos() {
        return persistencia.getAllArticulos();
    }
}


