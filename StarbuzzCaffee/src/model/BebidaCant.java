package model;


public class BebidaCant {
    private Beverage bebida;
    private int cantidad;

    public BebidaCant(Beverage bebida, int cantidad) {
        this.bebida = bebida;
        this.cantidad = cantidad;
    }

    public Beverage getBebida() {
        return bebida;
    }

    public void setBebida(Beverage bebida) {
        this.bebida = bebida;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public String toString(){
        String s;
        s=bebida.getDescription();
        s+="\ncantidad= "+cantidad;
        s+="\nprecio= "+bebida.cost()*cantidad;
        
        return s;
    }
    
}
