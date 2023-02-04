// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class SwerveConstants {

  }

  public static class ThrowerConstants {

    public static final int primaryMotorID = 5;

    public static final boolean primaryMotorInverted = false;
    public static final boolean secondaryMotorInverted = false;

    //motion magic values 
    public static int travelCruiseVelocity = 6000;
    public static int travelAcceleration = 6000;
    public static int travelProfileSmoothing = 2;

    public static int throwCruiseVelocity = 6000;
    public static int throwAcceleration = 6000;
    public static int throwProfileSmoothing = 2;

    //motion profile tolerance - how close the motor has to be to the target position to be considered "done" (measured in encoder ticks)
    public static int motionProfileTolerance = 15;

    //pid values
    public static double travelkP = 0.02;
    public static int travelkI = 0;
    public static int travelkD = 0;
    public static int travelkF = 0;

    public static double throwkP = 0.02;
    public static int throwkI = 0;
    public static int throwkD = 0;
    public static int throwkF = 0;

    //Encoder position values
    public static int loadPosition = 15000;
    public static int travelPosition = 25000;
    public static int preShootPosition = 35000;
    public static int throwPosition = 40000;

    
  }

  public static class HallwayConstants {
    
  }

  public static class AutoConstants {
    
  }

  public static class PhotonVisionConstants {
    
  }

  public static class SecondaryVisionConstants {
    
  }
}
