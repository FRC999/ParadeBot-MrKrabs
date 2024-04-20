// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */

  private final TalonFX motorLeftFront = new TalonFX(Constants.DriveConstants.leftFrontDriveMotorCanID, Constants.canbusName);
  private final TalonFX motorLeftBack = new TalonFX(Constants.DriveConstants.leftBackDriveMotorCanID, Constants.canbusName);
  private final TalonFX motorRightFront = new TalonFX(Constants.DriveConstants.rightFrontDriveMotorCanID, Constants.canbusName);
  private final TalonFX motorRightBack = new TalonFX(Constants.DriveConstants.rightBackDriveMotorCanID, Constants.canbusName);
  private DifferentialDrive drive;
  public DriveSubsystem() {
    configureMotors();
    driveTrainBrakeMode();
    drive = new DifferentialDrive(motorLeftFront, motorRightFront);
    drive.setSafetyEnabled(false); // safety must be disabled siince we plan to use Motion Magic
  }

  public void configureMotors() {
    /* Initialize all the motors */

    motorLeftFront.getConfigurator().apply(new TalonFXConfiguration());
    motorLeftBack.getConfigurator().apply(new TalonFXConfiguration());
    motorRightFront.getConfigurator().apply(new TalonFXConfiguration());
    motorRightBack.getConfigurator().apply(new TalonFXConfiguration());

    motorLeftFront.setInverted(Constants.DriveConstants.leftFrontDriveMotorInverted);
    motorLeftBack.setInverted(Constants.DriveConstants.leftBackDriveMotorInverted);
    motorRightFront.setInverted(Constants.DriveConstants.rightFrontDriveMotorInverted);
    motorRightBack.setInverted(Constants.DriveConstants.rightBackDriveMotorInverted);

    /* Set the followers to follow the leaders */
    DutyCycleOut m_request = new DutyCycleOut(0);
    motorLeftBack.setControl(new Follower(motorLeftFront.getDeviceID(), Constants.DriveConstants.leftFollowerInversion));
    motorRightBack.setControl(new Follower(motorRightFront.getDeviceID(), Constants.DriveConstants.rightFollowerInversion));

  }

  public void driveTrainBrakeMode() {
    motorLeftFront.setNeutralMode(NeutralModeValue.Brake);
    motorLeftBack.setNeutralMode(NeutralModeValue.Brake);
    motorRightFront.setNeutralMode(NeutralModeValue.Brake);
    motorRightBack.setNeutralMode(NeutralModeValue.Brake);
  }

  public void driveTrainCoastMode() {
    motorLeftFront.setNeutralMode(NeutralModeValue.Coast);
    motorLeftBack.setNeutralMode(NeutralModeValue.Coast);
    motorRightFront.setNeutralMode(NeutralModeValue.Coast);
    motorRightBack.setNeutralMode(NeutralModeValue.Coast);
  }
  public void manualDrive(double move, double turn) {
    
    // If joysticks will prove to be too sensitive near the center, turn on the deadband driving
    
    // drive.arcadeDrive(deadbandMove(move), deadbandTurn(turn));
    //System.out.println("D X "+move + " Y " + turn);
    //drive.arcadeDrive(move, turn)
    drive.arcadeDrive(move, turn);
  }

  public void testRightLeaderMotor(double power) {
    motorRightFront.set(power);
  }

    public void testLeftLeaderMotor(double power) {
    motorLeftFront.set(power);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
