package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ThreadPool;

@TeleOp(name="My First Op Mode 2", group="Practice-Bot")

public class FirstOpMode extends LinearOpMode {

    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
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

        //initialize classes
        Move_robot move = new Move_robot("move", leftFrontMotor, rightFrontMotor, leftBackMotor, rightBackMotor);

        //reverse motors
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //set all motors to zero
        leftFrontMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightBackMotor.setPower(0);

        //Wait for the game to start (driver presses PLAY)
        waitForStart();
        try {
// run until the end of the match (driver presses STOP)
            //start classes
            move.start();

            while(opModeIsActive()) {
                //set joystick buttons
                move.setXY(gamepad1.left_stick_x, -gamepad1.left_stick_y);
                move.rotateRobot(gamepad1.left_bumper, gamepad1.right_bumper);

                waitForTick(40);
            }
        } catch (InterruptedException exc) {

            return;

        } finally {
            //exterminate classes
            move.exterminate();

            //set all motors to zero power
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
        }
    }
}
