// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  private Compressor compressor;
  private static DoubleSolenoid solenoid;
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
    compressor = new Compressor(Constants.PneumaticComstants.compressorCanID, PneumaticsModuleType.CTREPCM);
    solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 
    Constants.PneumaticComstants.solenoidChannel[0], 
    Constants.PneumaticComstants.solenoidChannel[1]);

    activateCompressor();
  }

  public void activateCompressor() {
    compressor.enableDigital();
  }

  public void deactivateCompressor() {
    compressor.disable();
  }

  public void extendCylinder() {
    solenoid.set(Value.kForward);
  }

  public void retractCylinder() {
    solenoid.set(Value.kReverse);
  }

  public void toggleCylinder() {
    solenoid.toggle();
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
