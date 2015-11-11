package com.nullcognition.yaatc.db.provider;
// ersin 27/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.nullcognition.yaatc.db.DbOpenHelper;
import com.nullcognition.yaatc.db.TweetsTable;

public class TweetContentProvider extends ContentProvider{

	@NonNull
	public static final String AUTHORITY        = "com.nullcognition.yaatc";
	public static final Uri    BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

	private static final int URI_MATCHER_CODE_TWEETS = 1;

	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

	static{
		URI_MATCHER.addURI(AUTHORITY, TweetsTable.TWEET_URI.getPath(), URI_MATCHER_CODE_TWEETS);
	}

//	@Inject
//	SQLiteOpenHelper sqliteOpenHelper;
//
//	@Override
//	public boolean onCreate(){
//		YAATCApp.get(getContext()).getAppComponent().inject(this);
//		return true;
//	}

	private DbOpenHelper sqliteOpenHelper;

	@Override public boolean onCreate(){
		sqliteOpenHelper = new DbOpenHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
		switch(URI_MATCHER.match(uri)){
			case URI_MATCHER_CODE_TWEETS:
				return sqliteOpenHelper
						.getReadableDatabase()
						.query(
								TweetsTable.TABLE,
								projection,
								selection,
								selectionArgs,
								null,
								null,
								sortOrder
						);

			default:
				return null;
		}
	}

	@Override
	public String getType(Uri uri){
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values){
		final long insertedId;

		switch(URI_MATCHER.match(uri)){
			case URI_MATCHER_CODE_TWEETS:
				insertedId = sqliteOpenHelper
						.getWritableDatabase()
						.insert(
								TweetsTable.TABLE,
								null,
								values
						);
				break;

			default:
				return null;
		}

		if(insertedId != -1){
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return ContentUris.withAppendedId(uri, insertedId);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
		final int numberOfRowsAffected;

		switch(URI_MATCHER.match(uri)){
			case URI_MATCHER_CODE_TWEETS:
				numberOfRowsAffected = sqliteOpenHelper
						.getWritableDatabase()
						.update(
								TweetsTable.TABLE,
								values,
								selection,
								selectionArgs
						);
				break;

			default:
				return 0;
		}

		if(numberOfRowsAffected > 0){
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return numberOfRowsAffected;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs){
		final int numberOfRowsDeleted;

		switch(URI_MATCHER.match(uri)){
			case URI_MATCHER_CODE_TWEETS:
				numberOfRowsDeleted = sqliteOpenHelper
						.getWritableDatabase()
						.delete(
								TweetsTable.TABLE,
								selection,
								selectionArgs
						);
				break;

			default:
				return 0;
		}

		if(numberOfRowsDeleted > 0){
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return numberOfRowsDeleted;
	}
}
