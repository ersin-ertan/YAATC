package com.nullcognition.yaatc.view.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.api.TweetHandler;
import com.nullcognition.yaatc.di.fragment.BaseFragment;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.ImageItem;
import com.nullcognition.yaatc.model.item.TextItem;
import com.nullcognition.yaatc.view.adapter.FeedAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class FeedFragment extends BaseFragment{

	public static final String TAG = FeedFragment.class.getSimpleName();

	FeedAdapter adapter;
	@Inject                         MaterialDialog.Builder materialDialog;
	@Bind(R.id.recyclerView) public RecyclerView           recyclerView;
	@Bind(R.id.toolbar)             Toolbar                toolbar;
	@OnClick(R.id.fab) void fab(){
		materialDialog.show();
	}

	@Override public void onCreate(final Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		EventBus.getDefault().register(this);

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

	private void initRecyclerView(final AppCompatActivity activity){
		int columnSpanCount = 1;
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			columnSpanCount = 2;
		}
		else{ columnSpanCount = 3; }

		StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(columnSpanCount, LinearLayoutManager.VERTICAL);
		sglm.setReverseLayout(true);
		recyclerView.setLayoutManager(sglm);
		adapter = new FeedAdapter(activity, getAnimals()); // TODO remove this and replace with a persistable object
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(false);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
	}

	public void smoothScollToTop(){
		if(recyclerView != null){ recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1); }
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
		menuInflater.inflate(R.menu.menu_feed, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();

		if(id == R.id.action_logout){
			navigator.switchFragment(this, LoginFragment.class);
			return true;
		}
		if(id == R.id.action_home){
			recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onEvent(TweetHandler.Tweet tweet){
		adapter.addItem(tweet.text);
		adapter.notifyDataSetChanged();
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
