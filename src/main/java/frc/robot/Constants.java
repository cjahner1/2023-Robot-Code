// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.utils.Direction;
import frc.robot.utils.Goal;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class SwerveConstants {
    public static SwerveDriveKinematics kinematics = new SwerveDriveKinematics(null, null, null, null);
  }

  public static class ThrowerConstants {
    
  }

  public static class HallwayConstants {
    
  }

  public static class AutoConstants {
    public static final double maxSpeedMetersPerSecond = 1.5;
    public static final double maxAccelerationMetersPerSecondSquared = 1.0;
    public static final double ramseteB = 2;
    public static final double ramseteZeta = 0.7;

    public static final double maxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double maxAngularSpeedRadiansPerSecondSquared = Math.PI;


    public static final double kPXController = 1; //TODO: Tune PID
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    /* Constraint for the motion profilied robot angle controller */
    public static final TrapezoidProfile.Constraints thetaControllerConstraints =
    new TrapezoidProfile.Constraints(
        maxAngularSpeedRadiansPerSecond, maxAngularSpeedRadiansPerSecondSquared);
    
  }

  public static class PhotonVisionConstants {
    public static Goal goal = new Goal(0, Direction.RIGHT, new Pose2d());
    public static List<Goal> blueGoals = List.of(goal);
    public static List<Goal> redGoals = List.of(goal);
  }

  public static class SecondaryVisionConstants {
    
  }
}
