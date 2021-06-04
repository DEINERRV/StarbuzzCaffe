/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Pedido;


public class Pedidos implements Initializable {

    @FXML
    private AnchorPane scenePane;
    
    //Tabla Pedidos
    @FXML
    private TableView<Pedido> tblPedidos;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colEdad;
    
    @FXML
    private TextField txtFiltroCodigo;
    
    //Filtro Estados
    @FXML
    private MenuButton filtroEstado;
    @FXML
    private MenuItem filtroListo;
    @FXML
    private MenuItem filtroEspera;
    @FXML
    private MenuItem filtroProceso;
    @FXML
    private MenuItem filtroTodos;
    
    //Botones Cambiar Estado
    @FXML
    private MenuButton cambiarEstado;
    @FXML
    private MenuItem cambiarEstadoListo;
    @FXML
    private MenuItem cambiarEstadoEspera;
    @FXML
    private MenuItem cambiarEstadoProceso;
    @FXML
    
    private TextArea txtDesc;
    
    //Objetos de apoya
    private ObservableList<Pedido> pedidos;
    private ObservableList<Pedido> filtroPedidos;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        pedidos = FXCollections.observableArrayList();
        filtroPedidos = FXCollections.observableArrayList();
        
        this.tblPedidos.setItems(pedidos);
        
        this.colNombre.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.colEdad.setCellValueFactory(new PropertyValueFactory("estado"));
        
    }    

    @FXML
    private void seleccionar(MouseEvent event) {
        Pedido aux = this.tblPedidos.getSelectionModel().getSelectedItem();
        
        if(aux!=null)
            txtDesc.setText(aux.getDescripcion());
        else
            txtDesc.clear();
    }

    @FXML
    private void filtrarNombre(KeyEvent event) {
            String codigo = this.txtFiltroCodigo.getText();
        
        if(codigo.isEmpty()){
            filtroPedidos.clear();
            this.tblPedidos.setItems(pedidos);
        }else{
            filtroPedidos.clear();
            
            for (Pedido p:this.pedidos)
                if( p.getCodigo().toLowerCase().contains(codigo.toLowerCase()) )
                    this.filtroPedidos.add(p);
            
            this.tblPedidos.setItems(filtroPedidos);
        }
    }


    @FXML
    private void filtrarListo(ActionEvent event) {
        this.filtroPedidos.clear();
        this.filtroPedidos = this.filtrarEstado("listo");
        this.tblPedidos.setItems(this.filtroPedidos);
        this.filtroEstado.setText("Listo");
    }

    @FXML
    private void filtrarEspera(ActionEvent event) {
        this.filtroPedidos.clear();
        this.filtroPedidos = this.filtrarEstado("espera");
        this.tblPedidos.setItems(this.filtroPedidos);
        this.filtroEstado.setText("Espera");
    }

    @FXML
    private void filtrarProceso(ActionEvent event) {
        this.filtroPedidos.clear();
        this.filtroPedidos = this.filtrarEstado("proceso");
        this.tblPedidos.setItems(this.filtroPedidos);
        this.filtroEstado.setText("Proceso");
    }

    @FXML
    private void filtrarTodo(ActionEvent event) {
        this.tblPedidos.setItems(pedidos);
        this.filtroEstado.setText("Todos");
    }
    
    private ObservableList<Pedido> filtrarEstado(String estado){
        ObservableList<Pedido> filtro = FXCollections.observableArrayList();
        
        for (Pedido p:this.pedidos)
            if( p.getEstado().toLowerCase().contains(estado.toLowerCase()) )
                filtro.add(p);
        
        return filtro;
    }

    @FXML
    private void cambiarEstadoListo(ActionEvent event) {
        this.cambiarEstado("listo");
    }

    @FXML
    private void cambiarEstadoEspera(ActionEvent event) {
        this.cambiarEstado("espera");
    }

    @FXML
    private void cambiarEstadoProceso(ActionEvent event) {
        this.cambiarEstado("proceso");
    }
    
    private void cambiarEstado(String estado){
        Pedido aux = this.tblPedidos.getSelectionModel().getSelectedItem();
        
        if(aux!=null){
            aux.setEstado(estado);
            this.tblPedidos.refresh();
            
            //si la modificacion no contiene el texto del filtor, entoces lo eliminamos de
            //los elemenots del array(filtroPersonas) y de esa manera no se muestra si el filtro
            //esta activo(con texto)
            if( !aux.getEstado().toLowerCase().contains(this.filtroEstado.getText().toLowerCase()) )
                this.filtroPedidos.remove(aux);
            
            
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("error");
            alert.setContentText("No se ha seleccionada ningun pedido");
            alert.showAndWait();
        }
    }
    

    public void setPedidos(ObservableList<Pedido> pedidos) {
        this.pedidos = pedidos;
        this.tblPedidos.setItems(pedidos);
        this.tblPedidos.refresh();
    }
    
    public void refrescarTabla(){
        if(this.filtroEstado.getText().equals("Proceso")){
            this.filtroPedidos = this.filtrarEstado("proceso");
            this.tblPedidos.setItems(this.filtroPedidos);
            for(Pedido p:this.filtroPedidos){
                System.out.print(p.calcPrecio());
            }
        }
        
        this.tblPedidos.refresh();
    }
    
}
