package com.nullcognition.yaatc.di.application.navigation.switcher.fragment;
// ersin 15/10/15 Copyright (c) 2015+ All rights reserved.


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import com.nullcognition.yaatc.di.activity.BaseActivity;
import com.nullcognition.yaatc.di.application.navigation.switcher.Switcher;
import com.nullcognition.yaatc.di.application.navigation.switcher.animator.FragmentAnimator;
import com.nullcognition.yaatc.di.application.navigation.switcher.animator.FragmentSwitchAnimator;
import com.nullcognition.yaatc.di.fragment.BaseFragment;

import javax.inject.Inject;
import javax.inject.Named;

//@Singleton
public class FragmentSwitcher implements Switcher{

	private final FragmentSwitchAnimator switchAnimator;

	private BaseFragment currentFragment;
	public BaseFragment getCurrentFragment(){ return currentFragment; }

	private String currentFragmentTag;
	public String getCurrentFragmentTag(){ return currentFragmentTag; }

	@Inject public FragmentSwitcher(@Named(FragmentAnimator.SLIDE_LEFT_RIGHT) FragmentSwitchAnimator switchAnimator){
		this.switchAnimator = switchAnimator;
	}

	public void switcher(@NonNull BaseFragment srcFragment, @NonNull final Class<? extends BaseFragment> dstFragment){
		if(!dstFragment.getSimpleName().equals(srcFragment.getClass().getSimpleName())){

			FragmentManager fm = srcFragment.getActivity().getSupportFragmentManager();
			fm.executePendingTransactions();
			FragmentTransaction ft = fm.beginTransaction();

			int containerId = 0;
			try{
				containerId = ((ViewGroup) srcFragment.getView().getParent()).getId();
				// to be used on non-headless fragments contained in a container with valid id
			}
			catch(NullPointerException n){
				Log.e("FragmentSwitcher", "containerId NPE");
				n.printStackTrace();
			}
			if(containerId == 0){
				return;
			}
			currentFragment = switchAnimator.animateSwitch(ft, containerId, dstFragment, true);
			currentFragmentTag = dstFragment.getSimpleName();
		}

	}

	public void starter(final BaseActivity baseActivity, final int containerId, final Class<? extends BaseFragment> startFragment){
		currentFragment = switchAnimator.animateSwitch(baseActivity.getSupportFragmentManager().beginTransaction(),
				containerId, startFragment, false);
		currentFragmentTag = startFragment.getSimpleName();
	}
}
