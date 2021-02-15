package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.core.Mary;

import java.util.ArrayList;

public class DriveSensor {

    private ArrayList<DcMotor> motors;

    public DriveSensor(ArrayList<DcMotor> motors) {
        this.motors = motors;
    }

    public void move(MoveDirection direction, ReferenceDirection reference, int distance, double power) {
        switch(direction) {
            case FORWARD:
                while(Mary.sensors.getFrontRight() > distance && Mary.sensors.getFrontLeft() > distance) {
                    double motorPower = power;
                    DriveStyle.MecanumArcade(Mary.driveMotors, motorPower, 0, 1, 0);
                }
                break;
            case BACKWARD:
                while(Mary.sensors.getFrontRight() < distance && Mary.sensors.getFrontLeft() < distance) {
                    double motorPower = power;
                    DriveStyle.MecanumArcade(Mary.driveMotors, motorPower, 0, -1, 0);
                }
                break;
            case RIGHT:
                switch(reference) {
                    case TOWARDS:
                        while(Mary.sensors.getRight() > distance) {
                            double motorPower = power;
                            DriveStyle.MecanumArcade(Mary.driveMotors, motorPower, 1, 0, 0);
                        }
                        break;
                    case AWAY:
                        while(Mary.sensors.getRight() < distance) {
                            double motorPower = power;
                            DriveStyle.MecanumArcade(Mary.driveMotors, motorPower, -1, 0, 0);
                        }
                        break;
                }
                break;
            case LEFT:
                switch(reference) {
                    case TOWARDS:
                        while(Mary.sensors.getLeft() > distance) {
                            double motorPower = power;
                            DriveStyle.MecanumArcade(Mary.driveMotors, motorPower, -1, 0, 0);
                        }
                        break;
                    case AWAY:
                        while(Mary.sensors.getLeft() < distance) {
                            double motorPower = power;
                            DriveStyle.MecanumArcade(Mary.driveMotors, motorPower, 1, 0, 0);
                        }
                        break;
                }
                break;
        }
        DriveStyle.MecanumArcade(Mary.driveMotors, 0, 0, 0 ,0);
    }

    // don't use negative degrees, and you life will be easier
    public void turn(TurnDirection direction, int degrees) {
        double curr_heading = Mary.imu.getHeading();
        double new_heading = curr_heading + (degrees);

        while(Math.abs(Mary.imu.getHeading() - new_heading) > 5) {
            if(Mary.imu.getHeading() - new_heading < 0) {
                DriveStyle.MecanumArcade(motors, -1, 0,0,1);
            } else {
                DriveStyle.MecanumArcade(motors, 1, 1,0,1);
            }
        }
        DriveStyle.MecanumArcade(Mary.driveMotors, 0, 0, 0 ,0);
    }

    public void straighten(double heading) {
        while(Math.abs(Mary.imu.getHeading() - heading) > 5) {
            if(Mary.imu.getHeading() - heading < 0) {
                DriveStyle.MecanumArcade(motors, -0.2, 0,0,1);
            } else {
                DriveStyle.MecanumArcade(motors, 0.2, 1,0,1);
            }
        }
        DriveStyle.MecanumArcade(Mary.driveMotors, 0, 0, 0 ,0);
    }

    public enum MoveDirection {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    public enum TurnDirection {
        RIGHT,
        LEFT
    }

    public enum ReferenceDirection {
        TOWARDS,
        AWAY
    }
}