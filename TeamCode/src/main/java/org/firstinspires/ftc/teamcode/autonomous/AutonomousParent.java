package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.core.EasyOpenCVExample;
import org.firstinspires.ftc.teamcode.core.Mary;
import org.firstinspires.ftc.teamcode.library.DriveAuto;
import org.firstinspires.ftc.teamcode.library.DriveSensor;

public class AutonomousParent extends EasyOpenCVExample {

    // Environment variables for sub-classes (defaults to blue foundation)
    StartLocation startLocation = StartLocation.OUTSIDE;
    TeamColor teamColor = TeamColor.RED;
    RingPosition position = RingPosition.ONE;
    double POWER = 0.7;

    private DriveAuto drivetrain = new DriveAuto(Mary.driveMotors);
    private DriveSensor drivetrain2 = new DriveSensor(Mary.driveMotors);

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Mary.init(hardwareMap);

//        vuforiaInit();
        sensingInit();

        // Send diagnostics to user
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // waitForStart();

        while (!isStarted() && !isStopRequested()) {
            Mary.claw.grab();
            position = findPosition();
//            telemetry.addData("last location?: ", retrieveTranslation());
            telemetry.addData("Last Position: ", position);
        }

        moveToDrop1(); // need to speed up this step

        dropWobbleGoal();

        goToSecondWobbleGoal();

        grabSecondWobbleGoal();

        Mary.launcher.power(0.7, .65);
        moveToDrop2();

        importantStuffToDoAtEnd();

    }

    public void shoot(int times) {
        int sleepTime = 700;

        for(int i = 0; i < times; i++) {
            if (i != 0) {
                sleep(sleepTime);
            }
            Mary.launcher.TeleShoot(1);
            sleep(sleepTime);
            Mary.launcher.TeleShoot(0);
        }
    }

    public void moveToDrop2() {
        switch(position) {
            case FOUR: // C
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, 0.8); //
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY,55,POWER);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS,70,POWER);
                powerShot(1);
                drivetrain2.turn(170, POWER);
                drivetrain2.straighten(0, 0.6);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 40, POWER);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, POWER);
                dropWobbleGoal();
                break;
            case ONE: // B
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.8); //
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, POWER);
                break;
            case NONE: // A
                drivetrain2.straighten(180);
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 60, POWER);
                drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS,  200, POWER);
                if(Mary.sensors.getBack() < 140) {
                    drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.AWAY,  200, POWER);
                }
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER, 0.6); //
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, POWER);
                break;
        }
    }

    public void moveToDrop1() {
        switch(position) {
            case FOUR: // C
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 35, POWER);
                drivetrain2.straighten(0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 30, POWER);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, POWER);
                drivetrain2.straighten(0);
                break;
            case ONE: // B
                drivetrain2.straighten(0);
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 40, POWER);
                drivetrain2.straighten(0);
                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 200, POWER);
                if(Mary.sensors.getFront() < 200) {
                    drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 200, POWER);
                    drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER, 0.22); //
                } else {
                    drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.25); //
                }
                Mary.claw.out();
                drivetrain2.straighten(0);
                drivetrain2.moveAndTurn(95, POWER);
                break;
            case NONE: // A
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 50, POWER);
                drivetrain2.straighten(-0);
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER, 1.47); //1.42
//                drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS, 195, POWER);
//                if(Mary.sensors.getFront() < 200) {
//                    telemetry.addData("WALL: ", Mary.sensors.getFront());
//                    telemetry.update();
//                    drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 200, POWER);
//                    drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.2); //
//                } else {
//                    telemetry.addData("NORMAL: ", Mary.sensors.getFront());
//                    telemetry.update();
//                    drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.22); //
//                }
                drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY,  55, POWER);
                drivetrain2.straighten(0);
                break;
        }
    }

    public void dropWobbleGoal() {
        Mary.claw.out();
        sleep(750);

        Mary.claw.release();
        sleep(350);
        Mary.claw.in();
    }

    public void goToSecondWobbleGoal() {
        if(position == RingPosition.ONE) {
            drivetrain2.moveAndTurn(-90, POWER);
        } else if(position == RingPosition.FOUR) {
            drivetrain.move(DriveAuto.MoveDirection.FORWARD, 1, POWER); //
            drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.TOWARDS, 30, POWER);
            drivetrain2.straighten(0);
            drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 1.15); //
        }

        drivetrain2.straighten(0);
        // make distance below greater
        drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.TOWARDS, 115, POWER);
        if(position == RingPosition.FOUR) {
            drivetrain2.move(DriveSensor.Sensor.FRONT, DriveSensor.ReferenceDirection.AWAY, 60, POWER);
        }
        drivetrain2.move(DriveSensor.Sensor.LEFT, DriveSensor.ReferenceDirection.AWAY, 30, POWER);
        drivetrain.turn(DriveAuto.TurnDirection.LEFT, POWER, 1.05);
        drivetrain2.straighten(180);
        sleep(100);
        Mary.claw.out();
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 58, POWER); //60?
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 70, POWER);

        // Front = 25 in
        // Left = 26 in
    }

    public void grabSecondWobbleGoal() {
        Mary.claw.release();
        drivetrain2.straighten(180);
        sleep(100);
        drivetrain2.move(DriveSensor.Sensor.BACK, DriveSensor.ReferenceDirection.TOWARDS,45,0.2);//might go too far forward
        sleep(700);
        Mary.claw.grab();
        sleep(400);
        Mary.claw.in();
    }

    public void importantStuffToDoAtEnd() {
        switch(position) {
            case FOUR:
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.45); //
                break;
            case ONE:
                powerShot(3);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.4); //
                Mary.claw.out();
                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, POWER, .55); //
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.12); //
                dropWobbleGoal();
                sleep(500);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT,POWER,0.1); //
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, POWER, 0.3); //
                break;
            case NONE:
                powerShot(3);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, POWER, 0.30); //
                drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.TOWARDS, 70, POWER);
                drivetrain.turn(DriveAuto.TurnDirection.LEFT, POWER, 1);
                Mary.claw.out();
                sleep(400);
                dropWobbleGoal();
                sleep(700);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, POWER, 0.45); //
                drivetrain2.straighten(0);
                drivetrain.move(DriveAuto.MoveDirection.RIGHT, POWER, 0.2); //

                break;
        }
    }

    public void powerShot(int times) {
        drivetrain2.move(DriveSensor.Sensor.RIGHT, DriveSensor.ReferenceDirection.AWAY, 70, POWER);
        Mary.intake.inBelt();
        if(position == RingPosition.FOUR) {
            Mary.launcher.power(0.7, 0.7);
        } else {
            Mary.launcher.power(0.7, .65);
        }
        drivetrain2.straighten(180);
        sleep(100);
        if(times > 1) {
            drivetrain.turn(DriveAuto.TurnDirection.LEFT, 0.43, 0.24);
        } else {
            drivetrain.turn(DriveAuto.TurnDirection.LEFT, 0.43, 0.15);
        }
        for(int i = 0; i < times; i++) {
            if(i != 0) {
                drivetrain.turn(DriveAuto.TurnDirection.RIGHT, 0.25, 0.12);
            }
            shoot(1);
            if(times != 1)
                sleep(800);
        }
        Mary.intake.stop();
    }

    enum StartLocation {
        INSIDE,
        OUTSIDE
    }

    enum TeamColor {
        RED,
        BLUE
    }
}