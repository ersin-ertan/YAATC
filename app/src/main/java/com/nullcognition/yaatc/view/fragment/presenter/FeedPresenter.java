package com.nullcognition.yaatc.view.fragment.presenter;
// ersin 17/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.api.TweetHandler;
import com.nullcognition.yaatc.db.DbLoader;
import com.nullcognition.yaatc.model.Tweet;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.TextItem;
import com.nullcognition.yaatc.view.adapter.FeedAdapter;
import com.nullcognition.yaatc.view.fragment.FeedFragment;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

public class FeedPresenter extends BasePresenter{

	FeedAdapter  adapter;
	StorIOSQLite storIOSQLite;
	private RecyclerView recyclerView;

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

	public void initRecyclerView(RecyclerView rc){
		int columnSpanCount = 3;
		if(baseFrargment.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			columnSpanCount = 2;
		}
		StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(columnSpanCount, LinearLayoutManager.VERTICAL);
		sglm.setReverseLayout(true);
		rc.setLayoutManager(sglm);
		recyclerView = rc;
		getFeedItems();
	}

	private LoaderManager.LoaderCallbacks<List<FeedItem>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<FeedItem>>(){
		@Override public Loader<List<FeedItem>> onCreateLoader(final int id, final Bundle args){
			return new DbLoader(baseFrargment.getActivity().getApplicationContext(), storIOSQLite);
		}

		@Override public void onLoadFinished(final Loader<List<FeedItem>> loader, final List<FeedItem> data){

			adapter = new FeedAdapter(baseFrargment.getActivity(), data);
			recyclerView.setAdapter(adapter);
			recyclerView.setHasFixedSize(false);
			recyclerView.setItemAnimator(new DefaultItemAnimator());
		}

		@Override public void onLoaderReset(final Loader<List<FeedItem>> loader){}
	};

	public void smoothScrollToTop(RecyclerView recyclerView){
		if(recyclerView != null && adapter != null && adapter.getItemCount() > 0){
			recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
		}
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

	private void getFeedItems(){
		baseFrargment.getActivity().getSupportLoaderManager().initLoader(1, null, loaderCallbacks);
	}

	public void addTweetToFeed(final TweetHandler.TweetEvent tweetEvent){
		TextItem textItem = new TextItem(tweetEvent.tweet.content(), tweetEvent.tweet.isStarred());
		adapter.addItem(textItem);
		adapter.notifyDataSetChanged();
		storIOSQLite
				.put()
				.object(tweetEvent.tweet)
				.prepare()
				.executeAsBlocking();
	}

	public void deleteTweet(final TweetHandler.DeleteTweetEvent deleteTweetEvent){
		adapter.deleteItem(deleteTweetEvent.itemPositionInList);
		adapter.notifyDataSetChanged();
		// double db calls, use query to get the exact based on position in list
		Tweet t = getTweets().get(deleteTweetEvent.itemPositionInList);
//		String text = t.content();

		storIOSQLite
				.delete()
				.object(t)
				.prepare()
				.executeAsBlocking();
//		Toast.makeText(baseFrargment.getContext(), text, Toast.LENGTH_SHORT).show();
	}

	public void setStarred(final TweetHandler.StarredEvent starredEvent){
		Tweet t = getTweets().get(starredEvent.itemPositionInList);
		t.toggleStarred();
		adapter.setStared(starredEvent.itemPositionInList, starredEvent.isStarred);
		adapter.notifyItemChanged(starredEvent.itemPositionInList);

		PutResult pr = storIOSQLite
				.put()
				.object(t)
				.prepare()
				.executeAsBlocking();
//		Toast.makeText(baseFrargment.getContext(), "PUT RESULT: " + pr.wasUpdated(), Toast.LENGTH_SHORT).show();
		// working, bug was due to missing isStared assignment in teh TextItem class upon creation in getFeedItems()
	}
}
