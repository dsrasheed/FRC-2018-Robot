package com.robodogs.lib.motion;

import java.util.Scanner;
import java.io.IOException;
import java.io.File;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;

import edu.wpi.first.wpilibj.DriverStation;


public class TrajectoryDeserializer {
    
    File file;
    
    public TrajectoryDeserializer(String filename) {
        this(new File(filename));
    }
    
    public TrajectoryDeserializer(File file) {
        this.file = file;
    }
    
    public TrajectoryPoint[] deserialize(double posConversion, double velConversion) {
        TrajectoryPoint[] points = null;
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            int cnt = sc.nextInt();
        
            // next token is a blank line, so skip it
            sc.nextLine();
        
            points = new TrajectoryPoint[cnt];
            for (int i = 0; i < cnt; i++) {
                String[] items = sc.nextLine().split(",");
                TrajectoryPoint point = new TrajectoryPoint();
            
                point.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;
                point.position = Double.parseDouble(items[3]) * posConversion; // m to units
                point.velocity = Double.parseDouble(items[4]) * velConversion; // m/s to units
                point.profileSlotSelect0 = 0;
                point.profileSlotSelect1 = 0;
                point.headingDeg = 0;
            
                point.zeroPos = false;
                if (i == 0)
                    point.zeroPos = true;
            
                point.isLastPoint = false;
                if (i == cnt - 1)
                    point.isLastPoint = true;
            
                points[i] = point;
            }
        } catch (Exception e) {
            DriverStation.reportError("Something went wrong while deserializing a trajectory", false);
        }
        finally {
            if (sc != null)
                sc.close();
        }
        return points;
    }

}
