package infrastructure;

import domain.models.Articulo;
import java.util.ArrayList;

public interface IPersistenciaArticulo {

    void saveArticulo(Articulo articulo);
    Articulo findByname(String username);
    ArrayList<Articulo> getAllArticulos();

}
