package com.whs542.ftc2016.subsys;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.whs542.lib.Alliance;

public class WHSRobot
{
    public Drive drive;
    public LinearSlides slides;

    public WHSRobot(HardwareMap map, Alliance side)
    {
        drive = new Drive(map);
        slides = new LinearSlides(map, side);
    }
}