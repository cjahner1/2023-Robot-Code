// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SecondaryVisionSubsystem extends SubsystemBase {
  private NetworkTable table;
  private StringSubscriber color;
  private IntegerSubscriber orientation;
  /** Creates a new SecondaryVisionSubsystem. */
  public SecondaryVisionSubsystem() {
    table = NetworkTableInstance.getDefault().getTable("camera");
    color = table.getStringTopic("color").subscribe("error");
    //cone orientation is 0 for tip away, 1 for tip right, 2 for tip close, 3 for tip left, 4 for upright, -1 for error
    //Note: all orientations may not be used
    orientation = table.getIntegerTopic("orientation").subscribe(-1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }

  public String getColor() {
    return color.get();
  }

  public boolean isYellow() {
    return this.color.get().equals("yellow");
  }

  public boolean isPurple() {
    return this.color.get().equals("purple");
  }

  public int getOrientation() {
    return (int) orientation.get();
  }
}
