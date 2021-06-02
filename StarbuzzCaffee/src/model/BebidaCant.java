package model;


public class BebidaCant {
    private Bebida bebida;
    private int cantidad;

    public BebidaCant(Bebida bebida, int cantidad) {
        this.bebida = bebida;
        this.cantidad = cantidad;
    }

    public Bebida getBebida() {
        return bebida;
    }

    public void setBebida(Bebida bebida) {
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
        s=bebida.getDescripcion();
        s+="\ncantidad= "+cantidad;
        s+="\nprecio= "+bebida.coste()*cantidad;
        
        return s;
    }
    
}
