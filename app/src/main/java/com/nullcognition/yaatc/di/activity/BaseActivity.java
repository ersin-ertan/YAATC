package com.nullcognition.yaatc.di.activity;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nullcognition.yaatc.di.application.YAATCApp;
import com.nullcognition.yaatc.di.application.navigation.Navigator;
import com.nullcognition.yaatc.di.fragment.BaseFragment;
import com.nullcognition.yaatc.di.fragment.FragmentComponent;
import com.nullcognition.yaatc.di.fragment.FragmentModule;
import com.sora.util.akatsuki.Akatsuki;
import com.sora.util.akatsuki.Retained;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity{

	@Inject public Navigator navigator;
	@Retained      String    temp;
	protected int rootLayoutId;

	protected ActivityComponent activityComponent;
	public ActivityComponent getActivityComponent(){ return activityComponent; }

	private FragmentComponent fragmentComponent;
	public FragmentComponent getFragmentComponent(){ return fragmentComponent; }

	public FragmentComponent createFragmentComponent(BaseFragment baseFragment){
		if(activityComponent == null){ createActivityComponent(); }
		fragmentComponent = activityComponent.plus(new FragmentModule(baseFragment));
		return fragmentComponent;
	}

	public void releaseFragmentComponent(){ fragmentComponent = null; }

	@Override protected void onCreate(final Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		setContentView(getActivityLayout());

		createActivityComponent();
		injectSelf();
		injectViewsAndState(savedInstanceState);
	}

	protected abstract int getActivityLayout();

	private void createActivityComponent(){
		if(activityComponent == null){
			activityComponent = YAATCApp.get(this)
			                            .createActivityComponent(this);
		}
	}

	protected abstract void injectSelf();

	protected void injectViewsAndState(final Bundle savedInstanceState){
		ButterKnife.bind(this);
		Akatsuki.restore(this, savedInstanceState);
	}

	@Override protected void onSaveInstanceState(final Bundle outState){
		super.onSaveInstanceState(outState);
		Akatsuki.save(this, outState);
		ButterKnife.unbind(this);
	}

	@Override public void finish(){
		YAATCApp.get(this).releaseActivityComponent();
		super.finish();
	}
}
