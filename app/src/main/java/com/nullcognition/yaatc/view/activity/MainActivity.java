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

	@Override protected int getActivityLayout(){ return R.layout.activity_main; }

	@Override protected void injectSelf(){ activityComponent.inject(this); }

}
