// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
	public static String canbusName = "rio";

	public static class CANdleConstants{
		public static int candlePort = 0;
	}

	public static class OperatorConstants {
		public static final int kDriverControllerPort = 0;
	}

	public static class DriveConstants {
		public static final int rightFrontDriveMotorCanID = 1;
		public static final int rightBackDriveMotorCanID = 2;
		public static final int leftFrontDriveMotorCanID = 3;
		public static final int leftBackDriveMotorCanID = 4;

		public static final boolean leftFollowerInversion = false;
		public static final boolean rightFollowerInversion = false;

		public static final boolean rightFrontDriveMotorInverted = false;
		public static final boolean rightBackDriveMotorInverted = false;
		public static final boolean leftFrontDriveMotorInverted = true;
		public static final boolean leftBackDriveMotorInverted = true;

		public static double turnAdjust = 0.6;
	}

	public static final class OIConstants {
		public static final int driverControllerPort = 0;

		public static final int xBoxControllerAButton = 1;
		public static final int xBoxControllerBButton = 2;
		public static final int xBoxControllerXButton = 3;
		public static final int xBoxControllerYButton = 4;
		public static final int xBoxControllerRightTrigger = 3;
		public static final int xBoxControllerLeftTrigger = 2;

		public static enum ControllerDeviceType {
			LOGITECH,
			PS5,
			XBOX, // RightJ F/B, LeftJ L/R, L2/R2 - rotation
			XBOX_ONEDRIVE // RIghtJ F/B/L/R, LeftJ - rotation
		}

		public static enum ControllerDevice {

			XBOX_CONTROLLER(
					0, // Port Number for Xbox controller
					ControllerDeviceType.XBOX,
					0.03, // deadband X for Xbox
					0.03, // deadband Y for Xbox //TODO: ALL DEADBAND FOR XBOX IS PLACEHOLDER
					0.03, // deadband Omega for Xbox
					false, // No cube controller configuration for Xbox yet
					false);

			private ControllerDeviceType controllerDeviceType;
			private int portNumber;
			private double deadbandX;
			private double deadbandY;
			private double deadbandOmega;
			private boolean cubeControllerLeftStick;
			private boolean cubeControllerRightStick;

			ControllerDevice(int pn, ControllerDeviceType cdt, double dx, double dy, double dm, boolean ccL,
					boolean ccR) {
				this.portNumber = pn;
				this.controllerDeviceType = cdt;
				this.deadbandX = dx;
				this.deadbandY = dy;
				this.deadbandOmega = dm;
				this.cubeControllerLeftStick = ccL;
				this.cubeControllerRightStick = ccR;
			}

			public ControllerDeviceType getControllerDeviceType() {
				return controllerDeviceType;
			}

			public int getPortNumber() {
				return portNumber;
			}

			public double getDeadbandX() {
				return deadbandX;
			}

			public double getDeadbandY() {
				return deadbandY;
			}

			public double getDeadbandOmega() {
				return deadbandOmega;
			}

			public boolean isCubeControllerLeftStick() {
				return cubeControllerLeftStick;
			}

			public boolean isCubeControllerRightStick() {
				return cubeControllerRightStick;
			}
		}
	}

	public static final class PneumaticComstants {
		public static final int compressorCanID = 0;
		public static final int[] intakeSolenoidChannel = { 2, 3 };
		public static final int[] shooterSolenoidChannel = { 1, 0 };
		public static int[] climberSolenoidChannels = new int[] { 4 , 5 };
	}

	public static final class IntakeConstants {
		public static final int intakeMotorPort = 5;
		public static final double intakeForwardSpeed = 0.5;
		public static final double intakeReverseSpeed = -0.7;
	}

	public static final class ShooterConstants {
		public static final int tiltMotorPortID = 10;
		public static final int leftShooterMotorID = 11;
		public static final int rightShooterMotorID = 12;

		public static final double shooterSpeedIn = -0.5;
		public static final double shooterSpeedOut = 0.8;

		public final static int configureTimeoutMs = 30;
		public final static int PID_TILT = 0;
		public final static boolean tiltMotorInvert = false;
		public final static boolean tiltEncoderSensorPhase = false;
		public static final int tiltContinuousCurrentLimit = 1; // amperes
		public static final int tiltPeakCurrentLimit = 5; // amperes
		public static final int tiltPeakCurrentDuration = 1000; // Milliseconds
		public static final boolean tiltEnableCurrentLimit = true;
		public static final double PeakOutput = 0.4;
		public static final int SLOT_0 = 0;
		public static final int tiltDefaultAcceptableError = 1;

		public static final double P_TILT = 1.5;
        public static final double I_TILT = 0.0002;
        public static final double D_TILT = 0.8;
		public final static double F_TILT = 0;

		public static final int absoluteEncoderZeroValue = 3015;
		public static final int tiltBallPickupPosition = 0;
		public static final int tiltBallShootingPosition = 500;
	}

}
