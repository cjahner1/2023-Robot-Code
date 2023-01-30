// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotonVisionSubsystem extends SubsystemBase {
  /** Creates a new CameraSubSys. */
  private PhotonCamera camera;
  
  public PhotonVisionSubsystem() {
    configureShuffleBoard();
    camera = new PhotonCamera("photonvision");
  }
  public void configureShuffleBoard(){

  }

  @Override
  public void periodic() {
    
    }   
    // This method will be called once per scheduler run

}