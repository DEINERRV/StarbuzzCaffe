/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.UserAdmin;
import model.Alert2;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author deine
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    
    //variables para el funcionamiento del login
    private UserAdmin users = new UserAdmin();
    int attempts = 3 ;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        users.getData();//recupera los usuarios del .txt y los almacena en su vector
    }    

    @FXML
    private void Login(ActionEvent event) {
        String user = this.txtUser.getText();
        String password = this.txtPassword.getText();
        
        if(user.isEmpty() || password.isEmpty()){
            Alert2.showAlert("Error", "all fields must be filled", Alert.AlertType.ERROR);
        }
        else{
            if(!users.find(user)){
                Alert2.showAlert("Error", "Username doesn't exist", Alert.AlertType.ERROR);
            }
            else if(!users.validate(user, password)){
                attempts --;
                
                //Alerta
                Alert2.showAlert("Error", "Wrong password, you have " + attempts +" attempts left", Alert.AlertType.ERROR);
            
                if(attempts == 0){
                    System.exit(0);
                }
                
            }
            else{
                try{
                    //Carga la View del blog de Notas
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BebidaPedido.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    
                    //Cierra la venta de Login
                    Stage stage2 = (Stage) this.btnLogin.getScene().getWindow();
                    stage2.close();
                    
                    //le pasa el control y se muestra
                    stage2.setScene(scene);
                    stage2.show();
                }
                catch(Exception e){
                    
                }
            }//fin else
        }
    }//fin Login
    
}//fin del controlador
