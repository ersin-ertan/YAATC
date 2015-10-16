package com.nullcognition.yaatc.view.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.di.fragment.BaseFragment;

import butterknife.OnClick;

public class LoginFragment extends BaseFragment{

	public static final String TAG = "LoginFragment";

	@OnClick(R.id.twitter_login_button) void twitter_login_button(){
		navigator.switchFragment(this, FeedFragment.class);
	}

	@Override protected void injectSelf(){
		fragmentComponent.inject(this);
	}

	@Override protected int getFragmentLayout(){ return R.layout.fragment_login; }

}
