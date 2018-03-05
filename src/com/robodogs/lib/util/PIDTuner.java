package com.robodogs.lib.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.EntryListenerFlags;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;

import com.robodogs.lib.loops.Loop;
import com.robodogs.lib.loops.Looper;

public class PIDTuner {
    
    private static final double kDefaultTuningPeriod = 20.0; // 20 times per second
    
    private static NetworkTable pidTable = NetworkTableInstance.getDefault().getTable("pid_tuning");
    
    // TODO: Change to double array to reduce repeat code
    private PIDTunable tunable;
    private String name;
    private double p;
    private double i;
    private double d;
    
    private Looper tuningLooper;
    private Loop tuningLoop = new Loop() {
        
        private double startTime;
        
        @Override
        public void onStart() {
            tunable.start();
            startTime = Timer.getFPGATimestamp();
        }
        
        @Override
        public void onLoop() {
            double currTime = Timer.getFPGATimestamp();
            double timestamp = currTime - startTime;
            pidTable.getEntry(name + "_error").setDoubleArray(new double[] {
                timestamp, tunable.getError()
            });
        }
        
        @Override
        public void onStop() {
            tunable.stop();
        }
    };
    
    public PIDTuner(PIDController pidCtrl, String name) {
        this(new TunablePIDController(pidCtrl), name, pidCtrl.getP(), pidCtrl.getI(), pidCtrl.getD());
    }
    
    public PIDTuner(PIDTunable tunable, String name, double initP, double initI, double initD) {
        this(tunable, name, initP, initI, initD, kDefaultTuningPeriod);
    }
    
    public PIDTuner(PIDTunable tunable, String name, double initP, double initI, double initD, double dt) {
        this.tunable = tunable;
        this.name = name;
        this.p = initP;
        this.i = initI;
        this.d = initD;
        tuningLooper = new Looper(dt);
        tuningLooper.register(tuningLoop);
        
        initPIDListeners();
        
        NetworkTableEntry enabledEntry = pidTable.getEntry(name + "_enabled");
        NetworkTableEntry setpointEntry = pidTable.getEntry(name + "_setpoint");
        
        enabledEntry.addListener(event -> {
            if (event.value.getBoolean())
                tuningLooper.start();
            else
                tuningLooper.stop();
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        
        setpointEntry.addListener(event -> {
            tunable.setSetpoint(event.value.getDouble());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }
    
    private void initPIDListeners() {
        NetworkTableEntry pEntry = pidTable.getEntry(name + "_p");
        NetworkTableEntry iEntry = pidTable.getEntry(name + "_i");
        NetworkTableEntry dEntry = pidTable.getEntry(name + "_d");
        
        // Send the initial PID values so they don't need to be
        // manually set up on the PC app
        pEntry.setDouble(p);
        iEntry.setDouble(i);
        dEntry.setDouble(d);
        
        pEntry.addListener(event -> {
            synchronized(this) {
                p = event.value.getDouble();
                tunable.onPIDChange(p, i, d);
            }
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        
        iEntry.addListener(event -> {
            synchronized(this) {
                i = event.value.getDouble();
                tunable.onPIDChange(p, i, d);
            }
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        
        iEntry.addListener(event -> {
            synchronized(this) {
                d = event.value.getDouble();
                tunable.onPIDChange(p, i, d);
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
}
