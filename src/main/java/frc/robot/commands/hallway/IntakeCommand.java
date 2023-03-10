// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hallway;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HallwaySubsystem;

public class IntakeCommand extends CommandBase {
  private HallwaySubsystem hallwaySubsystem;
  private String color; //todo enums
  /** Creates a new IntakeCommand. */

  public IntakeCommand(HallwaySubsystem hallwaySubsystem, String _color, int orientation) {
    this.hallwaySubsystem = hallwaySubsystem;
    color = _color;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(hallwaySubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(color == "yellow") {
      hallwaySubsystem.setIntake(0.2);
    } else if(color == "purple") {
      hallwaySubsystem.setIntake(-0.2);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hallwaySubsystem.setIntake(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
