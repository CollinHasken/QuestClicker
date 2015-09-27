package com.tophattiger.Helper.Data;

import com.badlogic.gdx.graphics.g2d.Animation;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by Collin on 5/27/2015.
 */
public class DataHolder {
    public static double timeDif;
    public static float width,height;
    public static boolean open;
    public static int hBarWidth,pBarWidth,helperAmount;
    public static Calendar currentTime, pastTime;

    public static void Initialize(){
        currentTime = Calendar.getInstance();
        pastTime = Calendar.getInstance();
    }
}
