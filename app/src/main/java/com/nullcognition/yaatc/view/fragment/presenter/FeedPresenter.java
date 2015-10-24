package com.nullcognition.yaatc.view.fragment.presenter;
// ersin 17/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.api.TweetHandler;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.ImageItem;
import com.nullcognition.yaatc.model.item.TextItem;
import com.nullcognition.yaatc.view.adapter.FeedAdapter;
import com.nullcognition.yaatc.view.fragment.FeedFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeedPresenter extends BasePresenter{

	FeedAdapter adapter;

	public FeedPresenter(final FeedFragment feedFragment){
		super(feedFragment);
	}

	public void initToolbar(final Toolbar toolbar){
		if(toolbar != null){
			toolbar.setTitle(baseFrargment.getResources().getString(R.string.app_name_full));
			((AppCompatActivity) baseFrargment.getActivity()).setSupportActionBar(toolbar);
		}

	}

	public void initRecyclerView(RecyclerView recyclerView){
		int columnSpanCount = 3;
		if(baseFrargment.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			columnSpanCount = 2;
		}

		StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(columnSpanCount, LinearLayoutManager.VERTICAL);
		sglm.setReverseLayout(true);
		recyclerView.setLayoutManager(sglm);
		adapter = new FeedAdapter(baseFrargment.getActivity(), getAnimals()); // TODO remove this and replace with a persistable object
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(false);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

	}

	public void smoothScrollToTop(RecyclerView recyclerView){
		if(recyclerView != null){ recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1); }
	}

	private List<FeedItem> getAnimals(){
		String         twitterUrl = "https://pbs.twimg.com/profile_images/615680132565504000/EIpgSD2K.png";
		List<FeedItem> animals    = new ArrayList<>();

		animals.add(new ImageItem("Collie", twitterUrl));
		animals.add(new TextItem("American Curl"));
		animals.add(new TextItem("Baliness"));
//		animals.add(new TextItem("Bengal"));
//		animals.add(new TextItem("Corat"));
//		animals.add(new TextItem("Manx"));
//		animals.add(new TextItem("Nebelung"));
//		animals.add(new TextItem("Aidi"));
//		animals.add(new TextItem("Chinook"));
//		animals.add(new TextItem("Appenzeller"));
//		animals.add(new ImageItem("Collie", twitterUrl));
//		animals.add(new TextItem("Manx"));
//		animals.add(new TextItem("Nebelung"));
//		animals.add(new TextItem("Aidi"));
//		animals.add(new TextItem("Chinook"));
//		animals.add(new TextItem("Appenzeller"));
//		animals.add(new TextItem("Collie"));

		Collections.shuffle(animals);
		return animals;
	}

	public void addTweetToFeed(final TweetHandler.Tweet tweet){
		adapter.addItem(tweet.text);
		adapter.notifyDataSetChanged();
	}

	public void deteletTweet(final TweetHandler.DeleteTweet deleteTweet){
		adapter.deleteItem(deleteTweet.itemPositionInList);
		adapter.notifyDataSetChanged();
	}
}
