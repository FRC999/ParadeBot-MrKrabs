// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
  public static DoubleSolenoid climberSolenoid;
  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    climberSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 
      Constants.PneumaticComstants.climberSolenoidChannels[0], 
      Constants.PneumaticComstants.climberSolenoidChannels[1]);

    climberForward();
  }

  public void climberForward() {
    climberSolenoid.set(Value.kForward);
  }

  public void climberBackward() {
    climberSolenoid.set(Value.kReverse);
  }

  public void climberToggle() {
    System.out.println("Testing Solenoid toggle 1");
    climberSolenoid.toggle();
    System.out.println("Testing Solenoid toggle 2");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
