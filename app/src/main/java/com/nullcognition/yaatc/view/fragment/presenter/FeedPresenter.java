package com.nullcognition.yaatc.view.fragment.presenter;
// ersin 17/10/15 Copyright (c) 2015+ All rights reserved.


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.res.Configuration;
import android.os.AsyncTask;
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
import com.nullcognition.yaatc.widget.StackWidgetProvider;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;

import java.util.List;

public class FeedPresenter extends BasePresenter{

	private FeedAdapter           adapter;
	private RecyclerView          recyclerView;
	private StorIOContentResolver contentResolver;

	public FeedPresenter(final FeedFragment feedFragment, final StorIOContentResolver contentResolver){
		super(feedFragment);
		this.contentResolver = contentResolver;
	}

	public void initToolbar(final Toolbar toolbar){
		if(toolbar != null){
			toolbar.setTitle(baseFragment.getResources().getString(R.string.app_name_full));
			((AppCompatActivity) baseFragment.getActivity()).setSupportActionBar(toolbar);
		}

	}

	public void initRecyclerView(RecyclerView rc){
		int columnSpanCount = 3;
		if(baseFragment.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
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
			return new DbLoader(baseFragment.getActivity().getApplicationContext(), contentResolver);
		}

		@Override public void onLoadFinished(final Loader<List<FeedItem>> loader, final List<FeedItem> data){

			adapter = new FeedAdapter(baseFragment.getActivity(), data);
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
		// remember that the Query class exists both in the contentReslover and the Sqlite implementation of storio, pick the
		// right one for the specific use case
		return contentResolver.get()
		                      .listOfObjects(Tweet.class)
		                      .withQuery(TweetsTable.QUERY_ALL_CR)
		                      .prepare()
		                      .executeAsBlocking();
	}

	private void getFeedItems(){
		baseFragment.getActivity()
		            .getSupportLoaderManager()
		            .initLoader(1, null, loaderCallbacks);
	}

	public void addTweetToFeed(final TweetHandler.TweetEvent tweetEvent){
		final Tweet tweet    = tweetEvent.tweet;
		TextItem    textItem = new TextItem(tweet.content(), tweet.isStarred(), tweet.location());
		adapter.addItem(textItem);
		adapter.notifyDataSetChanged();

		new AsyncTask<Void, Void, Void>(){

			@Override protected Void doInBackground(final Void... params){
				contentResolver.put()
				               .object(tweet)
				               .prepare()
				               .executeAsBlocking();
				return null;
			}
		}.execute();
	}

	public void deleteTweet(final TweetHandler.DeleteTweetEvent deleteTweetEvent){
		adapter.deleteItem(deleteTweetEvent.itemPositionInList);
		adapter.notifyDataSetChanged();
		// double db calls, use query to get the exact based on position in list

		new AsyncTask<Void, Void, Void>(){

			@Override protected Void doInBackground(final Void... params){

				Tweet tweet = getTweets().get(deleteTweetEvent.itemPositionInList);
				contentResolver.delete()
				               .object(tweet)
				               .prepare()
				               .executeAsBlocking();
				return null;
			}

			@Override protected void onPostExecute(final Void aVoid){
				super.onPostExecute(aVoid);
				updateWidgets();
			}
		}.execute();
	}

	public void setStarred(final TweetHandler.StarredEvent starredEvent){

		new AsyncTask<Void, Void, Void>(){

			@Override protected Void doInBackground(final Void... params){
				List<Tweet> list  = getTweets();
				Tweet       tweet = list.get(starredEvent.itemPositionInList);
				tweet.toggleStarred();

				contentResolver.put()
				               .object(tweet)
				               .prepare()
				               .executeAsBlocking();
				return null;
			}

			@Override protected void onPostExecute(final Void aVoid){
				super.onPostExecute(aVoid);
				adapter.setStared(starredEvent.itemPositionInList, starredEvent.isStarred);
				adapter.notifyItemChanged(starredEvent.itemPositionInList);
				updateWidgets();
			}
		}.execute();
	}

	private void updateWidgets(){
		AppWidgetManager awm          = AppWidgetManager.getInstance(baseFragment.getContext());
		int[]            appWidgetIds = awm.getAppWidgetIds(new ComponentName(baseFragment.getContext(), StackWidgetProvider.class));
		awm.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
	}
}
