package com.nullcognition.yaatc.db;
// ersin 27/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.model.Tweet;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.TextItem;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.List;

public class DbMethodHelper{


	public static void put(StorIOSQLite db, Tweet tweet){
		db.put()
		  .object(tweet)
		  .prepare()
		  .executeAsBlocking();
	}

	public static void delete(StorIOSQLite db, int itemPositionInList){
//		Tweet t = getTweets().get(deleteTweetEvent.itemPositionInList);

		DeleteQuery dq = DeleteQuery.builder()
		                            .table(DbOpenHelper.DATABASE_NAME)
		                            .where(TweetsTable.COLUMN_ID + "=" + itemPositionInList)
		                            .build();
		db.delete()
				.byQuery(dq)
//				.object(t)
				.prepare()
				.executeAsBlocking();
	}

	public static List<Tweet> getTweets(StorIOSQLite db){
		return db.get()
		         .listOfObjects(Tweet.class)
		         .withQuery(Query.builder()
		                         .table(TweetsTable.TABLE)
		                         .build())
		         .prepare()
		         .executeAsBlocking();
	}

	public static List<FeedItem> transform(StorIOSQLite db){
		List<FeedItem> feedItems = new ArrayList<>();
		List<Tweet>    tweets    = getTweets(db);

		for(Tweet t : tweets){
			feedItems.add(new TextItem(t.content(), t.isStarred(), t.location()));
		}
		return feedItems;
	}

}
