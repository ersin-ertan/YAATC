package com.nullcognition.yaatc.view.fragment.presenter;
// ersin 17/10/15 Copyright (c) 2015+ All rights reserved.


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
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
import com.nullcognition.yaatc.db.TweetsTable;
import com.nullcognition.yaatc.model.Tweet;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.TextItem;
import com.nullcognition.yaatc.view.adapter.FeedAdapter;
import com.nullcognition.yaatc.view.fragment.FeedFragment;
import com.nullcognition.yaatc.widget.WidgetProvider;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import java.util.List;

public class FeedPresenter extends BasePresenter{

	FeedAdapter  adapter;
	StorIOSQLite storIOSQLite;
	private RecyclerView          recyclerView;
	private StorIOContentResolver contentResolver;

	public FeedPresenter(final FeedFragment feedFragment, final StorIOSQLite storIOSQLite){
		super(feedFragment);
		this.storIOSQLite = storIOSQLite;
	}

	public FeedPresenter(final FeedFragment feedFragment, final StorIOContentResolver contentResolver){
		super(feedFragment);
		this.contentResolver = contentResolver;
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
//			return new DbLoader(baseFrargment.getActivity().getApplicationContext(), storIOSQLite);
			return new DbLoader(baseFrargment.getActivity().getApplicationContext(), contentResolver);
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

	// note the get requires the uri

	public List<Tweet> getTweets(){
//		return storIOSQLite
//				.get()
//				.listOfObjects(Tweet.class)
//				.withQuery(Query.builder()
//				                .table("tweets")
//				                .build())
//				.prepare()
//				.executeAsBlocking();

		// remember that the Query class exists both in the contentReslover and the Sqlite implementation of storio, pick the
		// right one for the specific use case
		return contentResolver.get()
		                      .listOfObjects(Tweet.class)
		                      .withQuery(com.pushtorefresh.storio.contentresolver.queries.Query.builder()
				                      .uri(TweetsTable.TWEET_URI)
// if the where and args is excluded will the call get all the entries?
//		                                                                                       .where("content = ?")
//		                                                                                       .whereArgs("test")
				                      .build())
		                      .prepare()
		                      .executeAsBlocking();
	}

	private void getFeedItems(){
		baseFrargment.getActivity().getSupportLoaderManager().initLoader(1, null, loaderCallbacks);
	}

	public void addTweetToFeed(final TweetHandler.TweetEvent tweetEvent){
		Tweet    tweet    = tweetEvent.tweet;
		TextItem textItem = new TextItem(tweet.content(), tweet.isStarred(), tweet.location());
		adapter.addItem(textItem);
		adapter.notifyDataSetChanged();
//		storIOSQLite
//				.put()
//				.object(tweetEvent.tweet)
//				.prepare()
//				.executeAsBlocking();
		contentResolver.put()
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

//		storIOSQLite
//				.delete()
//				.object(t)
//				.prepare()
//				.executeAsBlocking();
		contentResolver.delete()
				.object(t) // could have used a query based on the objects values
				.prepare()
				.executeAsBlocking();

		if(t.isStarred()){
			Intent intent = new Intent(baseFrargment.getContext(), WidgetProvider.class);
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
			// since it seems the onUpdate() is only fired on that:
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(baseFrargment.getContext());
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(baseFrargment.getContext(), WidgetProvider.class));
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			baseFrargment.getActivity().sendBroadcast(intent);

			AppWidgetManager.getInstance(baseFrargment.getContext()).notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
		}
	}

	public void setStarred(final TweetHandler.StarredEvent starredEvent){
		Tweet t = getTweets().get(starredEvent.itemPositionInList);
		t.toggleStarred();
		adapter.setStared(starredEvent.itemPositionInList, starredEvent.isStarred);
		adapter.notifyItemChanged(starredEvent.itemPositionInList);

//		PutResult pr = storIOSQLite
//				.put()
//				.object(t)
//				.prepare()
//				.executeAsBlocking();
		contentResolver.put()
		               .object(t)
		               .prepare()
		               .executeAsBlocking();
		if(t.isStarred()){
			Intent intent = new Intent(baseFrargment.getContext(), WidgetProvider.class);
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
			// since it seems the onUpdate() is only fired on that:
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(baseFrargment.getContext());
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(baseFrargment.getContext(), WidgetProvider.class));
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			baseFrargment.getActivity().sendBroadcast(intent);

			AppWidgetManager.getInstance(baseFrargment.getContext()).notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
		}
		// working, bug was due to missing isStared assignment in teh TextItem class upon creation in getFeedItems()
	}
}
