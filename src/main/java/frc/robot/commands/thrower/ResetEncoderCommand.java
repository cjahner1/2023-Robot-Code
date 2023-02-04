// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.thrower;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ThrowerSubsystem;

public class ResetEncoderCommand extends CommandBase {
  /** Creates a new ResetEncoderCommand. */
  ThrowerSubsystem throwerSubsystem;
  public ResetEncoderCommand(ThrowerSubsystem throwerSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.throwerSubsystem = throwerSubsystem;
    addRequirements(throwerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    throwerSubsystem.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
