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


        return ((value + 1) / 2) * (2000) - 1000;
    }




    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        while (opModeIsActive()){
            udJoyFixed = map(gamepad1.right_stick_y);
            lrJoyFixed = map(gamepad1.right_stick_x);

            frontLeft.setVelocity(-udJoyFixed + lrJoyFixed);
            frontRight.setVelocity(-udJoyFixed + lrJoyFixed);
            backLeft.setVelocity(udJoyFixed - lrJoyFixed);
            backRight.setVelocity(-udJoyFixed - lrJoyFixed);
        }
    }
}
