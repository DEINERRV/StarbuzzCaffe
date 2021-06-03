/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Alert2;
import model.Bebida;
import model.BebidaCant;
import model.BebidaEspecifica;
import model.Decorador;
import model.Pedido;

/**
 * FXML Controller class
 *
 * @author deine
 */
public class BebidaPedidoController implements Initializable {

    @FXML
    private TableView<BebidaCant> tblBebidas;
    @FXML
    private MenuButton btnBebida;
    @FXML
    private TableView<Decorador> tblExtras;
    @FXML
    private TableColumn<BebidaCant, String> colnom;
    @FXML
    private TableColumn<BebidaCant, String> colPrecio;
    @FXML
    private TableColumn<?, ?> colCant;
    @FXML
    private MenuItem btnb1;
    @FXML
    private MenuItem btnb2;
    @FXML
    private MenuItem btnb3;
    @FXML
    private MenuItem btnb4;
    @FXML
    private TableColumn<?, ?> colExtraNom;
    @FXML
    private TableColumn<?, ?> colExtraPrecio;
    @FXML
    private TableColumn<?, ?> colCheck;
    @FXML
    private TextField txtCant;
    @FXML
    private Button btnElim;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnModif;
    @FXML
    private Button btnCancelarModif;
    @FXML
    private Button btnCancelarPedido;
    @FXML
    private Button btnRealizarModif;
    @FXML
    private Button btnVerPedidos;
    @FXML
    private Button btnRealizarPedido;
     
    
    private ObservableList<Decorador> extras;
    private ObservableList<BebidaCant> bebidas;
    private Pedido pedido;
    private BebidaCant bebidaSelect = null;
    private Bebida bebidaBase = null;
    private Vector<Pedidos> pedidosControllers = new Vector();
    private Vector<Pedido> pedidos = new Vector();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        pedido = new Pedido("1");
        
        Decorador d1 = new Decorador("Milk",0.12);
        Decorador d2 = new Decorador("Mocha",0.22);
        Decorador d3 = new Decorador("Soy",0.15);
        Decorador d4 = new Decorador("Whip",0.12);
        
        extras = FXCollections.observableArrayList();
        extras.add(d1);
        extras.add(d2);
        extras.add(d3);
        extras.add(d4);
        
