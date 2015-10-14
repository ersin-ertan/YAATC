package com.nullcognition.yaatc.di.application;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.app.Application;
import android.content.Context;

import com.nullcognition.yaatc.di.activity.ActivityComponent;
import com.nullcognition.yaatc.di.activity.ActivityModule;
import com.nullcognition.yaatc.di.activity.BaseActivity;

public class YAATCApp extends Application{


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
		if(appComponent == null){
			appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
		}
	}
}
