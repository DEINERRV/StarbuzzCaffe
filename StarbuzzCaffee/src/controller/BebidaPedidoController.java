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
import javafx.scene.control.CheckBox;
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
import model.BebidaCant;
import model.Beverage;
import model.CondimentDecorator;
import model.DarkRoast;
import model.Decaf;
import model.Espresso;
import model.HouseBlend;
import model.Milk;
import model.Mocha;
import model.Pedido;
import model.Soy;
import model.Whip;

/**
 * FXML Controller class
 *
 * @author deine
 */
public class BebidaPedidoController implements Initializable {

    //Tabla Bebidas
    @FXML
    private TableView<BebidaCant> tblBebidas;
    @FXML
    private TableColumn<BebidaCant, String> colnom;
    @FXML
    private TableColumn<BebidaCant, String> colPrecio;
    @FXML
    private TableColumn<?, ?> colCant;
    
    //Seleccion Bebida Base
    @FXML
    private MenuButton btnBebida;
    @FXML
    private MenuItem btnb1;
    @FXML
    private MenuItem btnb2;
    @FXML
    private MenuItem btnb3;
    @FXML
    private MenuItem btnb4;
    
    //Extras
    @FXML
    private CheckBox checkMilk;
    @FXML
    private CheckBox checkMocha;
    @FXML
    private CheckBox checkSoy;
    @FXML
    private CheckBox checkWhip;
    @FXML
    private TextField txtCant;
    
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnCancelarPedido;
    @FXML
    private Button btnRealizarModif;
    @FXML
    private Button btnElim;
    
    @FXML
    private Button btnModif;
    @FXML
    private Button btnCancelarModif;
    @FXML
    private Button btnVerPedidos;
    @FXML
    private Button btnRealizarPedido;
     
    //Objetos de apoyo
    private ObservableList<BebidaCant> bebidas;
    private Pedido pedido;
    private BebidaCant bebidaSelect = null;
    private BebidaCant bebidaModif = null;
    private Beverage bebidaBase = null;
    private Vector<Pedidos> pedidosControllers = new Vector();
    private ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
   
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pedido = new Pedido("1");
        
        bebidas = FXCollections.observableArrayList();
        
