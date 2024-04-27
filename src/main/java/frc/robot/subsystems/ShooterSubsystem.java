// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  public static DoubleSolenoid shooterSolenoid;
  public static WPI_TalonSRX leftShooterMotor;
  public static WPI_TalonSRX rightShooterMotor;
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    shooterSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 
      Constants.PneumaticComstants.shooterSolenoidChannel[0], 
      Constants.PneumaticComstants.shooterSolenoidChannel[1]);

      leftShooterMotor = new WPI_TalonSRX(Constants.ShooterConstants.leftShooterMotorID);
      rightShooterMotor = new WPI_TalonSRX(Constants.ShooterConstants.rightShooterMotorID);
      
      shooterMotorBrakeMode();
      configureShooterMotors();
  }

  public void configureShooterMotors() {
    leftShooterMotor.configFactoryDefault();
    rightShooterMotor.configFactoryDefault();

    leftShooterMotor.setInverted(InvertType.InvertMotorOutput);
    rightShooterMotor.setInverted(InvertType.InvertMotorOutput);

    rightShooterMotor.follow(leftShooterMotor);
    leftShooterMotor.set(ControlMode.PercentOutput, 0);
  }

  public void shooterMotorBrakeMode() {
    leftShooterMotor.setNeutralMode(NeutralMode.Brake);
    rightShooterMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void runShooterIn() {
    leftShooterMotor.set(Constants.ShooterConstants.shooterSpeedIn);
  }

  public void runShooterOut() {
    leftShooterMotor.set(Constants.ShooterConstants.shooterSpeedOut);
  }

  public void stopShooter() {
    leftShooterMotor.set(0);
  }

  public void extendPlunger() {
    shooterSolenoid.set(Value.kForward);
  }

  public void retractPlunger() {
    shooterSolenoid.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
