import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

public class Controller {

    private static final String kTableName = "pid_tuning";
    
    @FXML private BorderPane parent;
    @FXML private TextField pField;
    @FXML private TextField iField;
    @FXML private TextField dField;
    @FXML private TextField nameField;
    
    private String entryName;
    private double prevP;
    private double prevI;
    private double prevD;
    private NetworkTable pidTable;
    
    @FXML private void initialize() {
        createGraph();
        
        prevP = 0;
        prevI = 0;
        prevD = 0;
       
        NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
        ntInstance.startClient("10.21.71.22");
        pidTable = ntInstance.getTable(kTableName);
    }
    
    @FXML protected void setEntryName() {
        entryName = nameField.getText();
        if (!entryName.equals("")) {
            pField.setText(new Double(pidTable.getEntry(entryName + "_p").getDouble(0.0)).toString());
            iField.setText(new Double(pidTable.getEntry(entryName + "_i").getDouble(0.0)).toString());
            dField.setText(new Double(pidTable.getEntry(entryName + "_d").getDouble(0.0)).toString());
        }
    }
    
    @FXML protected void sendData() {
        double p = 0, i = 0, d = 0;
        try {
            p = Double.parseDouble(pField.getText());
            i = Double.parseDouble(iField.getText());
            d = Double.parseDouble(dField.getText());
            
        // TODO:implement custom NumberField to reject any characters other than
        // '.' and '0'-'9'.
        } catch (Exception e) {
            System.err.println("Parsing Error");
            return;
        }
        
        if (!entryName.equals("")) {
            if (p != prevP)
                pidTable.getEntry(entryName + "_p").setDouble(p);
            if (i != prevI)
                pidTable.getEntry(entryName + "_i").setDouble(i);
            if (d != prevD)
                pidTable.getEntry(entryName + "_d").setDouble(d);
        }

        prevP = p;
        prevI = i;
        prevD = d;
    }
    
    private void createGraph() {
        
    }
}