        this.tblBebidas.setItems(bebidas);
        this.colnom.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getBebida().getDescription()));    
        this.colPrecio.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getBebida().cost()+""));
        this.colCant.setCellValueFactory(new PropertyValueFactory("cantidad"));
       
        this.btnCancelarModif.setVisible(false);
        this.btnRealizarModif.setVisible(false);
    }    

    
    public void limpiarVariables(){
        //se reestablecen los valores de la parte donde se seleccionan 
        //las caracteristicasde la bebida
        txtCant.clear();
        btnBebida.setText("Select a beverage");
        bebidaBase = null;
        
        checkMilk.setSelected(false);
        checkMocha.setSelected(false);
        checkSoy.setSelected(false);
        checkWhip.setSelected(false);
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
        
        
        CondimentDecorator nueva = null;
        
        if(checkMocha.isSelected()){
            if(flagfirst == 0){
                nueva = new Mocha(bebidaBase);
                flagfirst = 1;
            }
            else{
                nueva = new Mocha(nueva);
            }
        }
        
        if(checkSoy.isSelected()){
            if(flagfirst == 0){
                nueva = new Soy(bebidaBase);
                flagfirst = 1;
            }
            else{
                nueva = new Soy(nueva);
            }
        }
        
        if(checkMilk.isSelected()){
            if(flagfirst == 0){
                nueva = new Milk(bebidaBase);
                flagfirst = 1;
            }
            else{
                nueva = new Milk(nueva);
            }
        }
        
            
        
        if(checkWhip.isSelected()){
            if(flagfirst == 0){
                nueva = new Whip(bebidaBase);
                flagfirst = 1;
            }
            else{
                nueva = new Whip(nueva);
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
    private void seleccionar(MouseEvent event) {
        bebidaSelect = this.tblBebidas.getSelectionModel().getSelectedItem();
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
    private void bebida1(ActionEvent event) {
        bebidaBase = new HouseBlend();
        this.btnBebida.setText(bebidaBase.getDescription());
    }

    @FXML
    private void bebida2(ActionEvent event) {
        bebidaBase = new DarkRoast();
        this.btnBebida.setText(bebidaBase.getDescription());
    }

    @FXML
    private void bebida3(ActionEvent event) {
        bebidaBase = new Espresso();
        this.btnBebida.setText(bebidaBase.getDescription());
    }

    @FXML
    private void bebida4(ActionEvent event) {
        bebidaBase = new Decaf();
        this.btnBebida.setText(bebidaBase.getDescription());
    }

    
    @FXML
    private void modificar(ActionEvent event) {
        bebidaModif = bebidaSelect;
        
        if(bebidaModif != null){//si selecciono una bebida de la tabla
            
            //se le da un valor ya que puede ser que no entre al if, lo que significario que no tiene extras
            Beverage bebidaBaseAux = bebidaModif.getBebida();
            
            //si la bebida seleccionada es un decorador(tiene extras)
            if(bebidaModif.getBebida() instanceof CondimentDecorator){
                
                //se realiza un castio para poder tener acceder a las funciones de Decorador
                CondimentDecorator aux = (CondimentDecorator) bebidaModif.getBebida();
                
                //ayudara a saber cuando la siguiente bebida del Decorador no es un Decorador y parar el ciclo
                int flag = 1;
                
                //ciclo para seleccionar los extras de la bebida
                while(flag==1){
                    if(aux instanceof Whip){
                        checkWhip.setSelected(true);
                        aux = (Whip)aux;
                    }else if(aux instanceof Soy){
                        checkSoy.setSelected(true);
                        aux = (Soy)aux;
                    }else if(aux instanceof Mocha){
                        checkMocha.setSelected(true);
                        aux = (Mocha)aux;
                    }else if(aux instanceof Milk){
                        checkMilk.setSelected(true);
                        aux = (Milk)aux;
                    }
                    
                    if(aux.getBeverage() instanceof CondimentDecorator)//si es un Decorador el ciclo continua
                        aux = (CondimentDecorator) aux.getBeverage();
                    else//de lo contrario significa que hemos llegado a la bebida Base y detiene el ciclo
                        flag = 0;
                    
                }
                
                //se le asigna la bebida Base
                bebidaBaseAux = aux.getBeverage();  
            }
        
            //se asignan los valores correspondientes al resto de campos
            txtCant.setText(bebidaModif.getCantidad()+"");
            btnBebida.setText(bebidaBaseAux.getDescription());
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
    private void realizarModif(ActionEvent event) {
        try{
            //se crea una bebida con las medificaciones 
            BebidaCant bebida = this.creaBebida();
        
            //se modifican los atributos de la bebida seleccionada
            bebidaModif.setBebida(bebida.getBebida());
            bebidaModif.setCantidad(bebida.getCantidad());
            
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
    private void eliminar(ActionEvent event) {
        if(bebidaSelect != null){
            if(bebidaSelect == bebidaModif){
                bebidaModif = null;
                this.CancelarModif(event);
            }
            bebidas.remove(bebidaSelect);
            bebidaSelect = null;
            
        }else{
            Alert2.showAlert("Error","No order selected", Alert.AlertType.ERROR);
        }
    }
    
    
    @FXML
    private void realizarPedido(ActionEvent event) {
        
        if(!bebidas.isEmpty()){//si la orden tiene pedidos
            
            //se agragan los pedido
            for(BebidaCant b: bebidas){
                pedido.agragar(b.getBebida(),b.getCantidad());
            }
            
            //se almacenan todas las ordenes en un vector para que cuando se abra una ventana donde se ven las ordenes
            //para que puedan ver todos los pedidos que se han hecho
            pedidos.add(pedido);
        
            //se le comunica la orden a las ventanas donde se ven las ordenes que esten abiertas
            for(Pedidos c: pedidosControllers){
               c.refrescarTabla();
            }
            
            //se limpian las variables
            pedido = new Pedido("1");
            bebidas.clear();
            
            //se genera un nueva codigo para el siguiente pedido
            pedido.setCodigo(pedido.getCodigo()+1);
        }
        else{
            //alerta si la lista de pedidos esta vacia, por ende no se puede hacer la orden
            Alert2.showAlert("ERROR", "No beverage in the order", Alert.AlertType.ERROR);
        }
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
            //se la tabla se actulize apenas se realize
            Pedidos controller = loader.getController();
            this.pedidosControllers.add(controller);
            
            //se le asigna la lista de pedidos para que tenga todos los pedidos hechos 
            controller.setPedidos(pedidos);
            
          
            //se abre la ventana
            stage.show();
            
            //cuando la ventan donde se ven los pedidos se cierra, entonces
            //se va a ejecutar esta accion, que elimina su contralador de la lista
            stage.setOnCloseRequest(ActionEvents -> this.elimObs(controller));
            
        }
        catch(Exception e){
                    
        }
    }

    public void elimObs(Pedidos p){
        if(this.pedidosControllers.contains(p)){
            this.pedidosControllers.remove(p);
        }
    }
    
         
    
}
