import javafx.application.Platform;
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
    private NetworkTable pidTable;
    
    @FXML private BorderPane parent;
    
    @FXML private TextField nameField;
    @FXML private Button nameBtn;
    private String tunerName;

    @FXML private TextField pField;
    @FXML private TextField iField;
    @FXML private TextField dField;
    @FXML private TextField setpointField;
    private double[] lastValues;
    private static final int kLastPIndex = 0;
    private static final int kLastIIndex= 1;
    private static final int kLastDIndex = 2;
    private static final int kLastSetpointIndex = 3;
    
    private int errorListenerHandle = -1;
    
    @FXML private void initialize() {
        NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
        ntInstance.startClient("10.21.71.22");
        pidTable = ntInstance.getTable(kTableName);
        
        tunerName = "";
        
        lastValues = new double[4];
    }
    
    @FXML protected void setTunerName() {
        String name = nameField.getText();
        if (!name.equals("")) {
            tunerName = name;
            
            nameField.setDisable(true);
            nameBtn.setDisable(true);
            
            errorListenerHandle = pidTable.getEntry(tunerName + "_error").addListener(errorListener, 
                    EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        }
    }
    
    @FXML protected void sendPID() {
        if (!tunerName.equals("")) {
            sendDouble(pField,kLastPIndex);
            sendDouble(iField,kLastIIndex);
            sendDouble(dField,kLastDIndex);
        }
    }
    
    @FXML protected void sendSetpoint() {
        if (!tunerName.equals(""))
            sendDouble(setpointField,kLastSetpointIndex);
    }
    
    private void sendDouble(TextField field, int index) {
        double value = 0;
        try {
            value = Double.parseDouble(field.getText());
        } catch (Exception e) {
            System.err.println("Error Parsing Text Field[id] = " + field.getId());
            return;
        }
        
        if (value != lastValues[index]) {
            pidTable.getEntry(tunerName + "_" + field.getId()).setDouble(value);
            lastValues[index] = value;
        }
    }
    
    private Consumer<EntryNotification> errorListener = new Consumer<EntryNotification>() {
        
        private List<List<Double>> errorData;
        private boolean firstError = true;
        
        @Override
        public void accept(EntryNotification event) {
            if (firstError) {
                errorData = new ArrayList<>();
                firstError = false;
                System.out.println("<<<<STARTED>>>>");
            }
            
            double isLastError = event.value.getDoubleArray()[2];
            if (isLastError == 1.0) {
                Platform.runLater( () -> {
                    createGraph(errorData);
                });
                firstError = true;
                System.out.println("<<<<STOPPED>>>>");
                return;
            }
                        
            double timestamp = event.value.getDoubleArray()[0];
            double error = event.value.getDoubleArray()[1];    
            System.out.println(timestamp + ": " + error);
            List<Double> data = new ArrayList<>();
            data.add(timestamp);
            data.add(error);
            errorData.add(data);
        }
    };
    
    private void createGraph(List<List<Double>> errorData) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Error");
        
        LineChart<Number,Number> errorChart = new LineChart<>(xAxis,yAxis);
        errorChart.setTitle(String.format("%f,%f,%f", lastValues[kLastPIndex], 
                lastValues[kLastIIndex], lastValues[kLastDIndex]));
        
        XYChart.Series errorSeries = new XYChart.Series();
        for (List<Double> data : errorData) {
            errorSeries.getData().add(new XYChart.Data(data.get(0),data.get(1)));
        }
        
        errorChart.getData().add(errorSeries);
        errorChart.setCreateSymbols(false);
        
        parent.setCenter(errorChart);
    }
}
