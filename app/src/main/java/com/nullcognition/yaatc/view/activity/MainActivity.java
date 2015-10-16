package com.nullcognition.yaatc.view.activity;

import android.os.Bundle;

import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.di.activity.BaseActivity;
import com.nullcognition.yaatc.view.fragment.LoginFragment;

public class MainActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);


		navigator.startFragment(this, R.id.activity_main_rootLayout, LoginFragment.class);
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
