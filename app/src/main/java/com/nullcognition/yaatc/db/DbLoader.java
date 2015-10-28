package com.nullcognition.yaatc.db;
// ersin 27/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.nullcognition.yaatc.model.Tweet;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.TextItem;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import java.util.ArrayList;
import java.util.List;

public class DbLoader extends AsyncTaskLoader<List<FeedItem>>{


	StorIOSQLite db;

	public DbLoader(final Context context, StorIOSQLite db){
		super(context);
		this.db = db;
	}

	@Override protected void onStartLoading(){
		forceLoad();

	}

	@Override public List<FeedItem> loadInBackground(){

		List<FeedItem> feedItems = new ArrayList<>();
		List<Tweet>    tweets    = DbMethodHelper.getTweets(db);

		for(Tweet t : tweets){
			feedItems.add(new TextItem(t.content(), t.isStarred(), t.location()));
		}
		return feedItems;
	}

	@Override public void deliverResult(final List<FeedItem> data){
		super.deliverResult(data);
	}
}
