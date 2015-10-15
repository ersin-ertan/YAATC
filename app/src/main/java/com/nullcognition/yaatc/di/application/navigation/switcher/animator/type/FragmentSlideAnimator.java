package com.nullcognition.yaatc.di.application.navigation.switcher.animator.type;
// ersin 15/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.di.application.navigation.switcher.animator.FragmentAnimator;

public class FragmentSlideAnimator extends FragmentAnimator{

	@Override public int getEnter(){ return android.R.anim.slide_in_left; }
	@Override public int getExit(){ return android.R.anim.slide_out_right; }
}
