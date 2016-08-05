package com.whs542.ftc2016;

import com.whs542.ftc2016.subsys.WHSRobot;

/**
 * Created by Lucy on 1/16/2016.
 */
public class Dependencies
{
    private boolean linearSlidesExtended;
    //private boolean boxOpen;
    //private boolean boxExtended;
    //private boolean intake;
    WHSRobot rob;

    public Dependencies()
    {
        //linearSlidesExtended = (rob.slides.getExtensionLength() != 0);
        //bocOpen = rob.box.getDoorState().equals("Open");
        //boxExtended = (!rob.box.boxFullyRetracted());
        //intake = (rob.intake.INTAKE_EXTENSION_STATE != 0);

        if(!linearSlidesExtended) //If linear slides are not extended
        {
            //boxOpen = false; //box should not open
            //rob.box.closeDoor();

            //boxExtended = false; //box should not be extended either
            //rob.box.setExtensionSpeed(0.0);

            //intake = true;
            //rob.intake.runIntake(true, false);

        }
        else //linear slides extended
        {
            //intake = false;
            //rob.intake.runIntake(false, false);
        }
    }


}
