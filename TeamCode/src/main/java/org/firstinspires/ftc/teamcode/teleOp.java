package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;



@TeleOp(name="TeleOp")
public class teleOp extends LinearOpMode {

    double udJoyFixed = 0.0;
    double lrJoyFixed = 0.0;

    public static double map(float value) {
        return ((value + 1) / 2) * (15200) - 7600;
    }

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while (opModeIsActive()){
            udJoyFixed = map(gamepad1.right_stick_y);
            lrJoyFixed = map(gamepad1.right_stick_x);

            if (!(gamepad1.left_stick_x == 0)){

                if (gamepad1.left_trigger_pressed) {
                    frontLeft.setVelocity(map(gamepad1.left_stick_x) * 0.5);
                    frontRight.setVelocity(map(gamepad1.left_stick_x) * 0.5);
                    backLeft.setVelocity(map(gamepad1.left_stick_x) * 0.5);
                    backRight.setVelocity(map(gamepad1.left_stick_x) * 0.5);
                } else {
                    frontLeft.setVelocity(map(gamepad1.left_stick_x));
                    frontRight.setVelocity(map(gamepad1.left_stick_x));
                    backLeft.setVelocity(map(gamepad1.left_stick_x));
                    backRight.setVelocity(map(gamepad1.left_stick_x));
                }

            } else {

                if (gamepad1.left_trigger_pressed){
                    frontLeft.setVelocity((udJoyFixed - lrJoyFixed) * 0.5);
                    frontRight.setVelocity((-udJoyFixed - lrJoyFixed) * 0.5);
                    backLeft.setVelocity((udJoyFixed + lrJoyFixed) * 0.5);
                    backRight.setVelocity((-udJoyFixed + lrJoyFixed) * 0.5);
                } else {
                    frontLeft.setVelocity(udJoyFixed - lrJoyFixed);
                    frontRight.setVelocity((-udJoyFixed - lrJoyFixed));
                    backLeft.setVelocity(udJoyFixed + lrJoyFixed);
                    backRight.setVelocity(-udJoyFixed + lrJoyFixed);
                }
            }

            if (gamepad1.backWasPressed()){
                frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                sleep(10);
                frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    }
}
