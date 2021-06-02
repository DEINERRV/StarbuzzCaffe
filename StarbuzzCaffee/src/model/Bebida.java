package model;

public abstract class Bebida {
    private String nombre,descripcion;
    private double precio;
    
    public abstract double coste();

    
    public Bebida(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = nombre;
    }

    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
}
