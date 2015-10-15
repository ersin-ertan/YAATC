package com.nullcognition.yaatc;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nullcognition.yaatc.di.fragment.BaseFragment;
import com.nullcognition.yaatc.model.FeedItem;
import com.nullcognition.yaatc.model.ImageItem;
import com.nullcognition.yaatc.model.TextItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class FeedFragment extends BaseFragment{

	public static final String TAG = FeedFragment.class.getSimpleName();

	FeedAdapter adapter;
	@Inject                  MaterialDialog.Builder materialDialog;
	@Bind(R.id.toolbar)      Toolbar                toolbar;
	@Bind(R.id.recyclerView) RecyclerView           recyclerView;
	@OnClick(R.id.fab) void fab(){
		materialDialog.show();
	}

	@Override public void onCreate(final Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	private void initRecyclerView(final AppCompatActivity activity){
		recyclerView.setLayoutManager(new LinearLayoutManager(activity));
		adapter = new FeedAdapter(activity, getAnimals());
		recyclerView.setAdapter(adapter);
		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setHasFixedSize(false);
	}

	@Override public void onViewCreated(final View view, final Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		if(toolbar != null){
			toolbar.setTitle(getResources().getString(R.string.app_name_full));
			activity.setSupportActionBar(toolbar);
		}
		initRecyclerView(activity);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
		menuInflater.inflate(R.menu.menu_scrolling, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();

		if(id == R.id.action_logout){ return true; }
//		if(id == R.id.action_home){
//			recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
//			return true; }
		return super.onOptionsItemSelected(item);
	}

	private List<FeedItem> getAnimals(){
		List<FeedItem> animals = new ArrayList<>();

		animals.add(new ImageItem("Collie", "https://avatars2.githubusercontent.com/u/4809853?v=3&u=c242e3f70705bc5d280484ba2adb582c645900e7&s=140"));
		animals.add(new TextItem("American Curl"));
		animals.add(new TextItem("Baliness"));
		animals.add(new TextItem("Bengal"));
		animals.add(new TextItem("Corat"));
		animals.add(new TextItem("Manx"));
		animals.add(new TextItem("Nebelung"));
		animals.add(new TextItem("Aidi"));
		animals.add(new TextItem("Chinook"));
		animals.add(new TextItem("Appenzeller"));
		animals.add(new ImageItem("Collie", "https://avatars2.githubusercontent.com/u/4809853?v=3&u=c242e3f70705bc5d280484ba2adb582c645900e7&s=140"));

		animals.add(new TextItem("Manx"));
		animals.add(new TextItem("Nebelung"));
		animals.add(new TextItem("Aidi"));
		animals.add(new TextItem("Chinook"));
		animals.add(new TextItem("Appenzeller"));
		animals.add(new TextItem("Collie"));

//		Collections.shuffle(animals);
		return animals;
	}


	@Override protected void injectSelf(){ fragmentComponent.inject(this); }

	@Override protected int getFragmentLayout(){return R.layout.fragment_feed; }
}


//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//				        .setAction("Action", null).show();
