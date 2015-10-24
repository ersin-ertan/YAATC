package com.nullcognition.yaatc.view.activity;

import android.content.Intent;
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

	@Override protected int getActivityLayout(){ return R.layout.activity_main; }

	@Override protected void injectSelf(){ activityComponent.inject(this); }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Pass the activity result to the fragment, which will then pass the result to the login
		// button.
		navigator.getCurrentFragment().onActivityResult(requestCode, resultCode, data);
	}
}
