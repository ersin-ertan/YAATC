package com.nullcognition.yaatc;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.nullcognition.yaatc.di.activity.BaseActivity;
import com.nullcognition.yaatc.di.fragment.BaseFragment;

public class Navigator{


	private String currentFragmentTag = "";
	public String getCurrentFragmentTag(){return currentFragmentTag;}

	public Navigator(){}

	public void switchFragment(BaseActivity baseActivity, final Class<? extends BaseFragment> srcFragment, final Class<? extends BaseFragment> dstFragment){

		if(dstFragment != null){
			FragmentTransaction ft = baseActivity.getSupportFragmentManager().beginTransaction();
			animateTransition(ft, 1); // only animate on switches, starts take too long
			replaceFragment(ft, dstFragment);
		}

	}
	public void startFragment(BaseActivity baseActivity){

		FragmentManager fm = baseActivity.getSupportFragmentManager();
		if(fm.findFragmentByTag(LoginFragment.TAG) == null){
			FragmentTransaction ft = fm.beginTransaction();
			Class<? extends BaseFragment> dstFragment;
			switch(currentFragmentTag){
				case "":{
					dstFragment = LoginFragment.class;
					break;
				}
				case "LoginFragment":{
					dstFragment = LoginFragment.class;
					break;
				}
				case "FeedFragment":{
					dstFragment = FeedFragment.class;
					break;
				}
				default:
					throw new IllegalArgumentException("not a possible fragment created");
			}
			replaceFragment(ft, dstFragment);
		}
	}
	private void replaceFragment(final FragmentTransaction ft, final Class<? extends BaseFragment> dstFragment){
		try{
			ft.replace(R.id.linearLayout, dstFragment.newInstance(), dstFragment.getSimpleName());
			ft.commit();
			currentFragmentTag = dstFragment.getSimpleName();
		}
		catch(InstantiationException | IllegalAccessException e){ e.printStackTrace();}
	}

	private void animateTransition(final FragmentTransaction ft, final int i){
		switch(i){
			case 0:
				ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
				break;
			case 1:
				ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				break;
		}

	}
}
