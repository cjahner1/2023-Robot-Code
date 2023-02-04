// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HallwaySubsystem extends SubsystemBase {
  private WPI_TalonFX intake1fx = new WPI_TalonFX(9);
  private WPI_TalonFX intake2fx = new WPI_TalonFX(10);
  private WPI_TalonFX flipper1fx = new WPI_TalonFX(11);
  private WPI_TalonFX flipper2fx = new WPI_TalonFX(12);

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("camera");
  private StringSubscriber color = table.getStringTopic("color").subscribe("error");
  private IntegerSubscriber orientation = table.getIntegerTopic("orientation").subscribe(-1);

  private static SendableChooser<Boolean> cameraChoose = new SendableChooser<>();

  /** Creates a new HallwaySubsystem. */
  public HallwaySubsystem() {
    intake1fx.configFactoryDefault();
    intake2fx.configFactoryDefault();
    flipper1fx.configFactoryDefault();
    flipper2fx.configFactoryDefault();

    intake1fx.setNeutralMode(NeutralMode.Coast);
    intake2fx.setNeutralMode(NeutralMode.Coast);
    flipper1fx.setNeutralMode(NeutralMode.Brake);
    flipper2fx.setNeutralMode(NeutralMode.Brake);

    intake1fx.setInverted(false);
    intake2fx.setInverted(true);
    flipper1fx.setInverted(false);
    flipper2fx.setInverted(true);

    intake2fx.follow(intake1fx);
    flipper2fx.follow(flipper1fx);

    cameraChoose.setDefaultOption("Auto flip", true);
    cameraChoose.addOption("Manual Flip", false);
  }

  public void setIntake(double vel){
    intake1fx.set(ControlMode.Velocity, vel);
  }

  public void setFlipper(double vel){
    flipper1fx.set(ControlMode.Velocity, vel);
  }

  public static boolean tipDetect() {
    if(cameraChoose.getSelected()){
      boolean placeholder=false;
      if(placeholder){
        return true;
      }
    }
    return false;
  }

  public static boolean baseDetect() {
    if(cameraChoose.getSelected()){
      boolean placeholder=false;
      if(placeholder){
        return true;
      }
    }
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private boolean manualControl;
  private boolean manualYellow;
  private boolean manualPurple;
  private ShuffleboardTab hallwayTab;
  private GenericEntry manualControlWidget;
  private GenericEntry isPurple;
  private GenericEntry isYellow;
  /** Creates a new SecondaryVisionSubsystem. */
  
    //cone orientation is 0 for tip away, 1 for tip right, 2 for tip close, 3 for tip left, 4 for upright, -1 for error
    //Note: all orientations may not be used

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

  public void setYellow() {
    manualYellow = true;
    manualPurple = false;
    System.out.println("Set Yellow");
  }

  public void setPurple() {
    manualYellow = false;
    manualPurple = true;
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

  public int getOrientation() {
    //return (int) orientation.get();
    return 0; //TODO manual control/fix
  }
}
