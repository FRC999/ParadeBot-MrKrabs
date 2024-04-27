// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  public static DoubleSolenoid shooterSolenoid;
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    shooterSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 
      Constants.PneumaticComstants.shooterSolenoidChannel[0], 
      Constants.PneumaticComstants.shooterSolenoidChannel[1]);
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
