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
  private final PhotonVisionSubsystem photonVisionSubsystem = new PhotonVisionSubsystem();
  private final HallwaySubsystem hallwaySubsystem = new HallwaySubsystem();
  private final ThrowerSubsystem throwerSubsystem = new ThrowerSubsystem();
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();

  //commands
  private final DriveCommand driveCommand = new DriveCommand();

  //triggers
  private final Trigger trigFlipForward = new Trigger(HallwaySubsystem::tipDetect);
  private final Trigger trigFlipReverse = new Trigger(HallwaySubsystem::baseDetect);

  //controllers
  private final CommandXboxController controller1 = new CommandXboxController(0);
  private final CommandXboxController controller2 = new CommandXboxController(1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Set default commands for subsystems
    driveSubsystem.setDefaultCommand(driveCommand);

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
    controller1.rightTrigger(0.2).whileTrue(new IntakeCommand(hallwaySubsystem).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    controller1.rightBumper().whileTrue(new PurgeCommand(hallwaySubsystem).withInterruptBehavior(InterruptionBehavior.kCancelIncoming));
    trigFlipForward.or(controller2.y()).whileTrue(new FlipForwardCommand(hallwaySubsystem));
    trigFlipReverse.or(controller2.a()).whileTrue(new FlipReverseCommand(hallwaySubsystem));

    
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
