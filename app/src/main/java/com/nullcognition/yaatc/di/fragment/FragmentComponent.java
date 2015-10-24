package com.nullcognition.yaatc.di.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.view.fragment.FeedFragment;
import com.nullcognition.yaatc.view.fragment.LoginFragment;
import com.nullcognition.yaatc.view.fragment.presenter.BasePresenter;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = { FragmentModule.class }) public interface FragmentComponent{

	void inject(BaseFragment<BasePresenter> baseFragment);
	void inject(FeedFragment feedFragment);
	void inject(LoginFragment loginFragment);
//	void inject(TestFragment testFragment);

}
