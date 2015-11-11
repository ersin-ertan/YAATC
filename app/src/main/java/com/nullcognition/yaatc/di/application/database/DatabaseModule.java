package com.nullcognition.yaatc.di.application.database;
// ersin 27/10/15 Copyright (c) 2015+ All rights reserved.


import android.database.sqlite.SQLiteOpenHelper;

import com.nullcognition.yaatc.db.DbOpenHelper;
import com.nullcognition.yaatc.db.provider.TweetMeta;
import com.nullcognition.yaatc.di.application.YAATCApp;
import com.nullcognition.yaatc.model.Tweet;
import com.nullcognition.yaatc.model.TweetStorIOSQLiteDeleteResolver;
import com.nullcognition.yaatc.model.TweetStorIOSQLiteGetResolver;
import com.nullcognition.yaatc.model.TweetStorIOSQLitePutResolver;
import com.pushtorefresh.storio.contentresolver.ContentResolverTypeMapping;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
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
	@Provides StorIOContentResolver provideStorioContentResolver(YAATCApp app){
		return DefaultStorIOContentResolver
				.builder()
				.contentResolver(app.getContentResolver())
				.addTypeMapping(Tweet.class, ContentResolverTypeMapping.<Tweet>builder()
				                                                       .putResolver(TweetMeta.PUT_RESOLVER)
				                                                       .getResolver(TweetMeta.GET_RESOLVER)
				                                                       .deleteResolver(TweetMeta.DELETE_RESOLVER)
				                                                       .build())
				.build();
	}


}
