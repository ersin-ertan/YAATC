package com.nullcognition.yaatc.view.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.di.fragment.BaseFragment;

import butterknife.Bind;

public class TestFragment<TestPresenter> extends BaseFragment{

	public static final String TAG = TestFragment.class.getSimpleName();

	@Override protected void createPresenter(){}

	@Override protected void injectSelf(){
		fragmentComponent.inject(this);
	}

	@Override protected int getFragmentLayout(){
		return R.layout.fragment_test;
	}

	@Bind(R.id.recyclerView) RecyclerView recyclerView;

	private void initRecyclerView(final AppCompatActivity activity){
		recyclerView.setLayoutManager(new LinearLayoutManager(activity));
	}

	@Override public void onViewCreated(final View view, final Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		initRecyclerView(activity);
	}
}
