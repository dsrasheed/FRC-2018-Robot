package com.robodogs.frc2018.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.robodogs.frc2018.Constants;
import com.robodogs.frc2018.Robot;

public class Claw extends Subsystem {
    
    private TalonSRX master;
    private TalonSRX slave;
    private DigitalInput limit;
    private DoubleSolenoid left;
    private DoubleSolenoid right;

    public Claw() {
        master = new TalonSRX(Constants.Claw.kMasterCANID);
        slave = new TalonSRX(Constants.Claw.kSlaveCANID);
        
        master.configContinuousCurrentLimit(18, 0);
        slave.configContinuousCurrentLimit(18, 0);
        master.configPeakCurrentLimit(0, 0);
        slave.configPeakCurrentLimit(0, 0);
        master.configPeakCurrentDuration(0, 0);
        slave.configPeakCurrentDuration(0, 0);
        master.enableCurrentLimit(true);
        slave.enableCurrentLimit(true);

        master.setNeutralMode(NeutralMode.Brake);
        slave.setNeutralMode(NeutralMode.Brake);
        
        slave.set(ControlMode.Follower, Constants.Claw.kMasterCANID);
        
        limit = new DigitalInput(Constants.Claw.kLimitPort);
        
        left = new DoubleSolenoid(Constants.Claw.kLeftPistonForwardPort, Constants.Claw.kLeftPistonReversePort);
        right = new DoubleSolenoid(Constants.Claw.kRightPistonForwardPort, Constants.Claw.kRightPistonReversePort);
    }
    
    public void suck() {
        if (!hasCube())
            master.set(ControlMode.PercentOutput, 1.0);
    }
    
    public void spit() {
        master.set(ControlMode.PercentOutput,  -1.0);
    }
    
    public void stop() {
        master.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void lift() {
        setClawPosition(DoubleSolenoid.Value.kReverse);
    }
    
    public void right() {
        setClawPosition(DoubleSolenoid.Value.kForward);
    }
    
    private void setClawPosition(DoubleSolenoid.Value value) {
        left.set(value);
        right.set(value);
    }
    
    public void lower() {
        
    }
    
    public boolean hasCube() {
        //return limit.get();
        return false;
    }
     
    public void initDefaultCommand() {
        lift();
    }
}
