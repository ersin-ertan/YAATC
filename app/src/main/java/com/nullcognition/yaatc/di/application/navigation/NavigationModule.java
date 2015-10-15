package com.nullcognition.yaatc.di.application.navigation;
// ersin 15/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.di.application.navigation.switcher.animator.FragmentAnimator;
import com.nullcognition.yaatc.di.application.navigation.switcher.animator.FragmentSwitchAnimator;
import com.nullcognition.yaatc.di.application.navigation.switcher.animator.type.FragmentFadeAnimator;
import com.nullcognition.yaatc.di.application.navigation.switcher.animator.type.FragmentSlideAnimator;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module public class NavigationModule{

	public NavigationModule(){}

	@Provides @Named(FragmentAnimator.FADE_IN_OUT) FragmentSwitchAnimator provideFragmentFadeAnimator(){
		return new FragmentFadeAnimator();
	}

	@Provides @Named(FragmentAnimator.SLIDE_LEFT_RIGHT) FragmentSwitchAnimator provideFragmentSlideAnimator(){
		return new FragmentSlideAnimator();
	}


}
