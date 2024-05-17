// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.OIConstants.ControllerDevice;
import frc.robot.commands.ArmReleasePID;
import frc.robot.commands.ArmToPickup;
import frc.robot.commands.ArmToShoot;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveManuallyCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ExtendPlunger;
import frc.robot.commands.IntakeBall;
import frc.robot.commands.IntakeBallStop;
import frc.robot.commands.IntakeDown;
import frc.robot.commands.IntakeStopSpinning;
import frc.robot.commands.IntakeUp;
import frc.robot.commands.IntakeUpAndShooterStopSpinning;
import frc.robot.commands.RetractPlunger;
import frc.robot.commands.ShootBallSequence;
import frc.robot.commands.ShooterStopSpinning;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SmartDashboardSubsystem;
import frc.robot.subsystems.LEDSubsystem.AnimationTypes;
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
  public final static PneumaticsSubsystem pneumaticsSubsystem = new PneumaticsSubsystem();
  public final static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public final static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  public final static ArmSubsystem armSubsystem = new ArmSubsystem();
    public final static LEDSubsystem ledSubsystem = new LEDSubsystem();
  public final static SmartDashboardSubsystem smartDashboardSubsystem = new SmartDashboardSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public Controller xboxDriveController;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureDriverInterface();
    configureBindings();
    configureTrigger();
    LEDAnimationChange();
    //testMotors();
    //testIntake();
    //testShooter();
    // testArm();

    finalControlMapping();

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

  private void finalControlMapping() {
    new Trigger(() -> xboxDriveController.getRawAxis(Constants.OIConstants.xBoxControllerLeftTrigger) > 0.3)
      .onTrue(new IntakeUpAndShooterStopSpinning());
    new Trigger(() -> xboxDriveController.getRawAxis(Constants.OIConstants.xBoxControllerLeftTrigger) > 0.3)
      .onTrue(new IntakeBall())
      .onFalse(new IntakeBallStop());
    new JoystickButton(xboxDriveController, 1)
      .onTrue(new ShootBallSequence());
  }

  private void configureTrigger() {
    new Trigger(() -> xboxDriveController.getRawAxis(Constants.OIConstants.xBoxControllerRightTrigger) > 0.3) // L2 trigger - spit out note
      .onTrue(new IntakeBall())
      .onFalse(new IntakeBallStop());
    new Trigger(() -> xboxDriveController.getRawAxis(Constants.OIConstants.xBoxControllerLeftTrigger) > 0.3)
      .onTrue(new IntakeUp());
    new JoystickButton(xboxDriveController, 6)
      .onTrue(new ShootBallSequence())
      .onFalse(new ShooterStopSpinning());
  }

  private void configureDriverInterface() {
    xboxDriveController = new Controller(ControllerDevice.XBOX_CONTROLLER);
  }

  private void LEDAnimationChange() {
    new JoystickButton(xboxDriveController, 7)
        .onTrue(new InstantCommand(() -> RobotContainer.ledSubsystem.setLEDRed()))
        .onTrue(new InstantCommand(() -> RobotContainer.ledSubsystem.setLEDWhite()))
        .onTrue(new InstantCommand(() -> RobotContainer.ledSubsystem.setLEDBlue()))
        .onFalse(new InstantCommand(() -> RobotContainer.ledSubsystem.configBrightness(0)));

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
    new JoystickButton(xboxDriveController, 2)
      .whileTrue(new IntakeDown());
    new JoystickButton(xboxDriveController, 3)
      .whileTrue(new IntakeUp());

    new JoystickButton(xboxDriveController, 3)
      .whileTrue(new InstantCommand(() -> RobotContainer.intakeSubsystem.spinIntakeForward()))
      .whileFalse(new InstantCommand(() -> RobotContainer.intakeSubsystem.stopIntakeMotor()));
    new JoystickButton(xboxDriveController, 4)
      .whileTrue(new InstantCommand(() -> RobotContainer.intakeSubsystem.spinIntakeReverse()))
      .whileFalse(new InstantCommand(() -> RobotContainer.intakeSubsystem.stopIntakeMotor()));
  }

  private void testShooter() {
    new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerAButton)
      .onTrue(new ExtendPlunger());
    new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerYButton)
      .onTrue(new RetractPlunger());
    
    new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerXButton)
      .whileTrue(new InstantCommand(() -> RobotContainer.shooterSubsystem.runShooterIn()))
      .whileFalse(new InstantCommand(() -> RobotContainer.shooterSubsystem.stopShooter()));
    new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerBButton)
      .whileTrue(new InstantCommand(() -> RobotContainer.shooterSubsystem.runShooterOut()))
      .whileFalse(new InstantCommand(() -> RobotContainer.shooterSubsystem.stopShooter()));

    new JoystickButton(xboxDriveController, 6)
      .whileTrue(new ShootBallSequence());
  }

  private void testArm() {
    // new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerAButton)
    //   .onTrue(new InstantCommand(() -> RobotContainer.armSubsystem.tiltMoveWithPower(0.2)))
    //   .onFalse(new InstantCommand(() -> RobotContainer.armSubsystem.tiltMoveWithPower(0.0)));

    // new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerBButton)
    //   .onTrue(new InstantCommand(() -> RobotContainer.armSubsystem.tiltHoldPosition(Constants.ShooterConstants.tiltBallShootingPosition)))
    //   .onFalse(new InstantCommand(() -> RobotContainer.armSubsystem.tiltMoveWithPower(0.0)));

    // new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerXButton)
    //   .onTrue(new InstantCommand(() -> RobotContainer.armSubsystem.tiltHoldPosition(Constants.ShooterConstants.tiltBallPickupPosition)))
    //   .onFalse(new InstantCommand(() -> RobotContainer.armSubsystem.tiltMoveWithPower(0.0)));

    // new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerAButton)
    //    .onTrue(new ArmToPickup());
    
    // new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerBButton)
    //    .onTrue(new ArmToShoot());
    
    // new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerXButton)
    //    .onTrue(new ArmReleasePID());

    new JoystickButton(xboxDriveController, Constants.OIConstants.xBoxControllerXButton)
      .onTrue(new IntakeBall())
      .onFalse(new IntakeBallStop());
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
