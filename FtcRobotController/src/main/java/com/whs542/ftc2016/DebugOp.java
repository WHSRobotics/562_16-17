package com.whs542.ftc2016;

//	TEST Opmode
//	 Throw your test code in here
//

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.whs542.lib.sensors.imu.*;

import org.FTC5866.lib.Bno055;
import org.FTC5866.lib.DataLogger;

public class DebugOp extends OpMode
{
    Bno055 imu;
    Quaternion imuData = new Quaternion();
    Vector imuEuler = new Vector(3);

    boolean     initComplete        = false;        // Flag to stop initialization

    Bno055.ScheduleItem     sensorData,fusionData,  // Data read schedules
            tempData,calibData;

    public void updateQuaternion(Quaternion q, double w, double x, double y, double z)
    {
        q.setW(w);
        q.setX(x);
        q.setY(y);
        q.setZ(z);
    }

    public void printQuaternion(Quaternion q)
    {
        telemetry.addData("W", q.getW());
        telemetry.addData("X", q.getX());
        telemetry.addData("Y", q.getY());
        telemetry.addData("Z", q.getZ());
    }

    public void printVector(Vector v)
    {
        telemetry.addData("Euler X", v.x());
        telemetry.addData("Euler Y", v.y());
        telemetry.addData("Euler Z", v.z());
    }

    public void init()
    {
        imu = new Bno055(hardwareMap, "imu");
        imu.init();
    }

    public void init_loop()
    {
        if (imu.isInitActive()) {
            imu.init_loop();
        } else if (!initComplete) {
            initComplete        = true;
            String status  = imu.isInitDone()?"OK":"Failed";
            telemetry.addData("Init", status);
        }
    }

    @Override
    public void start()
    {
        sensorData  = imu.startSchedule(Bno055.BnoPolling.SENSOR, 100);     // 10 Hz
        fusionData  = imu.startSchedule(Bno055.BnoPolling.FUSION, 33);      // 30 Hz
        tempData    = imu.startSchedule(Bno055.BnoPolling.TEMP, 200);       // 5 Hz
        calibData   = imu.startSchedule(Bno055.BnoPolling.CALIB, 250);      // 4 Hz
    }
    @Override
    public void loop()
    { // normal loop for drive motors
        imu.loop();
        updateQuaternion(imuData, imu.quaternionW(), imu.quaternionX(), imu.quaternionY(), imu.quaternionZ());
        imuData.normalize();
        imuEuler = imuData.toEuler();
        imuEuler.toDegrees();
        printVector(imuEuler);
        printQuaternion(imuData);
        telemetry.addData("ReqC", imu.requestCount());
        telemetry.addData("RspC", imu.responseCount());
        telemetry.addData("RdC", imu.readCount());
    }

    public void stop()
    {
        imu.stop();
    }
}