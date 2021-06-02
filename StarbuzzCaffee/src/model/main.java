package model;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(main.class.getResource("/view/LoginView.fxml")   );
            Pane ventana = (Pane) loader.load();
            
            Scene scene= new Scene(ventana);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("HOLA");
        launch(args);
    }
}
