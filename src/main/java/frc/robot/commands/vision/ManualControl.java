// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.SecondaryVisionSubsystem;
import frc.robot.utils.GamePiece;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ManualControl extends CommandBase {
  SecondaryVisionSubsystem subsystem;
  GamePiece gamePiece;
  public ManualControl(SecondaryVisionSubsystem subsystem, GamePiece gamePiece) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.subsystem = subsystem;
    this.gamePiece = gamePiece;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }
  @Override
  public void execute() {
    //return commmand if no color is passed to the command (could happen if manual mode is selected and the codriver doesn't ever select a color)
    if(gamePiece == GamePiece.NONE) {
      return;
    }

    if(gamePiece == GamePiece.CONE) {
      subsystem.setCone();
    } else if(gamePiece == GamePiece.CUBE) {
      subsystem.setCone();
    }
  }
}
