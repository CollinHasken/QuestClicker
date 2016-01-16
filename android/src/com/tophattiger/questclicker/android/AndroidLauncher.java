package com.tophattiger.questclicker.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.tophattiger.Helper.Controllers.AdsController;
import com.tophattiger.questclicker.QuestClicker;

import util.com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements AdsController, GameHelper.GameHelperListener {

	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-7630956469794912/8936973184";
	private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-7630956469794912/5563410782";
	private GameHelper gameHelper;
	private InterstitialAd interstitialAd;
	AdView bannerAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		View gameView = initializeForView(new QuestClicker(this), config);
		setupAds();
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layout.addView(bannerAd, params);
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);

		 interstitialAd.setAdListener(new AdListener() {
             @Override
             public void onAdLoaded() {
                 showOrLoadInterstitial();
             }

             @Override
             public void onAdClosed() {
             }
         });
		setContentView(layout);
		if (gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
		}
		gameHelper.setup(this);
	}

    public void setupAds(){
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setBackgroundColor(0xff000000);
		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);
		bannerAd.setScaleY(.97f);
	}

	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.VISIBLE);
                AdRequest.Builder builder = new AdRequest.Builder().addTestDevice("7443B1064ADDC626ED40DC796A34C161");
                //AdRequest.Builder builder = new AdRequest.Builder();
                AdRequest ad = builder.build();
                bannerAd.loadAd(ad);
            }
        });
	}

	@Override
	public void showOrLoadInterstitial() {
		try {
			 runOnUiThread(new Runnable() {
				 public void run() {
					 if (interstitialAd.isLoaded()) {
						 interstitialAd.show();
					 }
					 else {
						 AdRequest.Builder interstitialRequest = new AdRequest.Builder().addTestDevice("7443B1064ADDC626ED40DC796A34C161");
						 //AdRequest.Builder interstitialRequest = new AdRequest.Builder();
						 AdRequest ad = interstitialRequest.build();
						 interstitialAd.loadAd(ad);
					 }
				 }
			 });
		} catch (Exception e) {
		}
	}

	@Override
	public void hideBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
	}

    @Override
    public void rateGame()
    {
        String str = "https://play.google.com/store/apps/details?id=com.tophattiger.questclicker.android";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}

	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
        Games.Achievements.unlockImmediate(gameHelper.getApiClient(),achievementId);
    }

	@Override
	public void incrementAchievementGPGS(String achievementID, int amount) {
		Games.Achievements.increment(gameHelper.getApiClient(),achievementID,amount);
	}

    @Override
    public void stepAchievementGPGS(String achievementID, int amount) {
        Games.Achievements.setSteps(gameHelper.getApiClient(),achievementID,amount);
    }

    @Override
	public void getAchievementsGPGS() {
		if (gameHelper.isSignedIn()) {
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
		}
		else if (!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}

	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}
}
