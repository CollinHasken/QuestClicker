package com.tophattiger.questclicker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tophattiger.Helper.Controllers.AdsController;
import com.tophattiger.questclicker.QuestClicker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 990; config.height = 540;
		new LwjglApplication(new QuestClicker(new AdsController() {
			@Override
			public void showBannerAd() {

			}

			@Override
			public void rateGame() {

			}

            @Override
            public void stepAchievementGPGS(String achievementID, int amount) {

            }

            @Override
			public void hideBannerAd() {

			}

			@Override
			public void showOrLoadInterstitial() {

			}

			@Override
			public boolean getSignedInGPGS() {
				return false;
			}

			@Override
			public void loginGPGS() {

			}

			@Override
			public void unlockAchievementGPGS(String achievementID) {

			}

			@Override
			public void getAchievementsGPGS() {

			}

			@Override
			public void incrementAchievementGPGS(String achievementID, int amount) {

			}
		}), config);
	}
}
