// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {}
  //dummy functions, do not merge
  public Rotation2d getGyroAngle() {
    return Rotation2d.fromDegrees(0);
  }

  public SwerveModulePosition[] getModulePositions() {
    return new SwerveModulePosition[] {
      new SwerveModulePosition(0, Rotation2d.fromDegrees(0)),
      new SwerveModulePosition(0, Rotation2d.fromDegrees(0)),
      new SwerveModulePosition(0, Rotation2d.fromDegrees(0)),
      new SwerveModulePosition(0, Rotation2d.fromDegrees(0))
    };
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
