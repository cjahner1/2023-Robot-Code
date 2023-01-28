// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PhotonVisionSubsystem;

public class AlignCommand extends CommandBase {
  private PhotonVisionSubsystem vision;
  private DriveSubsystem drive;
  private String goal;
  /** Aligns the robot to selected goal. 
   * Please note: this command makes the assumption that the robot has the AprilTag in range of its Limelight camera */
  public AlignCommand(PhotonVisionSubsystem _vision, DriveSubsystem _drive, String _goal) {
    vision = _vision;
    drive = _drive;
    goal = _goal;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double angleToTag = vision.angleToTag();
    double distanceToWall = vision.distanceToTag() + AutoConstants.tagWallForwardOffset;
    double wallCenterOffset = vision.wallCenterOffset();

    //rotate robot to be perpendicular with tag
    drive.rotate(angleToTag);

    //update wall distance and offset after having rotated
    distanceToWall = vision.distanceToTag() + AutoConstants.tagWallOffset;
    wallCenterOffset = vision.wallCenterOffset();

    double strafeDistanceToGoal = 0000; //TODO: get distances from center of tag to center of goals and allow for left, right or center to be chosen on command run

    //move robot to goal (replace with on the fly trajectory generation if cpu allows) 
    //- look into isOpenLoop of drive() and see is it is suitable replacement for traj
    drive.drive(
      new Translation2d(distanceToWall - Units.inchesToMeters(1), strafeDistanceToGoal - wallCenterOffset),
        0,
        false,
        false
        );
        
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
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
