package infrastructure;

import domain.models.Articulo;
import java.util.ArrayList;

public interface IPersistenciaCarrito {

    void saveArticulo(Articulo articulo);
    Articulo findByname(String name);
    ArrayList<Articulo> getCarrito();
    void deleteArticulo(String nombre);

}
