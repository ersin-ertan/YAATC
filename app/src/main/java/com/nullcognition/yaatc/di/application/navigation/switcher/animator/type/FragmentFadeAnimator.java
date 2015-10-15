package com.nullcognition.yaatc.di.application.navigation.switcher.animator.type;
// ersin 15/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.di.application.navigation.switcher.animator.FragmentAnimator;

public class FragmentFadeAnimator extends FragmentAnimator{

	@Override public int getEnter(){ return android.R.anim.fade_in; }
	@Override public int getExit(){ return android.R.anim.fade_out; }
}