        bebidas = FXCollections.observableArrayList();
        
        
        this.tblBebidas.setItems(bebidas);
        this.colnom.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getBebida().getDescripcion()));    
        this.colPrecio.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getBebida().coste()+""));
        this.colCant.setCellValueFactory(new PropertyValueFactory("cantidad"));
        
        this.tblExtras.setItems(extras);
        this.colExtraNom.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colExtraPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        this.colCheck.setCellValueFactory(new PropertyValueFactory("checkbox"));
        
        this.btnCancelarModif.setVisible(false);
        this.btnRealizarModif.setVisible(false);
    }    

    @FXML
    private void eliminar(ActionEvent event) {
        if(bebidaSelect != null)
            bebidas.remove(bebidaSelect);
        else{
            Alert2.showAlert("Error","No order selected", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void bebida1(ActionEvent event) {
        bebidaBase = new BebidaEspecifica(this.btnb1.getText(),1.99);
        this.btnBebida.setText(bebidaBase.getNombre());
    }

    @FXML
    private void bebida2(ActionEvent event) {
        bebidaBase = new BebidaEspecifica(this.btnb2.getText(),2.99);
        this.btnBebida.setText(bebidaBase.getNombre());
    }

    @FXML
    private void bebida3(ActionEvent event) {
        bebidaBase = new BebidaEspecifica(this.btnb3.getText(),3.99);
        this.btnBebida.setText(bebidaBase.getNombre());
    }

    @FXML
    private void bebida4(ActionEvent event) {
        bebidaBase = new BebidaEspecifica(this.btnb1.getText(),1.99);
        this.btnBebida.setText(bebidaBase.getNombre());
    }
    
    @FXML
    private void anadir(ActionEvent event) throws Exception {
        try{
            BebidaCant bebida = this.creaBebida();
            bebidas.add(new BebidaCant(bebida.getBebida(),bebida.getCantidad()));
            
            this.limpiarVariables();  
        }
        catch(NumberFormatException e){//si se escribieron letras en el campo de cantidad de bebidas
            Alert2.showAlert("Error","Incorrect format in quantity field", Alert.AlertType.ERROR);
        }
        catch(Exception e){//si el numero es menor o igual a 0, u otro error
            Alert2.showAlert("Error",e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        bebidaSelect = this.tblBebidas.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void modificar(ActionEvent event) {
        if(bebidaSelect != null){//si selecciono una bebida de la tabla
            
            //se le da un valor ya que puede ser que no entre al if, lo que significario que no tiene extras
            Bebida bebidaBaseAux = bebidaSelect.getBebida();
            
            //si la bebida seleccionada es un decorador(tiene extras)
            if(bebidaSelect.getBebida() instanceof Decorador){
                
                //se realiza un castio para poder tener acceder a las funciones de Decorador
                Decorador aux = (Decorador) bebidaSelect.getBebida();
                
                //ayudara a saber cuando la siguiente bebida del Decorador no es un Decorador y parar el ciclo
                int flag = 1;
                
                //ciclo para seleccionar los extras de la bebida
                while(flag==1){
                    for(Decorador d: extras){
                        if(d.getNombre().equals(aux.getNombre()))
                            d.getCheckbox().setSelected(true);
                    }
                    if(aux.getBebida() instanceof Decorador)//si es un Decorador el ciclo continua
                        aux = (Decorador) aux.getBebida();
                    else//de lo contrario significa que hemos llegado a la bebida Base y detiene el ciclo
                        flag = 0;
                    
                }
                
                //se le asigna la bebida Base
                bebidaBaseAux = aux.getBebida();  
            }
        
            //se asignan los valores correspondientes al resto de campos
            txtCant.setText(bebidaSelect.getCantidad()+"");
            btnBebida.setText(bebidaBaseAux.getNombre());
            bebidaBase = bebidaBaseAux;
            
            //se ocultan y visibilisan los botones correspondientes
            btnAnadir.setVisible(false);
            btnRealizarModif.setVisible(true);
            btnCancelarModif.setVisible(true);
            
        }
        else{
            Alert2.showAlert("Error","No order selected", Alert.AlertType.ERROR);
            
        }
    }

    @FXML
    private void CancelarModif(ActionEvent event) {
        //se oculta y visibilisa los botones correspondientes
        this.btnCancelarModif.setVisible(false);
        this.btnRealizarModif.setVisible(false);
        this.btnAnadir.setVisible(true);
        //se limpian las variables
        this.limpiarVariables();
    }

    @FXML
    private void CancelarPedido(ActionEvent event) {
        //se limpia vectar que contiene las bebidas pedidas
        bebidas.clear();
        
        //se refresaca la tabla para ver los cambios
        tblBebidas.refresh();
        
        //tambien se le asigna null, ya que puede contener informacion 
        //de alguna bebida y debido a que se cancelo el pedido esta puede 
        //llegar a causar un comportamiento indebido de la aplicacion
        bebidaSelect = null;
        
        //se limpian las opciones
        this.limpiarVariables();
        
        //se oculta y visibilisa los botones correspondientes
        btnCancelarModif.setVisible(false);
        btnRealizarModif.setVisible(false);
        btnAnadir.setVisible(true);
    }

    @FXML
    private void realizarModif(ActionEvent event) {
        try{
            //se crea una bebida con las medificaciones 
            BebidaCant bebida = this.creaBebida();
        
            //se modifican los atributos de la bebida seleccionada
            bebidaSelect.setBebida(bebida.getBebida());
            bebidaSelect.setCantidad(bebida.getCantidad());
            
            //se refresca la tabla para poder ver las modificaciones
            this.tblBebidas.refresh();
            
            //se limpian las opciones para que esten lista para una nueva bebida
            this.limpiarVariables();
            
            //se oculta y visibilisa los botones correspondientes
            btnCancelarModif.setVisible(false);
            btnRealizarModif.setVisible(false);
            btnAnadir.setVisible(true);
        }
         catch(NumberFormatException e){//si se escribieron letras
            Alert2.showAlert("Error","Incorrect format in quantity field", Alert.AlertType.ERROR);
        }
        catch(Exception e){//si el numero es menor o igual a 0, u otro error
            Alert2.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        
    }
    
    public void limpiarVariables(){
        //se reestablecen los valores de la parte donde se seleccionan 
        //las caracteristicasde la bebida
        txtCant.clear();
        btnBebida.setText("Select a beverage");
        bebidaBase = null;
        for(Decorador d:extras){
            if(d.getCheckbox().isSelected()){
                d.getCheckbox().setSelected(false);
            }
        }
    }
    
    public BebidaCant creaBebida() throws Exception{
        
        //se obtiene la cantidad que se desea de una bebida del txtfield
        int cant = Integer.parseInt(this.txtCant.getText());
            
        //si es menor a 0 se tira una excepcion ya que no se pueden pedir 0 o -11,por ejemplo, de una bebida
        if(cant <= 0){
            Exception e = new Exception("The quantity is less than or equal to 0");
            throw e;
        }
       
        //no se puede pedir una bebida si no se ha seleccionado que bebida quiere
        if(bebidaBase == null){
            Exception e = new Exception("Base Drink has not been selected");
            System.out.print(e.getMessage());
            throw e;
        }
        
        
        int flagfirst = 0;//ayudara a identificar la primer iteracion del ciclo for
        
        Decorador aux = null;
        Decorador nueva = null;
        
        
        for(Decorador d : extras){
            if(d.getCheckbox().isSelected()){//las que estan seleccionadas mediante el checkbox
                flagfirst++;
                if(flagfirst != 1){
                    nueva = new Decorador(aux,d.getNombre(),d.getPrecio());
                    aux = nueva;
                }
                else{//el primer extra seleccionado va a contener la bebida base 
                    nueva = new Decorador(bebidaBase,d.getNombre(),d.getPrecio());
                    aux = nueva;
                }
            }
        }
        
        //Se crean y annaden la bebida al pedido
        if(nueva != null)//si no se selecciono ningun extra nuevo es null
            return new BebidaCant(nueva,cant);
        else if(bebidaBase != null)
            return new BebidaCant(bebidaBase,cant);
        
        return null;//si no pinia un return afuera de un if daba error
    }

    @FXML
    private void verPedidos(ActionEvent event) {
        try{
            //Carga la View de donde se observan los pedidos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Pedidos.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            
            //se alamcena el controller para cuando se realize una orden nueva 
            //se le pueda pasar
            Pedidos controller = loader.getController();
            pedidosControllers.add(controller);
            
            //se le agragan todos los pedidos que se han hecho
            for(Pedido p: pedidos){
                controller.agragarPedido(p);
            }
            
            //se abre la ventana
            stage.show();
            
        }
        catch(Exception e){
                    
        }
    }

    @FXML
    private void realizarPedido(ActionEvent event) {
        
        if(!bebidas.isEmpty()){//si la orden tiene pedidos
            
            //se agragan los pedido
            for(BebidaCant b: bebidas){
                pedido.agragar(b.getBebida(),b.getCantidad());
            }
            
            //se le comunica la orden a las ventanas donde se ven las ordenes que esten abiertas
            for(Pedidos c: pedidosControllers){
                c.agragarPedido(pedido);
            }
            
            //se almacenan todas las ordenes en un vector para que cuando se abra una ventana donde se ven las ordenes
            //para que puedan ver todos los pedidos que se han hecho
            pedidos.add(pedido);
        
            //se limpian las variables
            pedido.getBebidas().clear();
            bebidas.clear();
            
            //se genera un nueva codigo para el siguiente pedido
            pedido.setCodigo(pedido.getCodigo()+1);
        }
        else{
            //alerta si la lista de pedidos esta vacia, por ende no se puede hacer la orden
            Alert2.showAlert("Error", "No hay pedidos en la orden", Alert.AlertType.ERROR);
        }
    }
         
    
}
