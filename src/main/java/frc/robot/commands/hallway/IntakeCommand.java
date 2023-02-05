// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hallway;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.SecondaryVisionSubsystem;
import frc.robot.utils.GamePiece;

public class IntakeCommand extends CommandBase {
  private HallwaySubsystem hallwaySubsystem;
  private Supplier<GamePiece> gamePiece;
  /** Creates a new IntakeCommand. */

  public IntakeCommand(HallwaySubsystem hallwaySubsystem, SecondaryVisionSubsystem secondaryVisionSubsystem) {
    this.hallwaySubsystem = hallwaySubsystem;

    this.gamePiece = secondaryVisionSubsystem::getGamePiece;

    addRequirements(hallwaySubsystem, secondaryVisionSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //TODO: add logic for intake orientation
    if(gamePiece.get() == GamePiece.CONE) {
      hallwaySubsystem.setIntake(0.2);
    } 
    else if(gamePiece.get() == GamePiece.CUBE) {
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
