package domain.models;

import java.util.ArrayList;

public class Carrito {

    private ArrayList<Articulo> carrito;
    private double total;


    public Carrito() {
    }

    public Carrito(ArrayList<Articulo> carrito, double total) {
        this.carrito = carrito;
        this.total = total;
    }

    public ArrayList<Articulo> getCarrito() {
        return carrito;
    }

    public void setCarrito(ArrayList<Articulo> carrito) {
        this.carrito = carrito;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
