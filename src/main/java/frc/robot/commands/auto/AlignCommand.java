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
  private int height;
  /** Aligns the robot to selected goal using pose esitmation */
  public AlignCommand(PhotonVisionSubsystem _vision, DriveSubsystem _drive, String _goal, int _height) {
    vision = _vision;
    drive = _drive;
    goal = _goal;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
        
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
