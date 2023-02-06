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
import frc.robot.utils.Position;

public class ThrowerSubsystem extends SubsystemBase {
  private WPI_TalonFX primaryMotor;
  private WPI_TalonFX secondaryMotor;
  //private WPI_TalonFX secondaryMotor;

  /** Creates a new ThrowerSubsystem. */
  public ThrowerSubsystem() {
    primaryMotor = new WPI_TalonFX(ThrowerConstants.primaryMotorID);
    secondaryMotor = new WPI_TalonFX(ThrowerConstants.secondaryMotorID);

    primaryMotor.configFactoryDefault();
    primaryMotor.setNeutralMode(NeutralMode.Brake);
    primaryMotor.setInverted(ThrowerConstants.primaryMotorInverted);

    secondaryMotor.configFactoryDefault();
    secondaryMotor.setInverted(!ThrowerConstants.primaryMotorInverted); //secondary motor needs to go opposite direction of primary motor
    secondaryMotor.setNeutralMode(NeutralMode.Brake);
    secondaryMotor.follow(primaryMotor);

    resetEncoders();
    configurePIDSlots();
    configureShuffleboard();

  }

  @Override
  public void periodic() {
  }
  
  //check to see if the motor is at the target position
  public boolean motionProfileFinished() {
    return primaryMotor.getClosedLoopError() < ThrowerConstants.motionProfileTolerance;
  }
  public void resetEncoders() {
    primaryMotor.setSelectedSensorPosition(0);
  }

  public void setPosition(Position position) {
    switch (position) {
      case INTAKE:
        configureMovement();
        primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.loadPosition);
        break;
      case TRAVEL:
        configureMovement();
        primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.travelPosition);
        break;
      case PRE_THROW:
        configureMovement();
        primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.preThrowPosition);
        break;
      case THROW_CONE:
        configureThrowing();
        primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.throwPosition);
        break;
      case THROW_CUBE: //TODO: cube positon and setup function
        configureThrowing();
        primaryMotor.set(ControlMode.MotionMagic, ThrowerConstants.throwPosition);
        break;
    }
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
  //TODO: cube slot
  public void configurePIDSlots() {
    //slot 0
    primaryMotor.config_kP(0, ThrowerConstants.throwkP);
    primaryMotor.config_kI(0, ThrowerConstants.throwkI);
    primaryMotor.config_kD(0, ThrowerConstants.throwkD);
    primaryMotor.config_kF(0, ThrowerConstants.throwkF);
    //slot 1
    primaryMotor.config_kP(1, ThrowerConstants.travelkP);
    primaryMotor.config_kI(1, ThrowerConstants.travelkI);
    primaryMotor.config_kD(1, ThrowerConstants.travelkD);
    primaryMotor.config_kF(1, ThrowerConstants.travelkF);
    

  }
  public void configureShuffleboard() {
    ShuffleboardTab tab = Shuffleboard.getTab("Thrower");
    //sets each shuffleboard widget to a boolean supplier from the motor, so it updates periodically
    tab.addNumber("Primary Motor Velocity", primaryMotor::getSelectedSensorVelocity);
    tab.addNumber("Primary Motor Position", primaryMotor::getSelectedSensorPosition);
    tab.addNumber("Primary Motor Error", primaryMotor::getClosedLoopError);
    tab.add(this);
  }

}
