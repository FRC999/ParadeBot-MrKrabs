// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */

  private final TalonFX m_leftLeader = new TalonFX(Constants.DriveConstants.LeftFrontDriveMotorCanID, Constants.CanbusName);
  private final TalonFX m_leftFollower = new TalonFX(Constants.DriveConstants.LeftBackDriveMotorCanID, Constants.CanbusName);
  private final TalonFX m_rightLeader = new TalonFX(Constants.DriveConstants.RightFrontDriveMotorCanID, Constants.CanbusName);
  private final TalonFX m_rightFollower = new TalonFX(Constants.DriveConstants.RightBackDriveMotorCanID, Constants.CanbusName);

  public DriveSubsystem() {}

  public void configureMotors() {
    /* Initialize all the motors */
    initializeLeftDriveTalonFX(m_leftLeader.getConfigurator());
    initializeLeftDriveTalonFX(m_leftFollower.getConfigurator());
    initializeRightDriveTalonFX(m_rightLeader.getConfigurator());
    initializeRightDriveTalonFX(m_rightFollower.getConfigurator());

    /* Set the followers to follow the leaders */
    m_leftFollower.setControl(new Follower(m_leftLeader.getDeviceID(), false));
    m_rightFollower.setControl(new Follower(m_rightLeader.getDeviceID(), false));
  }

  private void initializeLeftDriveTalonFX(TalonFXConfigurator cfg) {
    var toApply = new TalonFXConfiguration();

    toApply.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

    cfg.apply(toApply);
    
    cfg.setPosition(0);
  }

  private void initializeRightDriveTalonFX(TalonFXConfigurator cfg) {
    var toApply = new TalonFXConfiguration();

    toApply.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

    cfg.apply(toApply);
    cfg.setPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
