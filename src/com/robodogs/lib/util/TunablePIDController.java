package com.robodogs.lib.util;

import edu.wpi.first.wpilibj.PIDController;

public class TunablePIDController implements PIDTunable {
    private PIDController pidCtrl;
    public TunablePIDController(PIDController pidCtrl) {
        this.pidCtrl = pidCtrl;
    }
    public void onPIDChange(double p, double i, double d) {
        pidCtrl.setPID(p, i, d);
        pidCtrl.reset();
    }
    public void setSetpoint(double setpoint) {
        pidCtrl.setSetpoint(setpoint);
    }
    public double getError() {
        return pidCtrl.getError();
    }
    public void start() {
        pidCtrl.enable();
    }
    public void stop() {
        pidCtrl.disable();
    }
}
