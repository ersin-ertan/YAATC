package com.nullcognition.yaatc.di.application.navigation.switcher.animator;
// ersin 15/10/15 Copyright (c) 2015+ All rights reserved.


import android.support.v4.app.FragmentTransaction;

import com.nullcognition.yaatc.di.fragment.BaseFragment;

public interface FragmentSwitchAnimator{

	BaseFragment animateSwitch(final FragmentTransaction fragmentTransaction, final int containerId, Class<? extends BaseFragment> dstFragment, boolean isAnimated);

}
