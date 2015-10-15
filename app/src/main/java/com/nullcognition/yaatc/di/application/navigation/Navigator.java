package com.nullcognition.yaatc.di.application.navigation;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.support.annotation.NonNull;

import com.nullcognition.yaatc.di.application.navigation.switcher.fragment.FragmentSwitcher;
import com.nullcognition.yaatc.di.fragment.BaseFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator{

	//	private final ActivitySwitcher activitySwitcher;
	private final FragmentSwitcher fragmentSwitcher;

	@Inject public Navigator(//final ActivitySwitcher activitySwitcher,
	                         final FragmentSwitcher fragmentSwitcher){
//		this.activitySwitcher = activitySwitcher;
		this.fragmentSwitcher = fragmentSwitcher;
	}

	public void switchFragment(final BaseFragment srcFragment, @NonNull final Class<? extends BaseFragment> dstFragment){
		fragmentSwitcher.switcher(srcFragment, dstFragment);
	}

	public BaseFragment getCurrentFragment(){ return fragmentSwitcher.getCurrentFragment(); }

	public String getCurrentFragmentTag(){ return fragmentSwitcher.getCurrentFragmentTag(); }
}
