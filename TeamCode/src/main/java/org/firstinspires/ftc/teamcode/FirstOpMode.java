package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ThreadPool;

@TeleOp(name="My First Op Mode", group="Practice-Bot")

public class FirstOpMode extends LinearOpMode {
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
    private CRServo liftServo;
    private ElapsedTime period = new ElapsedTime();

    private void waitForTick(long periodMs) throws java.lang.InterruptedException {
        long remaining = periodMs - (long)period.milliseconds();
// sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            Thread.sleep(remaining);
        }
// Reset the cycle clock for the next pass.
        period.reset();
    }

    public void runOpMode() throws InterruptedException {
        //variables

        //map motors
        leftFrontMotor = hardwareMap.dcMotor.get("frontLeft");
        rightFrontMotor = hardwareMap.dcMotor.get("frontRight");
        leftBackMotor = hardwareMap.dcMotor.get("backLeft");
        rightBackMotor = hardwareMap.dcMotor.get("backRight");
        liftServo = hardwareMap.crservo.get("liftServo");

        //initialize classes
        Move_robot move = new Move_robot("move",leftFrontMotor,rightFrontMotor,leftBackMotor,rightBackMotor);
        Lift_mechanism lift = new Lift_mechanism("lift", liftServo);


        //reverse motors
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //set all motors to zero
        leftFrontMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightBackMotor.setPower(0);
        liftServo.setPower(0);

        //Wait for the game to start (driver presses PLAY)
        waitForStart();
        try {
// run until the end of the match (driver presses STOP)
            //start classes
            move.start();
            lift.start();

            while (opModeIsActive()) {
                //set joystick buttons
                move.setXY(gamepad1.left_stick_x,-gamepad1.left_stick_y);
                move.rotateRobot(gamepad1.left_bumper, gamepad1.right_bumper);
                lift.setUpDown(gamepad2.dpad_up, gamepad2.dpad_down);

                waitForTick(40);
            }

        } catch (InterruptedException exc) {

            return;

        } finally {
            //exterminate classes
            move.exterminate();
            lift.exterminate();

            //set all motors to zero power
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
            liftServo.setPower(0);
        }
    }
}
