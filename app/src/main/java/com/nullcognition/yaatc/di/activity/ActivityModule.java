package com.nullcognition.yaatc.di.activity;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.nullcognition.yaatc.view.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module public class ActivityModule{

	private final BaseActivity baseActivity;

	public ActivityModule(BaseActivity a){baseActivity = a;}

	//	@ActivityScope
	@Provides public BaseActivity provideBaseActivity(){return baseActivity;}

	@Provides public Context provideContext(){ return baseActivity; }

	@Provides public FragmentManager provideFragmentManager(BaseActivity baseActivity){
		return baseActivity.getSupportFragmentManager();
	}

	@ActivityScope
	@Provides GoogleApiClient provideGoogleApiClient(){
		return new GoogleApiClient.Builder(baseActivity)
				.addApi(LocationServices.API)
				.addConnectionCallbacks((MainActivity) baseActivity)
				.addOnConnectionFailedListener((MainActivity) baseActivity)
				.build();
	}
}
