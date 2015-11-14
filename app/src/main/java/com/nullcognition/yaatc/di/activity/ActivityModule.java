package com.nullcognition.yaatc.di.activity;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.nullcognition.yaatc.view.activity.MainActivity;
import com.nullcognition.yaatc.view.fragment.LoginFragment;

import javax.inject.Named;

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
	@Provides @Named(MainActivity.LOCATION_GAPI_CLIENT) GoogleApiClient provideGoogleApiClient(){
		return new GoogleApiClient.Builder(baseActivity)
				.addApi(LocationServices.API)
				.addConnectionCallbacks((MainActivity) baseActivity)
				.addOnConnectionFailedListener((MainActivity) baseActivity)
				.build();
	}

	@ActivityScope
	@Provides GoogleSignInOptions provideGoogleSignInOptions(){
		return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestId().build();
	}

	@ActivityScope
	@Provides @Named(LoginFragment.LOGIN_GAPI_CLIENT) GoogleApiClient provideGoogleApiClientLogin(
			final Context context, final BaseActivity ba, GoogleSignInOptions gso){

		return new GoogleApiClient.Builder(context)
				.enableAutoManage(ba, (MainActivity) ba)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();
	}

}
