package com.whs542.ftc2016;

import com.whs542.ftc2016.CameraPreview;
import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

public class CameraOp extends OpMode
{
    //Camera Object (native to android)
    private Camera camera; 
    //Camera Preview Object (made)
    public CameraPreview preview;
    //Bitmap object
    public Bitmap image;
    //Height and width parameters for preview
    private int width;
    private int height;
    //YuvImage object (android)
    private YuvImage yuvImage = null;
    private int looped = 0;
    private String data;

    //Grabs RGB values from a 3 byte number
    private int red(int pixel)
    {
        return (pixel >> 16) & 0xff;
    }

    private int green(int pixel)
    {
        return (pixel >> 8) & 0xff;
    }

    private int blue(int pixel) {
        return pixel & 0xff;
    }

    //onPreviewFrame - Used somewhere, but I don't know where
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback()
    {
        public void onPreviewFrame(byte[] data, Camera camera)
        {
            Camera.Parameters parameters = camera.getParameters();
            width = parameters.getPreviewSize().width;
            height = parameters.getPreviewSize().height;
            yuvImage = new YuvImage(data, ImageFormat.NV21, width, height, null);
            looped += 1;
        }
    };

    //Turns an image into a bitmap
    private void convertImage()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 0, out);
        byte[] imageBytes = out.toByteArray();
        image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }


    @Override
    public void init()
    {
        //grab the front facing camera
        camera = ((FtcRobotControllerActivity)hardwareMap.appContext).camera;
        //
        camera.setPreviewCallback(previewCallback);

        //
        Camera.Parameters parameters = camera.getParameters();
        data = parameters.flatten();

        ((FtcRobotControllerActivity) hardwareMap.appContext).initPreview(camera, this, previewCallback);
    }

    public int highestColor(int red, int green, int blue)
    {
        int[] color = {red,green,blue};
        int value = 0;
        for (int i = 1; i < 3; i++) {
            if (color[value] < color[i]) {
                value = i;
            }
        }
        return value;
    }

    @Override
    public void loop()
    {
        if (yuvImage != null) {
            int redValue = 0;
            int blueValue = 0;
            int greenValue = 0;
            convertImage();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = image.getPixel(x, y);
                    redValue += red(pixel);
                    blueValue += blue(pixel);
                    greenValue += green(pixel);
                }
            }
            int color = highestColor(redValue, greenValue, blueValue);
            String colorString = "";
            switch (color) {
                case 0:
                    colorString = "RED";
                    break;
                case 1:
                    colorString = "GREEN";
                    break;
                case 2:
                    colorString = "BLUE";
            }
            telemetry.addData("Color:", "Color detected is: " + colorString);
        }
        telemetry.addData("Looped","Looped " + Integer.toString(looped) + " times");
        Log.d("DEBUG:",data);
    }
}