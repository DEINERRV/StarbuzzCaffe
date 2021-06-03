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
    private TableColumn<?, ?> colnom;
    @FXML
    private TableColumn<?, ?> colPrecio;
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
        this.colnom.setCellValueFactory(new PropertyValueFactory("bebida"));
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("error");
            alert.setContentText("No se ha seleccionada ninguna orden(bebida)");
            alert.showAndWait();
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
        catch(NumberFormatException e){//si se escribieron letras
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Formato Incorrecto");
            alert.showAndWait();
        }
        catch(Exception e){//si el numero es menor o igual a 0
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        bebidaSelect = this.tblBebidas.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void modificar(ActionEvent event) {
        if(bebidaSelect != null){
            Bebida bebidaBaseAux = bebidaSelect.getBebida();
            
            if(bebidaSelect.getBebida() instanceof Decorador){
                Decorador aux = (Decorador) bebidaSelect.getBebida();
                int flag = 1;
                while(flag==1){
                    for(Decorador d: extras){
                        if(d.getNombre().equals(aux.getNombre()))
                            d.getCheckbox().setSelected(true);
                    }
                    if(aux.getBebida() instanceof Decorador)
                        aux = (Decorador) aux.getBebida();
                    else
                        flag = 0;
                    
                }
                
                bebidaBaseAux = aux.getBebida();  
            }
        
            txtCant.setText(bebidaSelect.getCantidad()+"");
            btnBebida.setText(bebidaBaseAux.getNombre());
            bebidaBase = bebidaBaseAux;
            
            btnAnadir.setVisible(false);
            btnRealizarModif.setVisible(true);
            btnCancelarModif.setVisible(true);
            
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("error");
            alert.setContentText("No se ha seleccionada ninguna orden(bebida)");
            alert.showAndWait();
        }
    }

    @FXML
    private void CancelarModif(ActionEvent event) {
        this.btnCancelarModif.setVisible(false);
        this.btnRealizarModif.setVisible(false);
        this.btnAnadir.setVisible(true);
        this.limpiarVariables();
    }

    @FXML
    private void CancelarPedido(ActionEvent event) {
        bebidas.clear();
        tblBebidas.refresh();
        this.limpiarVariables();
        btnCancelarModif.setVisible(false);
        btnRealizarModif.setVisible(false);
        btnAnadir.setVisible(true);
    }

    @FXML
    private void realizarModif(ActionEvent event) {
        try{
            BebidaCant bebida = this.creaBebida();
        
            bebidaSelect.setBebida(bebida.getBebida());
            bebidaSelect.setCantidad(bebida.getCantidad());
            this.tblBebidas.refresh();
            this.limpiarVariables();
            btnCancelarModif.setVisible(false);
            btnRealizarModif.setVisible(false);
            btnAnadir.setVisible(true);
        }
         catch(NumberFormatException e){//si se escribieron letras
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Formato Incorrecto");
            alert.showAndWait();
        }
        catch(Exception e){//si el numero es menor o igual a 0
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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
        
        //se obtiene la cantidad que se desea de una bebida del txtfield de la ventana
        int cant = Integer.parseInt(this.txtCant.getText());
            
        //si es menor a 0 se tira una excepcion ya que no se pueden pedir 0 o -11,por ejemplo, de una bebida
        if(cant <= 0){
            Exception e = new Exception("La cantidad es menor o igua a 0");
            throw e;
        }
       
        if(bebidaBase == null){
            Exception e = new Exception("No se ha Seleccionado la Bebida Base");
            System.out.print(e.getMessage());
            throw e;
        }
        
        int flagfirst = 0;
        
        Decorador aux = null;
        Decorador nueva = null;
        
        for(Decorador d : extras){
            if(d.getCheckbox().isSelected()){
                flagfirst++;
                if(flagfirst != 1){
                    nueva = new Decorador(aux,d.getNombre(),d.getPrecio());
                    aux = nueva;
                }
                else{
                    nueva = new Decorador(bebidaBase,d.getNombre(),d.getPrecio());
                    aux = nueva;
                }
            }
        }
        
        System.out.println(nueva.getDescripcion());
        //Se crean y annaden la bebida al pedido
        if(nueva != null)
            return new BebidaCant(nueva,cant);
        else if(bebidaBase != null)
            return new BebidaCant(bebidaBase,cant);
        
        return null;
    }

    @FXML
    private void verPedidos(ActionEvent event) {
        try{
            //Carga la View del blog de Notas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Pedidos.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            
            Pedidos controller = loader.getController();
            pedidosControllers.add(controller);
            
            for(Pedido p: pedidos){
                controller.agragarPedido(p);
            }
            
            stage.show();
            
        }
        catch(Exception e){
                    
        }
    }

    @FXML
    private void realizarPedido(ActionEvent event) {
        
        for(BebidaCant b: bebidas){
            pedido.agragar(b.getBebida(),b.getCantidad());
        }
        
        for(Pedidos c: pedidosControllers){
            c.agragarPedido(pedido);
        }
        
        pedidos.add(pedido);
        
        pedido.getBebidas().clear();
        pedido.setCodigo(pedido.getCodigo()+1);
        bebidas.clear();
    }
         
    
}
