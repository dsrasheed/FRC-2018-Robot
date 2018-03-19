package com.robodogs.frc2018.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import com.robodogs.frc2018.commands.Spit;

public class AutoSpit extends Spit {
    
    private double duration;
    private double startTime;
    
    public AutoSpit(double duration) {
        super();
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
