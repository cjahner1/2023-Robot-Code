// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose2d;

/** Add your docs here. */
public class Goal {
    private int height;
    private Direction direction;
    private Pose2d scoringPose;

    public Goal(int height, Direction direction, Pose2d scoringPose) {
        this.height = height;
        this.direction = direction;
        this.scoringPose = scoringPose;
    }

    public int getHeight() {
        return height;
    }

    public Direction getDirection() {
        return direction;
    }

    public Pose2d getScoringPose() {
        return scoringPose;
    }
}
