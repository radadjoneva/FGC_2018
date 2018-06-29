package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;

public class Lift_mechanism extends Thread {
    private Thread t;
    private String threadName;
    private CRServo liftServo;
    private boolean UP;
    private boolean DOWN;
    private boolean exterminate;

    //constructor
    Lift_mechanism (String name, CRServo servo) {
        threadName = name;
        liftServo = servo;
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

    public void setUpDown(boolean up, boolean down) {
        UP = up;
        DOWN = down;
    }

    public void run() {
        try {
            while(!exterminate){
                if(UP) {
                    liftServo.setPower(1);
                } else if(DOWN) {
                    liftServo.setPower(-1);
                } else {
                    liftServo.setPower(0);
                }
            }
            liftServo.setPower(0);
        } catch (Exception e) {
            liftServo.setPower(0);
        }
    }
}
