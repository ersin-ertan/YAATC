package com.nullcognition.yaatc.di.application.navigation;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.support.annotation.NonNull;

import com.nullcognition.yaatc.di.activity.BaseActivity;
import com.nullcognition.yaatc.di.application.navigation.switcher.fragment.FragmentSwitcher;
import com.nullcognition.yaatc.di.fragment.BaseFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator{

	private final FragmentSwitcher fragmentSwitcher;

	@Inject public Navigator(final FragmentSwitcher fragmentSwitcher){
		this.fragmentSwitcher = fragmentSwitcher;
	}

	public void switchFragment(final BaseFragment srcFragment, @NonNull final Class<? extends BaseFragment> dstFragment){
		fragmentSwitcher.switcher(srcFragment, dstFragment);
	}

	public void startFragment(final BaseActivity baseActivity, final int containerId, final Class<? extends BaseFragment> startFragment){
		fragmentSwitcher.starter(baseActivity, containerId, startFragment);
	}

	public BaseFragment getCurrentFragment(){ return fragmentSwitcher.getCurrentFragment(); }

	public String getCurrentFragmentTag(){ return fragmentSwitcher.getCurrentFragmentTag(); }

}
