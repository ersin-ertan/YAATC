package com.nullcognition.yaatc.view.fragment.presenter;
// ersin 24/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.di.fragment.BaseFragment;

public abstract class BasePresenter{

	protected BaseFragment baseFragment;

	public BasePresenter(BaseFragment bf){
		baseFragment = bf;
	}
}
