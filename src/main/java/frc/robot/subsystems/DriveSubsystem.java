// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {}
  public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {}
  public void rotate(double aaa) {}
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
