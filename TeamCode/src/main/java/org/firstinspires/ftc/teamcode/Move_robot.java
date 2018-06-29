package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Move_robot extends Thread {
    private Thread t;
    private String threadName;
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
    private double X;
    private double Y;
    private double powerLF;
    private double powerLB;
    private boolean leftBumper;
    private boolean rightBumper;
    private boolean exterminate;

    //constructor
    Move_robot (String name, DcMotor motorLF, DcMotor motorRF, DcMotor motorLB, DcMotor motorRB){
        threadName = name;
        leftFrontMotor = motorLF;
        rightFrontMotor = motorRF;
        leftBackMotor = motorLB;
        rightBackMotor = motorRB;
    }

    public void start(){
        if (t == null){
            t = new Thread(this, threadName);
            exterminate = false;
            t.start();
        }
    }

    public void exterminate(){
        exterminate = true;
    }

    public void setXY(double x, double y){
        X = x;
        Y = y;
    }

    public void rotateRobot(boolean LBumper, boolean RBumper){
        leftBumper = LBumper;
        rightBumper = RBumper;
        if (leftBumper) {
            leftFrontMotor.setPower(1);
            rightFrontMotor.setPower(-1);
            leftBackMotor.setPower(1);
            rightBackMotor.setPower(-1);
        }
        if (rightBumper) {
            leftFrontMotor.setPower(-1);
            rightFrontMotor.setPower(1);
            leftBackMotor.setPower(-1);
            rightBackMotor.setPower(1);
        }
        else{
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
        }
    }

    public void run(){
        try {
            while(!exterminate) {
                powerLF = (X + Y);
                powerLB = (Y - X);
                leftFrontMotor.setPower(powerLF);
                rightFrontMotor.setPower(powerLB);
                leftBackMotor.setPower(powerLB);
                rightBackMotor.setPower(powerLF);
            }
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
        } catch (Exception e){
            leftFrontMotor.setPower(0);
            rightFrontMotor.setPower(0);
            leftBackMotor.setPower(0);
            rightBackMotor.setPower(0);
        }

    }
}
