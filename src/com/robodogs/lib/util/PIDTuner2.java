package com.robodogs.lib.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;

/**
 * PIDTuner is a bridge to the PID Tuning App. You can poll
 * for changes sent from the application by calling the needed 'get' methods.
 */
public class PIDTuner2 {
        
    private static NetworkTable pidTable = NetworkTableInstance.getDefault().getTable("pid_tuning");
    
    // Used as a prefix for entries in the network table,
    // allows you to keep entries unique to each PIDTuner instance.
    private String name;
    
    // Maintains the current values to determine if
    // the application has sent a new one.
    private double p;
    private double i;
    private double d;
    private double setpoint;
    
    private boolean enabled;
    private double startTime;
    
    // Locks
    private Object pidLock = new Object();
    private Object enabledLock = new Object();
    
    private String getEntryName(String suffix) {
        return name + "_" + suffix;
    }
    
    public PIDTuner2(String name) {
        this.name = name;
        this.enabled = false;
    }
    
    public void enable() {
        synchronized(enabledLock) {
            if (!enabled) {
                startTime = Timer.getFPGATimestamp();
                enabled = true;
            }
        }
    }   
    
    public void disable() {
        synchronized(enabledLock) {
            if (enabled) {
                sendError(0.0, true);
                enabled = false;
            }
        }
    }
    
    public void sendError(double error) {
        sendError(error, false);
    }
    
    private void sendError(double error, boolean isLast) {
        synchronized(enabledLock) {
            if (enabled) {
                double now = Timer.getFPGATimestamp();
                double timestamp = now - startTime;
                double[] send = {
                       timestamp, error,
                       isLast ? 1.0 : 0.0
                };
                pidTable.getEntry(getEntryName("error")).setDoubleArray(send);
            }
        }
    }
    
    public boolean hasPChanged() {
        synchronized(pidLock) {
            return p != pidTable.getEntry(getEntryName("p")).getDouble(0.0);
        }
    }
    
    public boolean hasIChanged() {
        synchronized(pidLock) {
            return i != pidTable.getEntry(getEntryName("i")).getDouble(0.0);
        }
    }
    
    public boolean hasDChanged() {
        synchronized(pidLock) {
            return d != pidTable.getEntry(getEntryName("d")).getDouble(0.0);
        }
    }
    
    public boolean hasSetpointChanged() {
        synchronized(pidLock) {
            return setpoint != pidTable.getEntry(getEntryName("setpoint")).getDouble(0.0);
        }
    }
    
    public double getP() {
        synchronized(pidLock) {
            p = pidTable.getEntry(getEntryName("p")).getDouble(0.0);
            return p;
        }
    }
    
    public double getI() {
        synchronized(pidLock) {
            i = pidTable.getEntry(getEntryName("i")).getDouble(0.0);
            return i;
        }
    }
    
    public double getD() {
        synchronized(pidLock) {
            d = pidTable.getEntry(getEntryName("d")).getDouble(0.0);
            return d;
        }
    }
    
    public double getSetpoint() {
        synchronized(pidLock) {
            setpoint = pidTable.getEntry(getEntryName("setpoint")).getDouble(0.0);
            return setpoint;
        }
    }
}
