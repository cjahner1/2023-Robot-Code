// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PhotonVisionConstants;

public class PhotonVisionSubsystem extends SubsystemBase {
  /** Creates a new CameraSubSys. */
  private PhotonCamera camera;
  private PhotonPipelineResult result;
  private List<PhotonTrackedTarget> targets;
  private PhotonTrackedTarget target;
  private int targetID;
  private double yaw;
  private double pitch;
  private double area;
  private double skew;
  private Translation2d translation;
  private double range;
  
  public PhotonVisionSubsystem() {
    configureShuffleBoard();
    camera = new PhotonCamera("photonvision");
  }
  public void configureShuffleBoard(){
    ShuffleboardTab tab = Shuffleboard.getTab("PhotonVision");
    tab.addNumber("TargetID", this::getTargetID);
    tab.addNumber("Yaw / X-Offset", this::getYaw);
    tab.addNumber("Pitch / Y-Offset", this::getPitch);
    tab.addNumber("Area", this::getArea);
    tab.addNumber("Skew", this::getSkew);
    tab.addNumber("Distance To Target", this::getRange);
    tab.addString("Translation2d", this::getTranslationString);
  }

  @Override
  public void periodic() {
    result = camera.getLatestResult();
    SmartDashboard.putBoolean("Target Valid", result.hasTargets());
    if (result.hasTargets() == true){

      targets = result.getTargets();
      target = result.getBestTarget();
      yaw = target.getYaw();
      pitch = target.getPitch();
      area = target.getArea();
      skew = target.getSkew();
      targetID = target.getFiducialId();

      //check to see if the target is a goal or a hatch
      if(targetID == 5 || targetID == 4){
      range = PhotonUtils.calculateDistanceToTargetMeters(Units.inchesToMeters(PhotonVisionConstants.cameraHeightInches), Units.inchesToMeters(PhotonVisionConstants.itemTag), Units.degreesToRadians(PhotonVisionConstants.cameraAngle), Units.degreesToRadians(result.getBestTarget().getPitch()));
      }
      else{
      range = PhotonUtils.calculateDistanceToTargetMeters(Units.inchesToMeters(PhotonVisionConstants.cameraHeightInches), Units.inchesToMeters(PhotonVisionConstants.goalTag), Units.degreesToRadians(PhotonVisionConstants.cameraAngle), Units.degreesToRadians(result.getBestTarget().getPitch()));
      }
      
      translation = PhotonUtils.estimateCameraToTargetTranslation(range, Rotation2d.fromDegrees(-target.getYaw()));
    }   
    // This method will be called once per scheduler run
  }
  public double getTargetID(){
    return targetID;
  }
  public double getYaw(){
    return yaw;
  }
  public double getPitch(){
    return pitch;
  }
  public double getArea(){
    return area;
  }
  public double getSkew(){
    return skew;
  }
  public double getRange(){
    return range;
  }
  public Translation2d getTranslation(){
    return translation;
  }
  public String getTranslationString(){
    return translation.toString();
  }
}