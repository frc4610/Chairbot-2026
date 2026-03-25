// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.ejml.dense.row.decomposition.qr.QRColPivDecompositionHouseholderColumn_DDRM;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */

  CommandXboxController m_Driver = new CommandXboxController(0);
  WPI_TalonSRX m_BackLeftDrive = new WPI_TalonSRX(10);
  WPI_TalonSRX m_FrontLeftDrive = new WPI_TalonSRX(11);
  WPI_TalonSRX m_FrontRightDrive = new WPI_TalonSRX(12);
  WPI_TalonSRX m_BackRightDrive = new WPI_TalonSRX(13);

  DifferentialDrive m_Drive;

  public Drivetrain() {

    m_FrontLeftDrive.setInverted(true);
    m_BackLeftDrive.setInverted(true);
    m_BackLeftDrive.set(TalonSRXControlMode.Follower, 11);
    m_BackRightDrive.set(TalonSRXControlMode.Follower, 12);

    m_Drive = new DifferentialDrive( m_FrontLeftDrive, m_FrontRightDrive);
  }

  public void curvatureDrive() {
    m_Drive.curvatureDrive(m_Driver.getLeftY(), m_Driver.getRightX(), true);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    curvatureDrive();
  }
}
