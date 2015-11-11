package com.nullcognition.yaatc.db.provider;
// ersin 11/11/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.db.TweetsTable;
import com.nullcognition.yaatc.model.Tweet;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.TextItem;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.queries.DeleteQuery;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderMethodHelper{

	public static void put(StorIOContentResolver db, Tweet tweet){
		db.put()
		  .object(tweet)
		  .prepare()
		  .executeAsBlocking();
	}

	public static void delete(StorIOContentResolver db, int itemPositionInList){
//		Tweet t = getTweets().get(deleteTweetEvent.itemPositionInList);

		DeleteQuery dq = DeleteQuery.builder()
		                            .uri(TweetsTable.TWEET_URI)
		                            .where(TweetsTable.COLUMN_ID + "=" + itemPositionInList)
		                            .build();
		db.delete()
				.byQuery(dq)
//				.object(t)
				.prepare()
				.executeAsBlocking();
	}

	public static List<Tweet> getTweets(StorIOContentResolver db){
		return db.get()
		         .listOfObjects(Tweet.class)
		         .withQuery(com.pushtorefresh.storio.contentresolver.queries.Query.builder()
				         .uri(TweetsTable.TWEET_URI)
//		                                                                                       .where("content = ?")
//		                                                                                       .whereArgs("test")
				         .build())
		         .prepare()
		         .executeAsBlocking();
	}

	public static List<FeedItem> transform(StorIOContentResolver db){
		List<FeedItem> feedItems = new ArrayList<>();
		List<Tweet>    tweets    = getTweets(db);

		for(Tweet t : tweets){
			feedItems.add(new TextItem(t.content(), t.isStarred(), t.location()));
		}
		return feedItems;
	}

}
