package com.robodogs.frc2018.triggers;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.Joystick;

public class AxisButton extends Trigger {
    
    private Joystick stick;
    private int axis;
    private double threshold;
    
    public AxisButton(Joystick stick, int axis, double threshold) {
        this.stick = stick;
        this.axis = axis;
        this.threshold = threshold;
    }

    public boolean get() {
        if (threshold < 0) {
            if (stick.getRawAxis(axis) <= threshold)
                return true;
        } else {
            if (stick.getRawAxis(axis) >= threshold)
                return true;
        }
        return false;
    }
}
