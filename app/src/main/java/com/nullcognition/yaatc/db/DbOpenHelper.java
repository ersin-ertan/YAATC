package com.nullcognition.yaatc.db;
// ersin 24/10/15 Copyright (c) 2015+ All rights reserved.

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

public class DbOpenHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "tweet_db";

	public DbOpenHelper(@NonNull Context context){
		super(context, DATABASE_NAME, null, 1);
	}

	@Override public void onCreate(@NonNull SQLiteDatabase db){
		db.execSQL(TweetsTable.getCreateTableQuery());
	}

	@Override public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion){ }
}
