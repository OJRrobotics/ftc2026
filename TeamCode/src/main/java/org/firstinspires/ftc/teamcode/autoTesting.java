package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCompatibilityManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.List;

@Autonomous(name="Testing")
public class autoTesting extends LinearOpMode {
    private AprilTagProcessor aprilTag;
    private static final boolean USE_WEBCAM = true;
    private VisionPortal visionPortal;
    double xoffset;
    DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
    DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
    DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
    DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");

    @Override
    public void runOpMode() throws InterruptedException {
        CameraCompatibilityManager.getInstance().addQuirk(0x046D, 0x0825, CameraCompatibilityManager.Quirk.AVOID_LIB_USB_RESET_DEVICE);



        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        initAprilTag();
        waitForStart();


        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (opModeIsActive()){
            frontLeft.setVelocity(5000);
            frontRight.setVelocity(-5000);
            backLeft.setVelocity(-5000);
            backRight.setVelocity(5000);

            centerOnTag();
            sleep(200);
        }

    }
    private boolean centerOnTag(){
        boolean b = false;
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;
            } else {
                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
            }
            xoffset = detection.ftcPose.x;
        }
        if (currentDetections.isEmpty()){
            b = false;

        }
        if (xoffset < 0){
            frontLeft.setVelocity(5000);
            frontRight.setVelocity(-5000);
            backLeft.setVelocity(-5000);
            backRight.setVelocity(5000);
        } else if (xoffset > 0){
            frontLeft.setVelocity(-5000);
            frontRight.setVelocity(5000);
            backLeft.setVelocity(5000);
            backRight.setVelocity(-5000);
        } else {
            while (xoffset == 0) {
                sleep(1000);
                if (xoffset == 0) {
                    b = true;
                }
            }
        }
        return b;
    }
    private void initAprilTag() {

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder().build();


        VisionPortal.Builder builder = new VisionPortal.Builder();

        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.enableLiveView(true);
        builder.setStreamFormat(VisionPortal.StreamFormat.MJPEG);
        builder.addProcessor(aprilTag);

        visionPortal = builder.build();
    }
}
