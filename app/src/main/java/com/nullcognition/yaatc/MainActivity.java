package com.nullcognition.yaatc;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.nullcognition.yaatc.di.activity.BaseActivity;

public class MainActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		FragmentManager     fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		if(fm.findFragmentByTag(FeedFragment.TAG) == null){
			ft.add(R.id.linearLayout, new TestFragment(), TestFragment.TAG)
			  .commit();
		}


	}
	@Override protected int getActivityLayout(){ return R.layout.activity_main; }

	@Override protected void injectSelf(){ activityComponent.inject(this); }


}
