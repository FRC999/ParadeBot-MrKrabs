// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.OIConstants.ControllerDevice;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveManuallyCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeDown;
import frc.robot.commands.IntakeUp;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  public final static DriveSubsystem driveSubsystem = new DriveSubsystem();
  public final static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public Controller xboxDriveController;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureDriverInterface();
    configureBindings();
    //testMotors();
    testIntake();

    driveSubsystem.setDefaultCommand(
        new DriveManuallyCommand(
            () -> getDriverXAxis(),
            () -> getDriverYAxis()));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  private void configureDriverInterface() {
    xboxDriveController = new Controller(ControllerDevice.XBOX_CONTROLLER);
  }

  private void testMotors() {
    new JoystickButton(xboxDriveController, 1)
        .onTrue(new InstantCommand(() -> RobotContainer.driveSubsystem.testLeftLeaderMotor(0.2)))
        .onFalse(new InstantCommand(() -> RobotContainer.driveSubsystem.testLeftLeaderMotor(0.0)));
    new JoystickButton(xboxDriveController, 4)
        .onTrue(new InstantCommand(() -> RobotContainer.driveSubsystem.testRightLeaderMotor(0.2)))
        .onFalse(new InstantCommand(() -> RobotContainer.driveSubsystem.testRightLeaderMotor(0.0)));
  }

  private void testIntake() {
    // new JoystickButton(xboxDriveController, 2)
    //   .whileTrue(new IntakeDown());
    // new JoystickButton(xboxDriveController, 3)
    //   .whileTrue(new IntakeUp());

    new JoystickButton(xboxDriveController, 3)
      .whileTrue(new InstantCommand(() -> RobotContainer.intakeSubsystem.spinIntakeForward()))
      .whileFalse(new InstantCommand(() -> RobotContainer.intakeSubsystem.stopIntakeMotor()));
    new JoystickButton(xboxDriveController, 4)
      .whileTrue(new InstantCommand(() -> RobotContainer.intakeSubsystem.spinIntakeReverse()))
      .whileFalse(new InstantCommand(() -> RobotContainer.intakeSubsystem.stopIntakeMotor()));
  }

  private double getDriverXAxis() {
    return -xboxDriveController.getLeftStickY();
  }

  private double getDriverYAxis() {
    return -xboxDriveController.getRightStickX();
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
