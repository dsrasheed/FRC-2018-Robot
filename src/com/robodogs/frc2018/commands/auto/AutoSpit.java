package com.robodogs.frc2018.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import com.robodogs.frc2018.commands.Spit;
import com.robodogs.frc2018.Robot;

public class AutoSpit extends Spit {
    
    private double duration;
    private double startTime;
    
    public AutoSpit(double duration) {
        this(duration,1.0);
    }
    
    public AutoSpit(double duration, double speed) {
        super(speed);
        this.duration = duration;
    }

    protected void initialize() {
        super.initialize();
        startTime = Timer.getFPGATimestamp();
    }

    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime >= duration;
    }
}
