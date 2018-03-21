import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class PIDTuningApp extends Application {
       
       @Override
       public void start(Stage stage) throws Exception {
           Parent root = FXMLLoader.load(getClass().getResource("ui.fxml"));
           Scene scene = new Scene(root, 1500, 1000);
           stage.setScene(scene);
		   stage.setResizable(true);
           stage.show();
       }
  
       public static void main(String[] args) {
           launch(args);
       }
}
