// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import frc.robot.commands.drive.DriveCommand;
import frc.robot.commands.hallway.FlipCommand;
import frc.robot.commands.hallway.IntakeCommand;
import frc.robot.commands.hallway.PurgeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.thrower.LowerCommand;
import frc.robot.commands.thrower.PreThrowCommand;
import frc.robot.commands.thrower.ResetEncoderCommand;
import frc.robot.commands.thrower.ThrowCommand;
import frc.robot.commands.thrower.TravelCommand;
import frc.robot.commands.vision.ManualControl;
import frc.robot.subsystems.HallwaySubsystem;
import frc.robot.subsystems.PhotonVisionSubsystem;
import frc.robot.subsystems.SecondaryVisionSubsystem;
import frc.robot.subsystems.ThrowerSubsystem;
import frc.robot.utils.GamePiece;
import frc.robot.utils.Orientation;
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
  //subsystems
  private final PhotonVisionSubsystem photonVisionSubsystem = new PhotonVisionSubsystem();
  private final HallwaySubsystem hallwaySubsystem = new HallwaySubsystem();
  private final ThrowerSubsystem throwerSubsystem = new ThrowerSubsystem();
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final SecondaryVisionSubsystem secondaryVisionSubsystem = new SecondaryVisionSubsystem();

  //commands
  private final DriveCommand driveCommand = new DriveCommand(driveSubsystem);
  private final PurgeCommand purgeCommand = new PurgeCommand(hallwaySubsystem);
  private final IntakeCommand intakeCommand = new IntakeCommand(hallwaySubsystem, secondaryVisionSubsystem);
  private final LowerCommand lowerCommand = new LowerCommand(throwerSubsystem);
  private final ThrowCommand throwCommand = new ThrowCommand(throwerSubsystem);

  //triggers
  private final Trigger trigFlipForward = new Trigger(() -> (secondaryVisionSubsystem.getOrientation() == Orientation.TIP_FORWARD));
  private final Trigger trigFlipReverse = new Trigger(() -> (secondaryVisionSubsystem.getOrientation() == Orientation.BASE_FORWARD));

  //controllers
  private final CommandXboxController primaryController = new CommandXboxController(0);
  private final CommandXboxController secondaryController = new CommandXboxController(1);

  //button names
  private Trigger throwButton;
  private Trigger intakeButton;
  private Trigger purgeButton;
  private Trigger manualSetConeButton;
  private Trigger manualSetCubeButton;
  private Trigger resetEncoderButton;

  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Set default commands for subsystems
    //driveSubsystem.setDefaultCommand(driveCommand);

    // Configure button map
    configureBindings();
    
    //configure commands to run for each subsystem
    configureThrowerSubsystem();
    configureHallwaySubsystem();

    //default commands
    throwerSubsystem.setDefaultCommand(new TravelCommand(throwerSubsystem));
    driveSubsystem.setDefaultCommand(driveCommand);
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
    /*
     * Driver Control Scheme:
     * 
     * Right Joystick: Drive
     * Left Joystick: Rotate
     * Right Trigger: Intake
     * Right Bumper: Purge
     * A: Throw
     * 
     * Codriver Control Scheme:
     * 
     * 
     */
    throwButton = primaryController.a();
    intakeButton = primaryController.rightTrigger(0.2);
    purgeButton = primaryController.rightBumper();

    manualSetCubeButton = secondaryController.x();
    manualSetConeButton = secondaryController.y();
    resetEncoderButton = secondaryController.y();
  
  }

  private void configureHallwaySubsystem() {
    //Configure hallway subsystem
    intakeButton.whileTrue(intakeCommand.alongWith(lowerCommand));
    purgeButton.whileTrue(purgeCommand.withInterruptBehavior(InterruptionBehavior.kCancelIncoming));
    //only run the flipper while intaking
    trigFlipForward.or(secondaryController.y()).and(intakeCommand::isScheduled).whileTrue(new FlipCommand(hallwaySubsystem, false)); //TODO: add manual control logic to secondaryVisionSubsystem.getOrientation() for manual control and remove .or
    trigFlipReverse.or(secondaryController.a()).and(intakeCommand::isScheduled).whileTrue(new FlipCommand(hallwaySubsystem, true)); //TODO: replace paramater with booleansupplier inside FlipCommand
  }

  private void configureThrowerSubsystem() {
    //Configure thrower subsystem

    throwButton.whileTrue(throwCommand);

    //thrower manual control buttons, really for debugging purposes only, will likely add to codriver controller in a way that cant accidentally be triggered
    primaryController.povUp().whileTrue(new TravelCommand(throwerSubsystem));
    primaryController.povDown().whileTrue(new PreThrowCommand(throwerSubsystem));
    primaryController.povLeft().whileTrue(new ThrowCommand(throwerSubsystem));
    primaryController.povRight().whileTrue(new LowerCommand(throwerSubsystem));

    resetEncoderButton.whileTrue(new ResetEncoderCommand(throwerSubsystem));

    //codriver manual control buttons
    manualSetConeButton.whileTrue(new ManualControl(secondaryVisionSubsystem, GamePiece.CONE));
    manualSetCubeButton.whileTrue(new ManualControl(secondaryVisionSubsystem, GamePiece.CUBE));

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
