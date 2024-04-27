// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  public WPI_TalonSRX tiltMotorController;

  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {
    tiltMotorController = new WPI_TalonSRX(Constants.ShooterConstants.tiltMotorPortID);
  }

  public void configureTiltMotorController() {
    tiltMotorController.configFactoryDefault();

    tiltMotorController.setInverted(Constants.ShooterConstants.MotorInvert);

    tiltMotorController.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.CTRE_MagEncoder_Absolute,
        Constants.ShooterConstants.PID_TILT,
        Constants.ShooterConstants.configureTimeoutMs);

    tiltMotorController.setSensorPhase(Constants.ShooterConstants.tiltEncoderSensorPhase);
  }

  public int getTiltEncoder() {
    return (int) tiltMotorController.getSelectedSensorPosition();
  }

  public void initQuadrature() { // Set absolute encoders
    int pulseWidth = tiltMotorController.getSensorCollection().getPulseWidthPosition();
  }

  private void configureCurrentLimiterAngle() {
    tiltMotorController.configPeakCurrentLimit(Constants.ShooterConstants.tiltPeakCurrentLimit,
        Constants.ShooterConstants.configureTimeoutMs);
    tiltMotorController.configPeakCurrentDuration(Constants.ShooterConstants.tiltPeakCurrentDuration,
        Constants.ShooterConstants.configureTimeoutMs);
    tiltMotorController.configContinuousCurrentLimit(Constants.ShooterConstants.tiltContinuousCurrentLimit,
        Constants.ShooterConstants.configureTimeoutMs);
    tiltMotorController.enableCurrentLimit(Constants.ShooterConstants.tiltEnableCurrentLimit); // Honor initial setting

  }

  public int getTiltAbsoluteEncoder() {
    return (int) tiltMotorController.getSensorCollection().getPulseWidthPosition() & 0xFFF;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
