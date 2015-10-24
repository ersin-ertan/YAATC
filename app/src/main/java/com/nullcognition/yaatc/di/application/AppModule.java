package com.nullcognition.yaatc.di.application;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.database.sqlite.SQLiteOpenHelper;

import com.nullcognition.yaatc.db.DbOpenHelper;
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

@Module public class AppModule{

	private YAATCApp application;

	public AppModule(YAATCApp a){application = a;}

	@Provides public YAATCApp provideYAATCApp(){return application;}

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


}
