package com.nullcognition.yaatc.di.application;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.app.Application;
import android.content.Context;

import com.nullcognition.yaatc.di.activity.ActivityComponent;
import com.nullcognition.yaatc.di.activity.ActivityModule;
import com.nullcognition.yaatc.di.activity.BaseActivity;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class YAATCApp extends Application{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "wwklI2sV6NZFSQSw4vSBOsYHw";
    private static final String TWITTER_SECRET = "MLKRJdHC5HzCUUZDb1C0OzvNDEiKXFhGhmYMdRcEyW5k6rWSgG";



// Application

	private AppComponent appComponent;
	public AppComponent getAppComponent(){ return appComponent; }


// Activity

	private ActivityComponent activityComponent;
	public ActivityComponent getActivityComponent(){ return activityComponent; }

	public ActivityComponent createActivityComponent(BaseActivity baseActivity){
		activityComponent = appComponent.plus(new ActivityModule(baseActivity));
		return activityComponent;
	}

	public void releaseActivityComponent(){ activityComponent = null; }


// App

	public static YAATCApp get(Context context){return (YAATCApp) context.getApplicationContext(); }

	@Override public void onCreate(){
		super.onCreate();
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new Twitter(authConfig));
		if(appComponent == null){
			appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
		}
	}
}
