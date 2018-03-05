import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Controller {

    private static final String kTableName = "pid_tuning";
    
    @FXML private BorderPane parent;
    @FXML private TextField nameField;
    @FXML private TextField pField;
    @FXML private TextField iField;
    @FXML private TextField dField;
    @FXML private TextField setpointField;
    @FXML private Button startBtn;
    
    private String entryName;
    private double prevP;
    private double prevI;
    private double prevD;
    private NetworkTable pidTable;
    
    private List<List<Double>> errorData;
    private String errorText;
    
    private Consumer<EntryNotification> errorListener = new Consumer<EntryNotification>() {
        @Override
        public void accept(EntryNotification event) {
            double timestamp = event.value.getDoubleArray()[0];
            double error = event.value.getDoubleArray()[1];
            System.out.println(timestamp + ": " + error);
            List<Double> data = new ArrayList<>();
            data.add(timestamp);
            data.add(error);
            errorData.add(data);
        }
    };
    private int errorListenerHandle;
    
    @FXML private void initialize() {
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
            prevP = pidTable.getEntry(entryName + "_p").getDouble(0.0);
            prevI = pidTable.getEntry(entryName + "_i").getDouble(0.0);
            prevD = pidTable.getEntry(entryName + "_d").getDouble(0.0);
            pField.setText(new Double(prevP).toString());
            iField.setText(new Double(prevI).toString());
            dField.setText(new Double(prevD).toString());
        }
    }
    
    @FXML protected void sendPIDData() {
        double p = 0, i = 0, d = 0;
        try {
            p = Double.parseDouble(pField.getText());
            i = Double.parseDouble(iField.getText());
            d = Double.parseDouble(dField.getText());
            
        // TODO:implement custom NumberField to reject any characters other than
        // '.' and '0'-'9'.
        } catch (Exception e) {
            System.err.println("PID Parsing Error");
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
    
    @FXML protected void sendSetpoint() {
        double setpoint = 0;
        try {
            setpoint = Double.parseDouble(setpointField.getText());
        } catch (Exception e) {
            System.err.println("Setpoint Parsing Error");
            return;
        }
        
        if (!entryName.equals("")) {
            pidTable.getEntry(entryName + "_setpoint").setDouble(setpoint);
        }
    }
    
    @FXML protected void toggleTuning() {
        NetworkTableEntry enabledEntry = pidTable.getEntry(entryName + "_enabled");
        boolean enabled = enabledEntry.getBoolean(false);
        if (!enabled) {
            // Add listener to update the graph when the mechanism reports its errors
            errorListenerHandle = pidTable.getEntry(entryName + "_error").addListener(errorListener, 
                    EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
            
            startBtn.getStyleClass().remove("green");
            startBtn.getStyleClass().add("red");
            startBtn.setText("Stop");
        
            errorData = new ArrayList<>();
            System.out.println("<<<<<<STARTING>>>>>>>");
        }
        else {
            pidTable.removeEntryListener(errorListenerHandle);
            
            startBtn.getStyleClass().remove("red");
            startBtn.getStyleClass().add("green");
            startBtn.setText("Start");
            System.out.println("<<<<<<STOPPING>>>>>>>");
            createGraph();
        }
        enabledEntry.setBoolean(!enabled);
    }

    private void createGraph() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Error");
        
        LineChart<Number,Number> errorChart = new LineChart<>(xAxis,yAxis);
        errorChart.setTitle(String.format("%f,%f,%f",prevP,prevI,prevD));
        
        XYChart.Series errorSeries = new XYChart.Series();
        for (List<Double> data : errorData) {
            errorSeries.getData().add(new XYChart.Data(data.get(0),data.get(1)));
        }
        errorChart.getData().add(errorSeries);
        errorChart.setCreateSymbols(false);
        parent.setCenter(errorChart);
    }
}

