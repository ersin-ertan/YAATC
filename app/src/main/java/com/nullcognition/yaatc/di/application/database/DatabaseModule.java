package com.nullcognition.yaatc.di.application.database;
// ersin 27/10/15 Copyright (c) 2015+ All rights reserved.


import android.database.sqlite.SQLiteOpenHelper;

import com.nullcognition.yaatc.db.DbOpenHelper;
import com.nullcognition.yaatc.db.provider.TweetContentProvider;
import com.nullcognition.yaatc.di.application.YAATCApp;
import com.nullcognition.yaatc.model.Tweet;
import com.nullcognition.yaatc.model.TweetStorIOSQLiteDeleteResolver;
import com.nullcognition.yaatc.model.TweetStorIOSQLiteGetResolver;
import com.nullcognition.yaatc.model.TweetStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module public class DatabaseModule{

	public DatabaseModule(){}

	@Singleton
	@Provides public SQLiteOpenHelper provideSQLiteOpenHelper(YAATCApp app){
		return new DbOpenHelper(app.getApplicationContext());
	}

	@Singleton
	@Provides StorIOSQLite provideStorioSQLite(SQLiteOpenHelper sqLiteOpenHelper){
		return DefaultStorIOSQLite
				.builder()
				.sqliteOpenHelper(sqLiteOpenHelper)
				.addTypeMapping(
						Tweet.class,
						SQLiteTypeMapping.<Tweet>builder()
						                 .putResolver(new TweetStorIOSQLitePutResolver())
						                 .getResolver(new TweetStorIOSQLiteGetResolver())
						                 .deleteResolver(new TweetStorIOSQLiteDeleteResolver())
						                 .build())
				.build();
	}

	@Singleton
	@Provides TweetContentProvider provideTweetContentProvider(){
		return new TweetContentProvider();
	}

//	@Singleton
//	@Provides StorIOContentResolver provideStorioContentResolver(TweetContentProvider tweetContentProvider){
//		return DefaultStorIOContentResolver
//				.builder()
//				.contentResolver(tweetContentProvider)
//				.addTypeMapping(Tweet.class, ContentResolverTypeMapping.<Tweet>builder()
//				                                                       .putResolver(new TweetPutResolver())
//				                                                       .getResolver(new TweetGetResolver())
//				                                                       .deleteResolver(new TweetDeleteResolver())
//				                                                       .build())
//				.build();
//	}


}
