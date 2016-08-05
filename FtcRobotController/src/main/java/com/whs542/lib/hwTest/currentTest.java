package com.whs542.lib.hwTest;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.whs542.lib.sensors.CurrentACS711EX;

import java.lang.Override;

public class currentTest extends OpMode
{
    CurrentACS711EX mag;   

    @Override
    public void init()
    {
        mag = new CurrentACS711EX(hardwareMap, 0);
    }

    @Override
    public void loop()
    {
        telemetry.addData("mag flux" , mag.getRawValue());
    }

    @Override
    public void stop()
    {

    }
}