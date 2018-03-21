package com.robodogs.frc2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import com.robodogs.frc2018.Constants;
import com.robodogs.frc2018.Robot;

public class Climber extends Subsystem {
    
    private DoubleSolenoid extender;
    private Talon winder;
    
    private boolean extended;
    
    public Climber() {
        winder = new Talon(Constants.Winder.kTalonPort);
        extender = new DoubleSolenoid(Constants.Winder.kForwardPort, Constants.Winder.kReversePort);
    }
    
    public void extend() {
        // Don't want hook to extend while arm is down
        if (!Robot.arm.canDescend())
            return;
        
        extender.set(DoubleSolenoid.Value.kForward);
        extended = true;
    }
    
    public void retract() {
        extender.set(DoubleSolenoid.Value.kReverse);
        extended = false;
    }
    
    public void wind() {
        winder.set(0.8);
    }
    
    public void unwind() {
        winder.set(-0.8);
    }
    
    public void stop() {
        winder.set(0.0);
    }
    
    public boolean isExtended() {
        return extended;
    }

    public void initDefaultCommand() {
        retract();
    }
}
