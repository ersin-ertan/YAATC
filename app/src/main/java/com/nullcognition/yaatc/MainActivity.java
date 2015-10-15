package com.nullcognition.yaatc;

import android.os.Bundle;

import com.nullcognition.yaatc.di.activity.BaseActivity;

public class MainActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);


		if(navigator.getCurrentFragment() == null){
			navigator.startFragment(this, R.id.activity_main_rootLayout, LoginFragment.class);
		}
		else{
			navigator.startFragment(this, R.id.activity_main_rootLayout, navigator.getCurrentFragment().getClass());
		}
	}

	@Override protected void onStart(){
		super.onStart();
//		navigator.switchFragment(navigator.getCurrentFragment(), FeedFragment.class);
	}
	@Override protected void onPostCreate(final Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);

//		navigator.switchFragment(replace, FeedFragment.class);
	}

	//	@Override public void onActivityResult(int requestCode, int resultCode, Intent data){
//		super.onActivityResult(requestCode, resultCode, data);
//		Fragment fragment = getSupportFragmentManager().findFragmentByTag(navigator.getCurrentFragmentTag());
//		if(fragment != null){
//			fragment.onActivityResult(requestCode, resultCode, data); }
//	}

	@Override protected int getActivityLayout(){ return R.layout.activity_main; }

	@Override protected void injectSelf(){ activityComponent.inject(this); }


}
