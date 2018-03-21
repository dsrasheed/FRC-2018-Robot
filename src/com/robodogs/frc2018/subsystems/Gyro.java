package com.robodogs.frc2018.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Gyro extends Subsystem implements PIDSource {
    
    private AHRS ahrs;

    public Gyro() {
        try {
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error setting up navx: " + ex.getMessage(), true);
        }
    }
    
    public double getAngle() {
        return ahrs.getAngle();
    }
    
    public void reset() {
        ahrs.reset();
    }
    
    public double pidGet() {
        return ahrs.getAngle();
    }
    

    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }
    
    public void setPIDSourceType(PIDSourceType type) {}
    
    public void outputToSmartDashboard() {
        SmartDashboard.putBoolean("Gyro Is Calibrating", ahrs.isCalibrating());
        SmartDashboard.putBoolean("Gyro Is Connected", ahrs.isConnected());
        SmartDashboard.putNumber("Gyro Byte Count", ahrs.getByteCount());
        SmartDashboard.putNumber("Gyro Update Count", ahrs.getUpdateCount());
        SmartDashboard.putNumber("Gyro Angle: ", ahrs.getAngle());
    }
    
    public void initDefaultCommand() {}
}

