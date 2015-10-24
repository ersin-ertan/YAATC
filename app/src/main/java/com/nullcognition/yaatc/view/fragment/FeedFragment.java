package com.nullcognition.yaatc.view.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.api.TweetHandler;
import com.nullcognition.yaatc.di.fragment.BaseFragment;
import com.nullcognition.yaatc.view.fragment.presenter.FeedPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class FeedFragment extends BaseFragment<FeedPresenter>{

	public static final String TAG = FeedFragment.class.getSimpleName();

	@Inject                         MaterialDialog.Builder materialDialog;
	@Bind(R.id.recyclerView) public RecyclerView           recyclerView;
	@Bind(R.id.toolbar)             Toolbar                toolbar;

	@OnClick(R.id.fab) void fab(){
		materialDialog.show();
	}

	@Override public void onCreate(final Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override protected void createPresenter(){ basePresenter = new FeedPresenter(this);}

	@Override public void onViewCreated(final View view, final Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);

		basePresenter.initToolbar(toolbar);
		basePresenter.initRecyclerView(recyclerView);
	}

	public void smoothScollToTop(){ basePresenter.smoothScrollToTop(recyclerView); }

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){ menuInflater.inflate(R.menu.menu_feed, menu); }

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.action_logout:{
				navigator.switchFragment(this, LoginFragment.class);
				return true;
			}
			case R.id.action_home:{
				smoothScollToTop();
				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void onEvent(TweetHandler.Tweet tweet){ basePresenter.addTweetToFeed(tweet); }

	@Override protected void injectSelf(){ fragmentComponent.inject(this); }

	@Override protected int getFragmentLayout(){return R.layout.fragment_feed; }
}
