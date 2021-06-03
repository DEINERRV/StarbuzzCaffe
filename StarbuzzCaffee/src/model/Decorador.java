package model;

import javafx.scene.control.CheckBox;


public class Decorador extends Bebida{
    private Bebida bebida;
    private CheckBox checkbox;

    public Decorador(String string, double d) {
        super(string, d);
        this.checkbox = new CheckBox();
    }

    
    public Decorador(Bebida bebida,String nombre, double precio) {
        super(nombre, precio);
        this.bebida = bebida;
        this.setDescripcion(bebida.getDescripcion()+" + "+nombre);
        this.checkbox = new CheckBox();

    }


    @Override
    public double coste() {
        return bebida.coste()+this.getPrecio();
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(CheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public Bebida getBebida() {
        return bebida;
    }
    
    
}
