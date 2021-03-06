package com.nullcognition.yaatc.di.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nullcognition.yaatc.di.activity.BaseActivity;
import com.nullcognition.yaatc.di.application.navigation.Navigator;
import com.nullcognition.yaatc.view.fragment.presenter.BasePresenter;
import com.sora.util.akatsuki.Akatsuki;
import com.sora.util.akatsuki.Retained;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment{

	@Retained      String    temp;
	@Inject public Navigator navigator;
	protected      P         basePresenter;


	protected FragmentComponent fragmentComponent;

	public FragmentComponent getFragmentComponent(){ return fragmentComponent; }

	@Override public void onAttach(final Context context){
		super.onAttach(context);

		createFragmentComponent();
		injectSelf();
	}

	@Override public void onCreate(final Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		createPresenter();
	}

	protected abstract void createPresenter();

	protected void createFragmentComponent(){
		if(fragmentComponent == null){
			fragmentComponent = ((BaseActivity) getActivity()).createFragmentComponent(this);
		}
	}

	protected abstract void injectSelf();

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		int fragmentLayoutId = getFragmentLayout();
		if(fragmentLayoutId == 0){
			return super.onCreateView(inflater, container, savedInstanceState);
		}
		View rootView = inflater.inflate(fragmentLayoutId, container, false);
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	protected abstract int getFragmentLayout();

	@Override public void onViewCreated(View view, Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		Akatsuki.restore(this, savedInstanceState);
	}

	@Override public void onSaveInstanceState(final Bundle outState){
		super.onSaveInstanceState(outState);
		Akatsuki.save(this, outState); // does not work if there are no @Arg to save
	}

	@Override public void onDestroy(){
		ButterKnife.unbind(this);
		EventBus.getDefault().unregister(this);
		((BaseActivity) getActivity()).releaseFragmentComponent();
		// if the release is per the lifetime of the fragment
		super.onDestroy();
	}
}
