package com.nullcognition.yaatc.view.fragment.presenter;
// ersin 17/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.api.TweetHandler;
import com.nullcognition.yaatc.model.Tweet;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.TextItem;
import com.nullcognition.yaatc.view.adapter.FeedAdapter;
import com.nullcognition.yaatc.view.fragment.FeedFragment;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.List;

public class FeedPresenter extends BasePresenter{

	FeedAdapter  adapter;
	StorIOSQLite storIOSQLite;

	public FeedPresenter(final FeedFragment feedFragment, final StorIOSQLite storIOSQLite){
		super(feedFragment);
		this.storIOSQLite = storIOSQLite;
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
		adapter = new FeedAdapter(baseFrargment.getActivity(), getFeedItems());
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(false);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

	}

	public void smoothScrollToTop(RecyclerView recyclerView){
		if(recyclerView != null){ recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1); }
	}


	public List<Tweet> getTweets(){
		return storIOSQLite
				.get()
				.listOfObjects(Tweet.class)
				.withQuery(Query.builder()
				                .table("tweets")
				                .build())
				.prepare()
				.executeAsBlocking();
	}

	private List<FeedItem> getFeedItems(){
		List<FeedItem> feedItems = new ArrayList<>();
		List<Tweet>    tweets    = getTweets();

		for(Tweet t : tweets){
			feedItems.add(new TextItem(t.content()));
		}


//		String         twitterUrl = "https://pbs.twimg.com/profile_images/615680132565504000/EIpgSD2K.png";
//		feedItems.add(new ImageItem("Collie", twitterUrl));
//		feedItems.add(new TextItem("American Curl"));
//		feedItems.add(new TextItem("Baliness"));


//		Collections.shuffle(feedItems);
		return feedItems;
	}

	public void addTweetToFeed(final TweetHandler.TweetEvent tweetEvent){
		adapter.addItem(tweetEvent.text);
		adapter.notifyDataSetChanged();
		storIOSQLite
				.put()
				.object(Tweet.newTweet(tweetEvent.text))
				.prepare()
				.executeAsBlocking();
	}

	public void deleteTweet(final TweetHandler.DeleteTweetEvent deleteTweetEvent){
		adapter.deleteItem(deleteTweetEvent.itemPositionInList);
		adapter.notifyDataSetChanged();
		Tweet  t    = getTweets().get(deleteTweetEvent.itemPositionInList);
		String text = t.content();

		storIOSQLite
				.delete()
				.object(t)
				.prepare()
				.executeAsBlocking();
		Toast.makeText(baseFrargment.getContext(), text, Toast.LENGTH_SHORT).show();
	}
}
