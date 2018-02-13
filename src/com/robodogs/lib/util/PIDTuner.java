package com.robodogs.lib.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.EntryListenerFlags;

import edu.wpi.first.wpilibj.PIDController;

public class PIDTuner {
    
    private static NetworkTable pidTable = NetworkTableInstance.getDefault().getTable("pid_tuning");
    
    private NetworkTableEntry pEntry;
    private NetworkTableEntry iEntry;
    private NetworkTableEntry dEntry;
    
    public PIDTuner(PIDController pidCtrl, String name, double initP, double initI, double initD) {
        pEntry = pidTable.getEntry(name + "_p");
        iEntry = pidTable.getEntry(name + "_i");
        dEntry = pidTable.getEntry(name + "_d");
        
        pEntry.setDouble(initP);
        iEntry.setDouble(initI);
        dEntry.setDouble(initD);
        
        pEntry.addListener(event -> {
            pidCtrl.setP(event.value.getDouble());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        
        iEntry.addListener(event -> {
            pidCtrl.setI(event.value.getDouble());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        
        dEntry.addListener(event -> {
            pidCtrl.setD(event.value.getDouble());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }
}
