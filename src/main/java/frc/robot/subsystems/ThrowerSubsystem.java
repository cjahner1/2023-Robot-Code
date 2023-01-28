// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ThrowerConstants;

public class ThrowerSubsystem extends SubsystemBase {
  private WPI_TalonFX primaryMotor;
  //private WPI_TalonFX secondaryMotor;

  /** Creates a new ThrowerSubsystem. */
  public ThrowerSubsystem() {
    primaryMotor.configFactoryDefault();
    primaryMotor.setNeutralMode(NeutralMode.Brake);
    primaryMotor.setInverted(ThrowerConstants.primaryMotorInverted);
    //secondaryMotor.configFactoryDefault()
    configurePIDSlots();

  }

  @Override
  public void periodic() {
    updateShuffleboard();
  }
  
  //check to see if the motor is at the target position
  public boolean motionProfileFinished() {
    return primaryMotor.getClosedLoopError() < ThrowerConstants.motionProfileTolerance;
  }

  public void setLoadPosition() {
    configureMovement();
    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.loadPosition);
  }

  public void setTravelPosition() {
    configureMovement();
    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.travelPosition);
  }

  public void setPreThrowPosition() {
    configureMovement();
    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.preShootPosition);
  }

  public void setThrowPosition() {
    configureThrowing();
    primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.throwPosition);
  }

  //Configures motor's pid and motion magic to be more stable for travel
  public void configureMovement() {
    primaryMotor.configMotionAcceleration(ThrowerConstants.travelAcceleration);
    primaryMotor.configMotionCruiseVelocity(ThrowerConstants.travelCruiseVelocity);
    primaryMotor.configMotionSCurveStrength(ThrowerConstants.travelProfileSmoothing);
  }

  //configures motor's pid and motion magic to be more powerful for launching
  public void configureThrowing() {
    primaryMotor.configMotionAcceleration(ThrowerConstants.throwAcceleration);
    primaryMotor.configMotionCruiseVelocity(ThrowerConstants.throwCruiseVelocity);
    primaryMotor.configMotionSCurveStrength(ThrowerConstants.throwProfileSmoothing);
  }

  //configures pid slots for the motor, slot 0 for throw, slot 1 for traveling
  public void configurePIDSlots() {
    //slot 0
    primaryMotor.config_kP(ThrowerConstants.throwkP, 0);
    primaryMotor.config_kI(ThrowerConstants.throwkI, 0);
    primaryMotor.config_kD(ThrowerConstants.throwkD, 0);
    primaryMotor.config_kF(ThrowerConstants.throwkF, 0);

    //slot 1
    primaryMotor.config_kP(ThrowerConstants.travelkP, 1);
    primaryMotor.config_kI(ThrowerConstants.travelkI, 1);
    primaryMotor.config_kD(ThrowerConstants.travelkD, 1);
    primaryMotor.config_kF(ThrowerConstants.travelkF, 1);

  }
  public void updateShuffleboard() {
    ShuffleboardTab tab = Shuffleboard.getTab("Thrower");
    tab.addNumber("Primary Motor Velocity", primaryMotor::getSelectedSensorVelocity);
    tab.addNumber("Primary Motor Position", primaryMotor::getSelectedSensorPosition);
    tab.addNumber("Primary Motor Error", primaryMotor::getClosedLoopError);
  }

}
