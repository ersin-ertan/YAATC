package com.nullcognition.yaatc.db;
// ersin 24/10/15 Copyright (c) 2015+ All rights reserved.

import android.net.Uri;
import android.support.annotation.NonNull;

import com.nullcognition.yaatc.db.provider.TweetContentProvider;
import com.pushtorefresh.storio.sqlite.queries.Query;


public class TweetsTable{

	@NonNull
	public static final String TABLE = "tweets";

	@NonNull
	public static final String COLUMN_ID = "_id";

	@NonNull
	public static final String COLUMN_CONTENT = "content";

	@NonNull
	public static final String COLUMN_STARRED = "starred";

	@NonNull
	public static final String COLUMN_LOCATION = "location";

	@NonNull
	public static final Query QUERY_ALL_SQ = Query.builder()
	                                              .table(TABLE)
	                                              .build();
	public static final Uri   TWEET_URI    =
			TweetContentProvider.BASE_CONTENT_URI.buildUpon()
			                                     .appendPath(TABLE)
			                                     .build();

	@NonNull
	public static final com.pushtorefresh.storio.contentresolver.queries.Query
			QUERY_ALL_CR = com.pushtorefresh.storio.contentresolver.queries.Query.builder().uri(TWEET_URI).build();


	private TweetsTable(){
		throw new IllegalStateException("Metadata class");
	}

	@NonNull
	public static String getCreateTableQuery(){
		return "CREATE TABLE " + TABLE + "("
				+ COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
				+ COLUMN_CONTENT + " TEXT NOT NULL,"
				+ COLUMN_STARRED + " BOOLEAN NOT NULL,"
				+ COLUMN_LOCATION + " TEXT NOT NULL"
				+ ");";
	}
}
