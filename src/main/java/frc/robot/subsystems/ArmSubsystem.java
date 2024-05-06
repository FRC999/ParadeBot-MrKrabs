// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  private WPI_TalonSRX tiltMotorController;

  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {
    tiltMotorController = new WPI_TalonSRX(Constants.ShooterConstants.tiltMotorPortID);

    configureTiltMotorController();
    tiltMotorBrakeMode();
  }

  public void configureTiltMotorController() {
    tiltMotorController.configFactoryDefault();

    tiltMotorController.setInverted(Constants.ShooterConstants.tiltMotorInvert);

    tiltMotorController.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.CTRE_MagEncoder_Absolute,
        Constants.ShooterConstants.PID_TILT,
        Constants.ShooterConstants.configureTimeoutMs);

    tiltMotorController.setSensorPhase(Constants.ShooterConstants.tiltEncoderSensorPhase);

    tiltMotorController.configPeakOutputForward(Constants.ShooterConstants.PeakOutput,
        Constants.ShooterConstants.configureTimeoutMs);
    tiltMotorController.configPeakOutputReverse(Constants.ShooterConstants.PeakOutput * (-1),
        Constants.ShooterConstants.configureTimeoutMs);
    tiltMotorController.configNominalOutputForward(0, Constants.ShooterConstants.configureTimeoutMs);
    tiltMotorController.configNominalOutputReverse(0, Constants.ShooterConstants.configureTimeoutMs);

    tiltMotorController.configAllowableClosedloopError(Constants.ShooterConstants.SLOT_0,
        Constants.ShooterConstants.tiltDefaultAcceptableError,
        Constants.ShooterConstants.configureTimeoutMs);

    tiltMotorController.config_kP(Constants.ShooterConstants.SLOT_0, Constants.ShooterConstants.P_TILT,
        Constants.ShooterConstants.configureTimeoutMs);
    tiltMotorController.config_kI(Constants.ShooterConstants.SLOT_0, Constants.ShooterConstants.I_TILT,
        Constants.ShooterConstants.configureTimeoutMs);
    tiltMotorController.config_kD(Constants.ShooterConstants.SLOT_0, Constants.ShooterConstants.D_TILT,
        Constants.ShooterConstants.configureTimeoutMs);
    tiltMotorController.config_kF(Constants.ShooterConstants.SLOT_0, Constants.ShooterConstants.F_TILT,
        Constants.ShooterConstants.configureTimeoutMs);

    tiltMotorController
        .setSelectedSensorPosition((getTiltAbsoluteEncoder() - Constants.ShooterConstants.absoluteEncoderZeroValue));
  }

  public void tiltMotorBrakeMode() {
    tiltMotorController.setNeutralMode(NeutralMode.Brake);
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

  public void tiltMoveWithPower(double power) {
    tiltMotorController.set(TalonSRXControlMode.PercentOutput, power);
  }

  public void tiltHoldPosition(int position) {
    tiltMotorController.set(TalonSRXControlMode.Position, position);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
