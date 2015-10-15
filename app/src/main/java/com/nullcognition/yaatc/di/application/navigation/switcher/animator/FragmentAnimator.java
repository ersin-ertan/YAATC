package com.nullcognition.yaatc.di.application.navigation.switcher.animator;
// ersin 15/10/15 Copyright (c) 2015+ All rights reserved.


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.nullcognition.yaatc.di.fragment.BaseFragment;

public abstract class FragmentAnimator implements FragmentSwitchAnimator{

	public static final String NO_ANIMATION     = "NoAnimation";
	public static final String FADE_IN_OUT      = "FragmentFadeAnimator";
	public static final String SLIDE_LEFT_RIGHT = "FragmentSlideAnimator";

	public abstract int getEnter();
	public abstract int getExit();

	@Override public BaseFragment animateSwitch(@NonNull final FragmentTransaction fragmentTransaction, final int containerId,
	                                            @NonNull final Class<? extends BaseFragment> dstFragment, boolean isAnimated){
		BaseFragment newBaseFragment;
		try{
			newBaseFragment = dstFragment.newInstance();
			if(isAnimated){ fragmentTransaction.setCustomAnimations(getEnter(), getExit()); }
		}
		catch(InstantiationException | IllegalAccessException e){
			Log.e("FragmentFadeAnimator", "**animateSwitch() ERROR**");
			e.printStackTrace();
			return null;
		}
		fragmentTransaction.replace(containerId, newBaseFragment, dstFragment.getSimpleName()).commit();
		return newBaseFragment;

	}
}
