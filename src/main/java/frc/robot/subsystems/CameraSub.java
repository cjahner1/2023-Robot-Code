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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PhotonVisionConstants;

public class CameraSub extends SubsystemBase {
  /** Creates a new CameraSubSys. */
  private PhotonCamera camera = new PhotonCamera("OV5647");
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
  
  public CameraSub() {

    SmartDashboard.putNumber("TargetID", 0);
    SmartDashboard.putNumber("Yaw / X-Offset", 0);
    SmartDashboard.putNumber("Pitch / Y-Offset", 0);
    SmartDashboard.putNumber("Area", 0);
    SmartDashboard.putNumber("Skew", 0);
    SmartDashboard.putNumber("Distance To Target", 0);
    SmartDashboard.putString("Translation2d", "0");
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

      if(targetID == 5 || targetID == 4){
      range = PhotonUtils.calculateDistanceToTargetMeters(Units.inchesToMeters(PhotonVisionConstants.cameraHeightInches), Units.inchesToMeters(PhotonVisionConstants.itemTag), Units.degreesToRadians(PhotonVisionConstants.cameraAngle), Units.degreesToRadians(result.getBestTarget().getPitch()));
      }
      else{
      range = PhotonUtils.calculateDistanceToTargetMeters(Units.inchesToMeters(PhotonVisionConstants.cameraHeightInches), Units.inchesToMeters(PhotonVisionConstants.goalTag), Units.degreesToRadians(PhotonVisionConstants.cameraAngle), Units.degreesToRadians(result.getBestTarget().getPitch()));
      }
      
      translation = PhotonUtils.estimateCameraToTargetTranslation(range, Rotation2d.fromDegrees(-target.getYaw()));
      //System.out.println(translation);
      SmartDashboard.putNumber("TargetID", targetID);
      SmartDashboard.putNumber("Yaw / X-Offset", yaw);
      SmartDashboard.putNumber("Pitch / Y-Offset", pitch);
      SmartDashboard.putNumber("Area", area);
      SmartDashboard.putNumber("Skew", skew);
      SmartDashboard.putNumber("Distance To Target", range);
      SmartDashboard.putString("Translation2d", translation.toString());
    }
      
    // This method will be called once per scheduler run
  }
}
