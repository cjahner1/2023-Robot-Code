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
  private WPI_TalonFX thrower1 = new WPI_TalonFX(13);
  private WPI_TalonFX thrower2 = new WPI_TalonFX(14);

  /** Creates a new ThrowerSubsystem. */
  public ThrowerSubsystem() {
    thrower2.configFactoryDefault();
    thrower2.configFactoryDefault();

    thrower2.setNeutralMode(NeutralMode.Brake);
    thrower2.setNeutralMode(NeutralMode.Brake);

    thrower1.setInverted(false);
    thrower2.setInverted(true);

    thrower2.follow(thrower1);

    //slot 0
    thrower1.config_kP(0, ThrowerConstants.throwkP);
    thrower1.config_kI(0, ThrowerConstants.throwkI);
    thrower1.config_kD(0, ThrowerConstants.throwkD);
    thrower1.config_kF(0, ThrowerConstants.throwkF);
    //slot 1
    thrower1.config_kP(1, ThrowerConstants.travelkP);
    thrower1.config_kI(1, ThrowerConstants.travelkI);
    thrower1.config_kD(1, ThrowerConstants.travelkD);
    thrower1.config_kF(1, ThrowerConstants.travelkF);

    ShuffleboardTab tab = Shuffleboard.getTab("Thrower");
    //sets each shuffleboard widget to a boolean supplier from the motor, so it updates
    tab.addNumber("Primary Motor Velocity", thrower1::getSelectedSensorVelocity);
    tab.addNumber("Primary Motor Position", thrower1::getSelectedSensorPosition);
    tab.addNumber("Primary Motor Error", thrower1::getClosedLoopError);
    tab.add(this);

    resetEncoders();
  }
  
  //check to see if the motor is at the target position
  public boolean motionProfileFinished() {
    return (thrower1.getClosedLoopError() < ThrowerConstants.motionProfileTolerance);
  }

  public void resetEncoders() {
    thrower1.setSelectedSensorPosition(0);
  }

  public void setTravelPosition() {
    configureMovement();
    thrower1.set(ControlMode.MotionMagic, ThrowerConstants.travelPosition);
  }
  
  public void setLoadPosition() {
    configureMovement();
    thrower1.set(ControlMode.MotionMagic, ThrowerConstants.loadPosition);
  }

  public void setPreThrowPosition() {
    configureMovement();
    thrower1.set(ControlMode.MotionMagic, ThrowerConstants.preShootPosition);
  }

  public void setThrowPosition() {
    configureThrowing();
    thrower1.set(ControlMode.MotionMagic, ThrowerConstants.throwPosition);
  }

  //Configures motor's pid and motion magic to be more stable for travel
  public void configureMovement() {
    thrower1.configMotionAcceleration(ThrowerConstants.travelAcceleration);
    thrower1.configMotionCruiseVelocity(ThrowerConstants.travelCruiseVelocity);
    thrower1.configMotionSCurveStrength(ThrowerConstants.travelProfileSmoothing);
  }

  //configures motor's pid and motion magic to be more powerful for launching
  public void configureThrowing() {
    thrower1.configMotionAcceleration(ThrowerConstants.throwAcceleration);
    thrower1.configMotionCruiseVelocity(ThrowerConstants.throwCruiseVelocity);
    thrower1.configMotionSCurveStrength(ThrowerConstants.throwProfileSmoothing);
  }

  @Override
  public void periodic() {
  }
}
