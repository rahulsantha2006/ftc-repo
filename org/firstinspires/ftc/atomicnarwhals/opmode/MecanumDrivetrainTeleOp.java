package org.firstinspires.ftc.atomicnarwhals.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.atomicnarwhals.hardware.Arm;
import org.firstinspires.ftc.atomicnarwhals.hardware.DraggerServo;
import org.firstinspires.ftc.atomicnarwhals.hardware.IntakeWheels;
import org.firstinspires.ftc.atomicnarwhals.hardware.MecanumDrivetrain;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Mecanum 1", group="Linear Opmode")
public class MecanumDrivetrainTeleOp extends LinearOpMode {

    private static final double THROTTLE = 0.35;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mMecanumDrivetrain = null;

    @Override
    public void runOpMode() {

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        mMecanumDrivetrain = new MecanumDrivetrain(telemetry, frontLeft, frontRight, backLeft, backRight);

        CRServo dragger  = hardwareMap.get(CRServo.class, "dragger");
        DraggerServo draggerServo = new DraggerServo(dragger);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {

            /* Gamepad 1 */
            mMecanumDrivetrain.drive(gamepad1.left_stick_x,-gamepad1.left_stick_y, gamepad1.right_stick_x, THROTTLE);

            if (gamepad1.x) {
                draggerServo.moveDown();
            }
            else if (gamepad1.y) {
                draggerServo.moveUp();
            }
            else {
                draggerServo.stop ();
            }
        }

        telemetry.addData("MecanumDrivetrainTeleOp", "Stopping");
        mMecanumDrivetrain.stop();
    }
}
