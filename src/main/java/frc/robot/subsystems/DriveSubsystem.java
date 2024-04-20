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

  private final TalonFX motorLeftLeader = new TalonFX(Constants.DriveConstants.leftFrontDriveMotorCanID, Constants.canbusName);
  private final TalonFX motorLeftFollower = new TalonFX(Constants.DriveConstants.leftBackDriveMotorCanID, Constants.canbusName);
  private final TalonFX motorRightLeader = new TalonFX(Constants.DriveConstants.rightFrontDriveMotorCanID, Constants.canbusName);
  private final TalonFX motorRightFollower = new TalonFX(Constants.DriveConstants.rightBackDriveMotorCanID, Constants.canbusName);
  private DifferentialDrive drive;
  public DriveSubsystem() {
    configureMotors();
    driveTrainBrakeMode();
    drive = new DifferentialDrive(motorLeftLeader, motorRightLeader);
    drive.setSafetyEnabled(false); // safety must be disabled siince we plan to use Motion Magic
  }

  public void configureMotors() {
    /* Initialize all the motors */

    motorLeftLeader.getConfigurator().apply(new TalonFXConfiguration());
    motorLeftFollower.getConfigurator().apply(new TalonFXConfiguration());
    motorRightLeader.getConfigurator().apply(new TalonFXConfiguration());
    motorRightFollower.getConfigurator().apply(new TalonFXConfiguration());


    /* Set the followers to follow the leaders */
    DutyCycleOut m_request = new DutyCycleOut(0);
    motorLeftFollower.setControl(new Follower(motorLeftLeader.getDeviceID(), Constants.DriveConstants.leftFrontDriveMotorInverted));
    motorRightFollower.setControl(new Follower(motorRightLeader.getDeviceID(), Constants.DriveConstants.rightFrontDriveMotorInverted));

  }

  public void driveTrainBrakeMode() {
    motorLeftLeader.setNeutralMode(NeutralModeValue.Brake);
    motorLeftFollower.setNeutralMode(NeutralModeValue.Brake);
    motorRightLeader.setNeutralMode(NeutralModeValue.Brake);
    motorRightFollower.setNeutralMode(NeutralModeValue.Brake);
  }

  public void driveTrainCoastMode() {
    motorLeftLeader.setNeutralMode(NeutralModeValue.Coast);
    motorLeftFollower.setNeutralMode(NeutralModeValue.Coast);
    motorRightLeader.setNeutralMode(NeutralModeValue.Coast);
    motorRightFollower.setNeutralMode(NeutralModeValue.Coast);
  }
  public void manualDrive(double move, double turn) {
    
    // If joysticks will prove to be too sensitive near the center, turn on the deadband driving
    
    // drive.arcadeDrive(deadbandMove(move), deadbandTurn(turn));
    // System.out.println("D X "+move + " Y " + turn);
    //drive.arcadeDrive(move, turn);
    drive.arcadeDrive(move, turn);
  }

  public void testRightLeaderMotor(double power) {
    motorRightLeader.set(power);
  }

    public void testLeftLeaderMotor(double power) {
    motorLeftLeader.set(power);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
