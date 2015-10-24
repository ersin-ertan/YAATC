package com.nullcognition.yaatc.db;
// ersin 24/10/15 Copyright (c) 2015+ All rights reserved.

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.queries.Query;

public class TweetsTable{

	@NonNull
	public static final String TABLE = "tweets";

	@NonNull
	public static final String COLUMN_ID = "_id";

	@NonNull
	public static final String COLUMN_CONTENT = "content";

	@NonNull
	public static final Query QUERY_ALL = Query.builder()
	                                           .table(TABLE)
	                                           .build();

	private TweetsTable(){
		throw new IllegalStateException("Metadata class");
	}

	@NonNull
	public static String getCreateTableQuery(){
		return "CREATE TABLE " + TABLE + "("
				+ COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
				+ COLUMN_CONTENT + " TEXT NOT NULL"
				+ ");";
	}
}
