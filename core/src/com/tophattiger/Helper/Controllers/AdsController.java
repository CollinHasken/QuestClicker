package com.tophattiger.Helper.Controllers;

/**
 * Created by Collin on 1/3/2016.
 */
public interface AdsController {
    public void showBannerAd();
    public void hideBannerAd();
    public void showOrLoadInterstitial();
    public boolean getSignedInGPGS();
    public void loginGPGS();
    public void unlockAchievementGPGS(String achievementID);
    public void getAchievementsGPGS();
    public void incrementAchievementGPGS(String achievementID, int amount);
    public void rateGame();
    public void stepAchievementGPGS(String achievementID, int amount);
}
