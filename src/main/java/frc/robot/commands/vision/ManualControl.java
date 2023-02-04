// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.SecondaryVisionSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ManualControl extends CommandBase {
  SecondaryVisionSubsystem subsystem;
  String color;
  public ManualControl(SecondaryVisionSubsystem _subsystem, String _color) {
    // Use addRequirements() here to declare subsystem dependencies.
    subsystem = _subsystem;
    color = _color;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }
  @Override
  public void execute() {
    if(color != "yellow" && color != "purple") {
      return;
    }
    System.out.println("Color: " + color + "");
    if(color == "yellow") {
      subsystem.setYellow();
    } else if(color == "purple") {
      subsystem.setPurple();
    }
  }
}
