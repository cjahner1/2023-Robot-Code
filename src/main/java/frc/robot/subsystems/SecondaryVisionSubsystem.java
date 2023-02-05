// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.GamePiece;
import frc.robot.utils.Orientation;

public class SecondaryVisionSubsystem extends SubsystemBase {
  private NetworkTable table;
  private StringSubscriber color;
  private IntegerSubscriber orientation;
  private boolean manualControl;
  private boolean manualYellow;
  private boolean manualPurple;
  private ShuffleboardTab hallwayTab;
  private GenericEntry manualControlWidget;
  private GenericEntry isPurple;
  private GenericEntry isYellow;
  /** Creates a new SecondaryVisionSubsystem. */
  public SecondaryVisionSubsystem() {
    ShuffleboardTab tab = Shuffleboard.getTab("Settings");
    GenericEntry manualControlWidget = tab.add("Manual Control", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
    
    table = NetworkTableInstance.getDefault().getTable("camera");
    color = table.getStringTopic("color").subscribe("error");
    orientation = table.getIntegerTopic("orientation").subscribe(-1);

    manualYellow = false;
    manualPurple = false;
    
    hallwayTab = Shuffleboard.getTab("Hallwayy");
    hallwayTab.addBoolean("isYellow", this::isYellow);
    hallwayTab.addBoolean("isPurple", this::isPurple);

    this.manualControlWidget = manualControlWidget;
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("isPurple", isPurple());
    SmartDashboard.putBoolean("isYellow", isYellow());
  }

  public String getColor() {
    //TODO fix this
    return color.get();
  }

  public boolean manualControlEnabled() {
    //return manualControlWidget.getBoolean(false);
    return true;
  }

  public void setCone() {
    manualYellow = true;
    manualPurple = false;
    System.out.println("Set Yellow");
  }

  public void setCube() {
    manualYellow = false;
    manualPurple = true;
  }

  public GamePiece getGamePiece() {
    if(isYellow()) {
      return GamePiece.CONE;
    } else if(isPurple()) {
      return GamePiece.CUBE;
    } else {
      return GamePiece.NONE;
    }
  }
  
  public boolean isYellow() {
    System.out.println("Manual contrl: " + manualYellow);
    if(manualControlEnabled()) {
      System.out.println("Manual Control check");
      return manualYellow;
    } else {
      System.out.println("Manual Control not check");
      return this.color.get().equals("yellow");
    }   
  }

  public boolean isPurple() {
    if(manualControlEnabled()) {
      return manualPurple;
    } else {
      return this.color.get().equals("purple");
    } 
  }

  public Orientation getOrientation() {
    //return (int) orientation.get();
    return Orientation.BASE_FORWARD; //TODO manual control/fix
  }
}
