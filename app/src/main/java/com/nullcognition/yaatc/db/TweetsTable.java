package com.nullcognition.yaatc.db;
// ersin 24/10/15 Copyright (c) 2015+ All rights reserved.

import android.net.Uri;

import com.nullcognition.yaatc.db.provider.TweetContentProvider;
import com.pushtorefresh.storio.contentresolver.queries.Query;


public class TweetsTable{

	public static final String TABLE           = "tweets";
	public static final String COLUMN_ID       = "_id";
	public static final String COLUMN_CONTENT  = "content";
	public static final String COLUMN_STARRED  = "starred";
	public static final String COLUMN_LOCATION = "location";

	public static final Uri TWEET_URI =
			TweetContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(TABLE).build();

	public static final Query QUERY_ALL_CR = Query.builder().uri(TWEET_URI).build();

//
//	public static final Query QUERY_ALL_SQL = Query.builder()
//	                                              .table(TABLE)
//	                                              .build();

	private TweetsTable(){
		throw new IllegalStateException("Metadata class");
	}


	public static String getCreateTableQuery(){
		return "CREATE TABLE " + TABLE + "("
				+ COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
				+ COLUMN_CONTENT + " TEXT NOT NULL,"
				+ COLUMN_STARRED + " BOOLEAN NOT NULL,"
				+ COLUMN_LOCATION + " TEXT NOT NULL"
				+ ");";
	}
}
