package com.robodogs.lib.util;

public interface PIDTunable {
    public void onPIDChange(double p, double i, double d);
    public void setSetpoint(double setpoint);
    public double getError();
    public void start();
    public void stop();
}
