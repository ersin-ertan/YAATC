package com.nullcognition.yaatc;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nullcognition.yaatc.di.fragment.BaseFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class FeedFragment extends BaseFragment{

	public static final String TAG = FeedFragment.class.getSimpleName();

	@Inject             MaterialDialog.Builder materialDialog;
	@Bind(R.id.toolbar) Toolbar                toolbar;
	@OnClick(R.id.fab) void fab(){
		materialDialog.show();
	}

	@Override public void onCreate(final Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);


//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//				        .setAction("Action", null).show();
	}

	@Override public void onViewCreated(final View view, final Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		if(toolbar != null){
			toolbar.setTitle(getResources().getString(R.string.app_name_full));
			((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
		menuInflater.inflate(R.menu.menu_scrolling, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();

		if(id == R.id.action_settings){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override protected void injectSelf(){ fragmentComponent.inject(this); }

	@Override protected int getFragmentLayout(){return R.layout.fragment_feed; }
}
