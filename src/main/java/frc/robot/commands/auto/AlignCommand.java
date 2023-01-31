// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.PhotonVisionConstants;
import frc.robot.Constants.SwerveConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PhotonVisionSubsystem;
import frc.robot.utils.Goal;

public class AlignCommand extends SequentialCommandGroup {
  private PhotonVisionSubsystem vision;
  private DriveSubsystem drive;
  private Goal targetGoal;

  /** Aligns the robot to selected goal using pose esitmation */
  //second constructor for when you want to specify the goal
  public AlignCommand(PhotonVisionSubsystem _vision, DriveSubsystem _drive, Goal _targetGoal) {
    vision = _vision;
    drive = _drive;
    targetGoal = _targetGoal;

    double closestDistance = 0;

    //if no goal is specified, find the closest one
    if (targetGoal == null) {
      //use list of goals for our alliance (2 seperate lists because field isnt symmetrical)
      List<Goal> goals = DriverStation.getAlliance() == DriverStation.Alliance.Blue ? PhotonVisionConstants.blueGoals : PhotonVisionConstants.redGoals;

      //look for closest goal
      for(Goal goal : goals) {

        double distance = goal.getScoringPose().getTranslation().getDistance(vision.getCurrentPose().getTranslation());

        if(distance < closestDistance) {
          closestDistance = distance;
          targetGoal = goal;
        }
      }
    }

    //generate trajectory to goal
    Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
      vision.getCurrentPose(),
      List.of(), //TODO test that the straight line trajectory with no waypoints/blank waypoints works
      targetGoal.getScoringPose(),
      new TrajectoryConfig(AutoConstants.maxSpeedMetersPerSecond,
                          AutoConstants.maxAccelerationMetersPerSecondSquared)
          // Add kinematics to ensure max speed is actually obeyed
          .setKinematics(SwerveConstants.kinematics)
    );

    ProfiledPIDController thetaController =
            new ProfiledPIDController(
                AutoConstants.kPThetaController, 0, 0, AutoConstants.thetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

    SwerveControllerCommand swerveControllerCommand =
            new SwerveControllerCommand(
                trajectory,
                drive::getPose,
                SwerveConstants.kinematics,
                new PIDController(AutoConstants.kPXController, 0, 0), //TODO: Tune PID
                new PIDController(AutoConstants.kPYController, 0, 0),
                thetaController,
                drive::setModuleStates,
                drive);

    addCommands(
            new InstantCommand(() -> drive.resetOdometry(trajectory.getInitialPose())),
            swerveControllerCommand
        );
  }
  public AlignCommand(PhotonVisionSubsystem _vision, DriveSubsystem _drive) {
    this(_vision, _drive, null); 
  }
  
  

}
