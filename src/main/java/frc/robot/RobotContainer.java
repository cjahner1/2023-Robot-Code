// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.drive.DriveCommand;
import frc.robot.commands.hallway.FlipForwardCommand;
import frc.robot.commands.hallway.FlipReverseCommand;
import frc.robot.commands.hallway.IntakeCommand;
import frc.robot.commands.hallway.PurgeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.thrower.LowerCommand;
import frc.robot.commands.thrower.PreThrowCommand;
import frc.robot.commands.thrower.ResetEncoderCommand;
import frc.robot.commands.thrower.ThrowCommand;
import frc.robot.commands.thrower.TravelCommand;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.PhotonVisionSubsystem;
import frc.robot.subsystems.ThrowerSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  
  //subsystems
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final HallwaySubsystem hallwaySubsystem = new HallwaySubsystem();
  private final ThrowerSubsystem throwerSubsystem = new ThrowerSubsystem();
  private final PhotonVisionSubsystem photonVisionSubsystem = new PhotonVisionSubsystem();

  //triggers
  private final Trigger trigConeTip = new Trigger(hallwaySubsystem::tipDetect);
  private final Trigger trigConeBase = new Trigger(hallwaySubsystem::baseDetect);
  private final Trigger trigCube = new Trigger(hallwaySubsystem::cubeDetect);

  //controllers
  private final CommandXboxController primaryController = new CommandXboxController(0);
  private final CommandXboxController secondaryController = new CommandXboxController(1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Set default commands for subsystems
    driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem));
    throwerSubsystem.setDefaultCommand(new TravelCommand(throwerSubsystem));

    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    //Configure button bindings
    
    primaryController.rightTrigger(0.1).whileTrue(new IntakeCommand(hallwaySubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    primaryController.rightBumper().whileTrue(new PurgeCommand(hallwaySubsystem).withInterruptBehavior(InterruptionBehavior.kCancelIncoming));

    trigConeTip.or(secondaryController.y()).whileTrue(new FlipForwardCommand(hallwaySubsystem).alongWith(new LowerCommand(throwerSubsystem)));
    trigConeBase.or(secondaryController.a()).whileTrue(new FlipReverseCommand(hallwaySubsystem).alongWith(new LowerCommand(throwerSubsystem)));
    trigCube.or(secondaryController.b()).whileTrue(new LowerCommand(throwerSubsystem));

    secondaryController.leftTrigger(0.1).whileTrue(new ThrowCommand(throwerSubsystem));
    secondaryController.leftBumper().whileTrue(new PreThrowCommand(throwerSubsystem));
    secondaryController.rightBumper().onTrue(new ResetEncoderCommand(throwerSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // returns command to run in auto period
    return null;
  }
}
