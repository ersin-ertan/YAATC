package com.nullcognition.yaatc.di.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.FeedFragment;
import com.nullcognition.yaatc.TestFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = { FragmentModule.class }) public interface FragmentComponent{

	void inject(FeedFragment feedFragment);
	void inject(TestFragment testFragment);

}
