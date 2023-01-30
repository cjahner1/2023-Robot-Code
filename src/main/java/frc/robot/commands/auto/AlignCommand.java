// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.PhotonVisionConstants;
import frc.robot.Constants.SwerveConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PhotonVisionSubsystem;
import frc.robot.utils.Goal;

public class AlignCommand extends CommandBase {
  private PhotonVisionSubsystem vision;
  private DriveSubsystem drive;
  private Goal targetGoal;

  /** Aligns the robot to selected goal using pose esitmation */
  public AlignCommand(PhotonVisionSubsystem _vision, DriveSubsystem _drive) {
    vision = _vision;
    drive = _drive;
  }
  
  //second constructor for when you want to specify the goal
  public AlignCommand(PhotonVisionSubsystem _vision, DriveSubsystem _drive, Goal _targetGoal) {
    vision = _vision;
    drive = _drive;
    targetGoal = _targetGoal;
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double closestDistance = 0;

    //Loop through available goals to make sure we are targeting the closest one
    for(Goal goal : PhotonVisionConstants.goals) {
      double x1 = goal.getScoringPose().getX();
      double y1 = goal.getScoringPose().getY();
      double x2 = vision.getCurrentPose().getX();
      double y2 = vision.getCurrentPose().getY();

      double distance = Math.hypot(x1-x2, y1-y2);

      if(distance < closestDistance) {
        closestDistance = distance;
        targetGoal = goal;
      }
    }

    //generate trajectory to goal
    Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
      vision.getCurrentPose(),
      List.of(new Translation2d(0, 0)), //TODO test that trajectory with no waypoints/blank waypoints works
      targetGoal.getScoringPose(),
      new TrajectoryConfig(AutoConstants.maxSpeedMetersPerSecond,
                          AutoConstants.maxAccelerationMetersPerSecondSquared)
          // Add kinematics to ensure max speed is actually obeyed
          .setKinematics(SwerveConstants.kinematics)
    );

    //TODO follow generated trajectory

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
