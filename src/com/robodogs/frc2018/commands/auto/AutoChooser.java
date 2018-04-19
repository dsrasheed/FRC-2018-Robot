package com.robodogs.frc2018.commands.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.command.Command;

public class AutoChooser {
    
    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';
    public static final char MIDDLE = 'M';
    
    private SendableChooser<String> preferenceChooser;
    private SendableChooser<Character> robotPosChooser;
    private SendableChooser<Boolean> canCrossChooser;
    private SendableChooser<Boolean> matchChooser;

    public AutoChooser() {
        preferenceChooser = new SendableChooser<>();
        robotPosChooser = new SendableChooser<>();
        canCrossChooser = new SendableChooser<>();
        matchChooser = new SendableChooser<>();
    }
    
    public void setupSmartDashboard() {
        preferenceChooser.addDefault("Switch", "Switch");
        preferenceChooser.addObject("Scale", "Scale");
        //preferenceChooser.addObject("Both", "Both");
        preferenceChooser.addObject("Drive Past Line", "DPL");
        preferenceChooser.addObject("Do Nothing", "DN");
        
        robotPosChooser.addDefault("Left", LEFT);
        robotPosChooser.addObject("Right", RIGHT);
        robotPosChooser.addObject("Middle", MIDDLE);
        
        canCrossChooser.addDefault("Cannot Cross", false);
        canCrossChooser.addObject("Can Cross", true);
        
        matchChooser.addDefault("In Match", true);
        matchChooser.addObject("Debugging", false);

        SmartDashboard.putData("Auto Preference", preferenceChooser);
        SmartDashboard.putData("Robot Position", robotPosChooser);
        SmartDashboard.putData("Can Cross Over to Other Side", canCrossChooser);
        SmartDashboard.putData("Match Mode", matchChooser);
    }
    
   public Command getAuto() {
       String gameData = DriverStation.getInstance().getGameSpecificMessage();
       
       String preference = preferenceChooser.getSelected();
       char robotPos = robotPosChooser.getSelected();
       boolean canCross = canCrossChooser.getSelected();
       boolean inMatch = matchChooser.getSelected();
       
       SmartDashboard.putString("Game Data", gameData);
       SmartDashboard.putString("Selected Robot Position", new Character(robotPos).toString());
       SmartDashboard.putBoolean("Selected Can Cross", canCross);
       
       if (preference.equals("DPL"))
           return new DrivePastLine();
       
       if (preference.equals("DN"))
           return null;
       
       canCross = false;
       
       if (robotPos == MIDDLE)
           return new SwitchAuto(false, robotPos, gameData.charAt(0),inMatch);
       
       if (robotPos == gameData.charAt(0) && robotPos == gameData.charAt(1))
           if (preference.equals("Both"))
               return new SwitchAndScaleAuto();
       
       /* remove the && preference part.
       if (robotPos == gameData.charAt(1) && preference.equals("Scale"))
           return new ScaleAuto(false, robotPos);*/
       
       if (robotPos == gameData.charAt(0))
           return new SwitchAuto(false, robotPos, gameData.charAt(0),inMatch);
       
       if (canCross) {
           if (preference.equals("Both") || preference.equals("Scale"))
               return new ScaleAuto(true,robotPos);
           return new SwitchAuto(true,robotPos,gameData.charAt(0),inMatch);
       }
       return new DrivePastLine();
   }
}
