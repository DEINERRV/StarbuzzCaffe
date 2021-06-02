package model;

public class BebidaEspecifica extends Bebida {

    public BebidaEspecifica(String nombre, double precio) {
        super(nombre, precio);
    }

    @Override
    public double coste() {
        return this.getPrecio();
    }
    
}
