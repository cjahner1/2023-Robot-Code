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

  private static NetworkTable table = NetworkTableInstance.getDefault().getTable("camera");
  private static StringSubscriber color = table.getStringTopic("color").subscribe("error");
  private static IntegerSubscriber orientation = table.getIntegerTopic("orientation").subscribe(-1);

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

  private String getColor() {
    return color.get();
  }

  private int getOrientation() {
    return (int)orientation.get();
  }

  public boolean tipDetect() {
    if(cameraChoose.getSelected()){
      if(getColor().equals("yellow")){
        if(getOrientation() == 2){
          return true;
        }
      }
    }
    return false;
  }

  public boolean baseDetect() {
    if(cameraChoose.getSelected()){
      if(getColor().equals("yellow")){
        if(getOrientation() == 0){
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean cubeDetect() {
    if(cameraChoose.getSelected()){
      if(getColor().equals("purple")){
        return true;
      }
    }
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
