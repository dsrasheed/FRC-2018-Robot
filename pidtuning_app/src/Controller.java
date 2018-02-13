import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

public class Controller {

    private static final String kTableName = "pid_tuning";
    
    @FXML private TextField pField;
    @FXML private TextField iField;
    @FXML private TextField dField;
    
    private double prevP;
    private double prevI;
    private double prevD;
    
    private NetworkTable pidTable;
    
    public Controller() {
        prevP = 0;
        prevI = 0;
        prevD = 0;
       
        NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
        ntInstance.startClient("10.21.71.2");
        pidTable = ntInstance.getTable(kTableName);
    }
    
    @FXML protected void sendData() {
        try {
            double p = Double.parseDouble(pField.getText());
            double i = Double.parseDouble(iField.getText());
            double d = Double.parseDouble(dField.getText());
            
            
        // TODO:implement custom NumberField to reject any characters other than
        // '.' and '0'-'9'.
        } catch (Exception e) {
            System.err.println("Parsing Error");
        }
    }
}
