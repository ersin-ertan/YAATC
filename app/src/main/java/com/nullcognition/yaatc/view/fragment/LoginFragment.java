package com.nullcognition.yaatc.view.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.di.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment{

	public static final String TAG = "LoginFragment";

	@Bind(R.id.twitter_login_button) Button loginButton;

	@OnClick(R.id.twitter_login_button) void twitter_login_button(final View view){
		navigator.switchFragment(this, FeedFragment.class);
	}


	@Override protected void injectSelf(){
		fragmentComponent.inject(this);
	}
	@Override protected int getFragmentLayout(){
		return R.layout.fragment_login;
	}

	@Override public void onViewCreated(final View view, final Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);

//		loginButton.setCallback(new Callback<TwitterSession>(){
//			@Override
//			public void success(Result<TwitterSession> result){
//				TwitterSession session = result.data;
//
//				String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
//				Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//				navigator.switchFragment(LoginFragment.class, FeedFragment.class);
//			}
//			@Override
//			public void failure(TwitterException exception){
//				Log.d("TwitterKit", "Login with Twitter failure", exception);
//			}
//		});

	}
}


// The TwitterSession is also available through:
// Twitter.getInstance().core.getSessionManager().getActiveSession()
