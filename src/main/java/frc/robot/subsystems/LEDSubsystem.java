// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.FireAnimation;
import com.ctre.phoenix.led.LarsonAnimation;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.RgbFadeAnimation;
import com.ctre.phoenix.led.SingleFadeAnimation;
import com.ctre.phoenix.led.StrobeAnimation;
import com.ctre.phoenix.led.TwinkleAnimation;
import com.ctre.phoenix.led.TwinkleAnimation.TwinklePercent;
import com.ctre.phoenix.led.TwinkleOffAnimation;
import com.ctre.phoenix.led.TwinkleOffAnimation.TwinkleOffPercent;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class LEDSubsystem extends SubsystemBase {
  /** Creates a new LEDSubsystem. */
  private CANdle candleLED;
  private final int LedCount = 38;
  private Animation toAnimate = null;
  public enum AnimationTypes {
    ColorFlow,
    Fire,
    Larson,
    Rainbow,
    RgbFade,
    SingleFade,
    Strobe,
    Twinkle,
    TwinkleOff,
    SetAll
  }
  private AnimationTypes currentAnimation;
  public LEDSubsystem() {
    candleLED =  new CANdle(Constants.CANdleConstants.candlePort);
    CANdleConfiguration configAll = new CANdleConfiguration();
    configAll.statusLedOffWhenActive = false;
    configAll.disableWhenLOS = false;
    configAll.stripType = LEDStripType.GRB;
    configAll.brightnessScalar = 0.1;
    configAll.vBatOutputMode = VBatOutputMode.Modulated;
    candleLED.configAllSettings(configAll, 100);
    //candleLED.animate(new FireAnimation(0.5, 0.7, 38, 0.7, 0.5));
  }

  public void incrementAnimation() {
    switch(currentAnimation) {
        case ColorFlow: changeAnimation(AnimationTypes.Fire); break;
        case Fire: changeAnimation(AnimationTypes.Larson); break;
        case Larson: changeAnimation(AnimationTypes.Rainbow); break;
        case Rainbow: changeAnimation(AnimationTypes.RgbFade); break;
        case RgbFade: changeAnimation(AnimationTypes.SingleFade); break;
        case SingleFade: changeAnimation(AnimationTypes.Strobe); break;
        case Strobe: changeAnimation(AnimationTypes.Twinkle); break;
        case Twinkle: changeAnimation(AnimationTypes.TwinkleOff); break;
        case TwinkleOff: changeAnimation(AnimationTypes.ColorFlow); break;
        case SetAll: changeAnimation(AnimationTypes.ColorFlow); break;
    }
}
public void decrementAnimation() {
    switch(currentAnimation) {
        case ColorFlow: changeAnimation(AnimationTypes.TwinkleOff); break;
        case Fire: changeAnimation(AnimationTypes.ColorFlow); break;
        case Larson: changeAnimation(AnimationTypes.Fire); break;
        case Rainbow: changeAnimation(AnimationTypes.Larson); break;
        case RgbFade: changeAnimation(AnimationTypes.Rainbow); break;
        case SingleFade: changeAnimation(AnimationTypes.RgbFade); break;
        case Strobe: changeAnimation(AnimationTypes.SingleFade); break;
        case Twinkle: changeAnimation(AnimationTypes.Strobe); break;
        case TwinkleOff: changeAnimation(AnimationTypes.Twinkle); break;
        case SetAll: changeAnimation(AnimationTypes.ColorFlow); break;
    }
  }
  public void setColors() {
    changeAnimation(AnimationTypes.SetAll);
  }

  public void changeAnimation(AnimationTypes toChange) {
    currentAnimation = toChange;
    
    switch(toChange)
    {
         
        case ColorFlow:
            toAnimate = new ColorFlowAnimation(237, 20, 70, 0, 0.7, LedCount, Direction.Forward);
            break;
        case Fire:
            toAnimate = new FireAnimation(0.5, 0.7, LedCount, 0.7, 0.5);
            break;
        case Larson:
            toAnimate = new LarsonAnimation(0, 255, 46, 0, 1, LedCount, BounceMode.Front, 3);
            break;
        case Rainbow:
            toAnimate = new RainbowAnimation(1, 0.1, LedCount);
            break;
        case RgbFade:
            toAnimate = new RgbFadeAnimation(0.7, 0.4, LedCount);
            break;
        case SingleFade:
            toAnimate = new SingleFadeAnimation(50, 2, 200, 0, 0.5, LedCount);
            break;
        case Strobe:
            toAnimate = new StrobeAnimation(240, 10, 180, 0, 98.0 / 256.0, LedCount);
            break;
        case Twinkle:
            toAnimate = new TwinkleAnimation(30, 70, 60, 0, 0.4, LedCount, TwinklePercent.Percent6);
            break;
        case TwinkleOff:
            toAnimate = new TwinkleOffAnimation(70, 90, 175, 0, 0.8, LedCount, TwinkleOffPercent.Percent100);
            break;
        case SetAll:
            toAnimate = null;
            break;
     }
    System.out.println("Changed to " + currentAnimation.toString());
    candleLED.animate(toAnimate);
  }

  public void setLEDBlue(){
    candleLED.setLEDs(10,10,200);
    candleLED.modulateVBatOutput(0.9);
  }

  public void setLEDRed(){
    candleLED.setLEDs(200,10,10);
    candleLED.modulateVBatOutput(0.9);
  }

  public void setLEDWhite(){
    candleLED.setLEDs(255,255,255);
    candleLED.modulateVBatOutput(0.9);
  }


  /* Wrappers so we can access the CANdle from the subsystem */
  public double getVbat() { return candleLED.getBusVoltage(); }
  public double get5V() { return candleLED.get5VRailVoltage(); }
  public double getCurrent() { return candleLED.getCurrent(); }
  public double getTemperature() { return candleLED.getTemperature(); }
  public void configBrightness(double percent) { candleLED.configBrightnessScalar(percent, 0); }
  public void configLos(boolean disableWhenLos) { candleLED.configLOSBehavior(disableWhenLos, 0); }
  public void configLedType(LEDStripType type) { candleLED.configLEDType(type, 0); }
  public void configStatusLedBehavior(boolean offWhenActive) { candleLED.configStatusLedState(offWhenActive, 0); }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }
}
