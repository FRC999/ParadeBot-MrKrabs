// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.RobotContainer;

public class DriveManuallyCommand extends Command {
  DoubleSupplier xAxis;
  DoubleSupplier yAxis;
  /** Creates a new DriveManuallyCommand. */
  public DriveManuallyCommand(DoubleSupplier xAxis, DoubleSupplier yAxis) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.driveSubsystem);
    this.xAxis = xAxis;
    this.yAxis = yAxis;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.driveSubsystem.driveTrainBrakeMode();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double move = xAxis.getAsDouble();
    double turn = yAxis.getAsDouble();

    RobotContainer.driveSubsystem.manualDrive(move, turn * DriveConstants.turnAdjust);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
