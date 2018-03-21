package com.robodogs.frc2018.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.robodogs.frc2018.commands.auto.FollowPath;
import com.robodogs.frc2018.commands.auto.AutoSpit;


public class PlaceCubeOnSwitch extends CommandGroup {
    
    public static final String LEFT = "Left";
    public static final String MIDDLE = "Middle";
    public static final String RIGHT = "Right";
   
    private FollowPath switchPath = new FollowPath();

    public PlaceCubeOnSwitch() {
        addSequential(new FollowPath());
        addSequential(new AutoSpit(2.0));
    }
    
    public String getMotionProfileName(String botPos, char switchPos) {
        botPos = MIDDLE;
        return "Switch" + "-" + botPos + "-" + switchPos;
    }
    
    public void setProfile(String trajName) {
        switchPath.setTrajectory(trajName);
    }
}
