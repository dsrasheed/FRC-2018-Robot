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
    
    // TODO: Change to double array to reduce repeat code
    private double p;
    private double i;
    private double d;
    private String name;
    
    public static interface PIDChangeListener {
        public void onPIDChange(double p, double i, double d);
    }
    
    private static class PIDControllerUpdater implements PIDChangeListener {
        private PIDController pidCtrl;
        public PIDControllerUpdater(PIDController pidCtrl) {
            this.pidCtrl = pidCtrl;
        }
        public void onPIDChange(double p, double i, double d) {
            pidCtrl.setPID(p, i, d);
        }
    }
    
    public PIDTuner(PIDController pidCtrl, String name) {
        this(new PIDControllerUpdater(pidCtrl), name, pidCtrl.getP(), pidCtrl.getI(), pidCtrl.getD());
    }
    
    public PIDTuner(PIDChangeListener listener, String name, double initP, double initI, double initD) {
        this.name = name;
        pEntry = pidTable.getEntry(name + "_p");
        iEntry = pidTable.getEntry(name + "_i");
        dEntry = pidTable.getEntry(name + "_d");
        
        p = initP;
        i = initI;
        d = initD;
        pEntry.setDouble(p);
        iEntry.setDouble(i);
        dEntry.setDouble(d);
        
        pEntry.addListener(event -> {
            synchronized(this) {
                p = event.value.getDouble();
                listener.onPIDChange(p, i, d);
            }
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        
        iEntry.addListener(event -> {
            synchronized(this) {
                i = event.value.getDouble();
                listener.onPIDChange(p, i, d);
            }
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        
        iEntry.addListener(event -> {
            synchronized(this) {
                d = event.value.getDouble();
                listener.onPIDChange(p, i, d);
            }
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }
    
    public double getP() {
        synchronized(this) {
            return p;
        }
    }
    
    public double getI() {
        synchronized(this) {
            return i;
        }
    }
    
    public double getD() {
        synchronized(this) {
            return d;
        }
    }
    
    public void setSetpoint(double value) {
        pidTable.getEntry(name + "_sp").setDouble(value);
    }
}
