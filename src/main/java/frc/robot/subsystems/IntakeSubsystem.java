// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  private WPI_TalonSRX intakeMotorController;
  public static DoubleSolenoid intakeSolenoid;
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
    intakeMotorController = new WPI_TalonSRX(Constants.IntakeConstants.intakeMotorPort);
    intakeMotorController.setInverted(true);

    
    intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 
      Constants.PneumaticComstants.intakeSolenoidChannel[0], 
      Constants.PneumaticComstants.intakeSolenoidChannel[1]);
  }

public void configureIntakeMotor() {
  intakeMotorController.setNeutralMode(NeutralMode.Brake);
}

  public void extendCylinder() {
    intakeSolenoid.set(Value.kForward);
  }

  public void retractCylinder() {
    intakeSolenoid.set(Value.kReverse);
  }

  public void toggleCylinder() {
    intakeSolenoid.toggle();
  }

  public void spinIntakeForward() {
    intakeMotorController.set(Constants.IntakeConstants.intakeForwardSpeed);
  }

  public void spinIntakeReverse() {
    intakeMotorController.set(Constants.IntakeConstants.intakeReverseSpeed);
  }

  public void stopIntakeMotor() {
    intakeMotorController.set(0);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
