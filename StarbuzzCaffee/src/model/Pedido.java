package model;
import java.util.Vector;


public class Pedido {
    private Vector<BebidaCant> bebidas = new Vector();
    double precio;
    String estado,codigo,descripcion;

    public Pedido(String codigo) {
        this.codigo = codigo;
        this.estado = "proceso";
    }
    
    public double calcPrecio(){
        double p = 0;
        
        for(BebidaCant b : bebidas){
            p+= b.getBebida().cost()*b.getCantidad();
        }
        
        return p;
    }
    
    public void agragar(Beverage bebida,int cant){
        bebidas.add(new BebidaCant(bebida,cant));
        this.precio = this.calcPrecio();
        this.descripcion = this.toString();
    }
    
    public void eliminar(BebidaCant bebida){
        bebidas.remove(bebida);
        this.precio = this.calcPrecio();
    }

    public Vector<BebidaCant> getBebidas() {
        return bebidas;
    }

    public void setBebidas(Vector<BebidaCant> bebidas) {
        this.bebidas = bebidas;
        this.precio = this.calcPrecio();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        estado = estado.toLowerCase();
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    public String toString(){
        String s;
        
        s="Pedido\n\n";
        
        for(BebidaCant b : bebidas){
            s+=b.toString()+"\n---------------------\n";
        }
        
        s+="Precio Final= "+precio;
        
        return s;
    }
}
