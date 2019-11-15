package org.firstinspires.ftc.atomicnarwhals.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.atomicnarwhals.hardware.MecanumDrivetrain;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="BlueBuildSide", group="Linear Opmode")
public class BlueBuildSide extends LinearOpMode {
    
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mMecanumDrivetrain = null;
    private CRServo mDragger = null;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        mMecanumDrivetrain = new MecanumDrivetrain(telemetry, frontLeft, frontRight, backLeft, backRight);
        mDragger  = hardwareMap.get(CRServo.class, "dragger");

        // wait for start
        waitForStart();
        runtime.reset();

        autonomousByEncoder();
    }

    private void autonomousByEncoder(){
        double autonomousSpeed = 0.25;

        //1. Strafe Right 35 Inches
        mMecanumDrivetrain.driveByDistance(35, MecanumDrivetrain.DIRECTION_STRAFE_RIGHT, autonomousSpeed);
        while (opModeIsActive() && mMecanumDrivetrain.isMoving()){
            telemetry.addData("Step 1", "Strafe Right 35\"");
            telemetry.update();
        }
        mMecanumDrivetrain.stop();

        //2. Drive Reverse 29 Inches
        mMecanumDrivetrain.driveByDistance(29, MecanumDrivetrain.DIRECTION_REVERSE, autonomousSpeed);
        while (opModeIsActive() && mMecanumDrivetrain.isMoving()){
            telemetry.addData("Step 2", "Drive Reverse 29\"");
            telemetry.update();
        }
        mMecanumDrivetrain.stop();

        //3. Lower Dragger Arm to Grab Foundation
        grabFoundation();

        //4. Drive Forward 40 Inches
        mMecanumDrivetrain.driveByDistance(40, MecanumDrivetrain.DIRECTION_FORWARD, autonomousSpeed);
        while (opModeIsActive() && mMecanumDrivetrain.isMoving()){
            telemetry.addData("Step 4", "Drive Forward 40\"");
            telemetry.update();
        }
        mMecanumDrivetrain.stop();

        //5. Raise Dragger Arm to Release Foundation
        releaseFoundation();

        //6. Strafe Left 55 Inches
        mMecanumDrivetrain.driveByDistance(55, MecanumDrivetrain.DIRECTION_STRAFE_LEFT, autonomousSpeed);
        while (opModeIsActive() && mMecanumDrivetrain.isMoving()){
            telemetry.addData("Step 6", "Strafe Left 55\"");
            telemetry.update();
        }
        mMecanumDrivetrain.stop();
    }

    private void autonomousByTime(){
        //Strafe to Position
        mMecanumDrivetrain.strafeRightByTime(this,.3,2.4);

        //Drive to foundation
        mMecanumDrivetrain.backwardByTime(this, .5,.68);
        mMecanumDrivetrain.stop();
        sleep(10);

        //Grab Foundation
        grabFoundation();

        //Pull into Build Area
        mMecanumDrivetrain.forwardByTime(this, .5, 1.4);
        mMecanumDrivetrain.stop();
        sleep(10);

        //Release Foundation
        releaseFoundation();

        //Park under Bridge
        mMecanumDrivetrain.strafeLeftByTime(this,.5,1.5);
        mMecanumDrivetrain.stop();
    }

    private void releaseFoundation() {
        mDragger.setPower(-1);
        sleep(4000);
        mDragger.setPower(0);
    }

    private void grabFoundation() {
        mDragger.setPower(1);
        sleep(4000);
    }
}
