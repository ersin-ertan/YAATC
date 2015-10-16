package com.nullcognition.yaatc.di.activity;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.di.fragment.FragmentComponent;
import com.nullcognition.yaatc.di.fragment.FragmentModule;
import com.nullcognition.yaatc.view.activity.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = { ActivityModule.class}) public interface ActivityComponent{

	void inject(MainActivity mainActivity); // activity type specific inheritance does not work

	FragmentComponent plus(FragmentModule fragmentModule);

}
