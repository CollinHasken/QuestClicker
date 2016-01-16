package com.tophattiger.Helper.Data;

import java.util.Calendar;

/**
 * Created by Collin on 5/27/2015.
 */
public class DataHolder {
    public static double timeDif;
    public static float width,height;
    public static boolean open;
    public static int hBarWidth,pBarWidth,vBarWidth,helperAmount;
    public static Calendar currentTime, pastTime;
    public static String hundredCombo = "CgkIiLfOgJEBEAIQAQ", hundredEnemies = "CgkIiLfOgJEBEAIQAg", tenQeusts = "CgkIiLfOgJEBEAIQAw", thousandTaps = "CgkIiLfOgJEBEAIQCA", restart = "CgkIiLfOgJEBEAIQBQ";
    public static String nina = "CgkIiLfOgJEBEAIQBA", guildOwner = "CgkIiLfOgJEBEAIQBw";

    /**
     * Initialize the current and past time calendar dates.
     */
    public static void Initialize(){
        currentTime = Calendar.getInstance();
        pastTime = Calendar.getInstance();
    }
}
