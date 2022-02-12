package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.concurrent.CopyOnWriteArraySet;


public class ImageDetector extends OpenCvPipeline {
    private Mat workingMatrix = new Mat();
    public static String position = "FIRST";
    public static double firstT, secondT, thirdT;
    public int rows = 720, cols = 1080;

    int HEIGHT = 150, WIDTH = 150;
    Point p1ss = new Point(0  , 400),
          p2ss = new Point(400, 400),
          p3ss = new Point(800, 400);


    Point p1dj = new Point(p1ss.x + HEIGHT, p1ss.y + WIDTH),
          p2dj = new Point(p2ss.x + HEIGHT, p2ss.y + WIDTH),
          p3dj = new Point(p3ss.x + HEIGHT, p3ss.y + WIDTH);

    Mat posOne, posTwo, posThree;

    public ImageDetector() {}

    @Override
    public void init(Mat firstFrame)
    {
        posOne = firstFrame.submat(new Rect(p1ss, p1dj));
        posTwo = firstFrame.submat(new Rect(p2ss, p2dj));
        posThree = firstFrame.submat(new Rect(p3ss, p3dj));
    }
    @Override
    public final Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if(workingMatrix.empty()) {
            return input;
        }
//        posOne = input.submat(new Rect(p1ss, p1dj));
//        posTwo = input.submat(new Rect(p2ss, p2dj));
//        posThree = input.submat(new Rect(p3ss, p3dj));
//        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2XYZ);
//        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_XYZ2BGR);

        Imgproc.rectangle(workingMatrix, p1ss, p1dj, new Scalar(255, 0, 0), 4);
        Imgproc.rectangle(workingMatrix, p2ss, p2dj, new Scalar(0, 255, 0), 4);
        Imgproc.rectangle(workingMatrix, p3ss, p3dj, new Scalar(0, 0, 255), 4);

        firstT = HEIGHT*WIDTH*255 - Core.sumElems(posOne).val[2];
        secondT = HEIGHT*WIDTH*255 - Core.sumElems(posTwo).val[2];
        thirdT = HEIGHT*WIDTH*255 - Core.sumElems(posThree).val[2];

        double firstmax = Math.max(firstT, secondT),
               lastmax = Math.max(firstmax, thirdT);

        if(lastmax == firstT) position = "FIRST";
        else if(lastmax == secondT) position = "SECOND";
        else position = "THIRD";

        return workingMatrix;
    }
    public String getPosition()
    {
        return position;
    }
    public double getFirstT()
    {
        return firstT;
    }
    public double getSecondT()
    {
        return secondT;
    }
    public double getThirdT() { return thirdT; }

}
