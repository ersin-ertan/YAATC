package com.nullcognition.yaatc.api;
// ersin 15/10/15 Copyright (c) 2015+ All rights reserved.


import com.nullcognition.yaatc.model.Tweet;

import de.greenrobot.event.EventBus;

public class TweetHandler{

	public TweetHandler(){}

	public static void sendTweet(final Tweet tweet){
		EventBus.getDefault().post(new TweetEvent(tweet));
	}

	public static void deleteTweet(final int itemPositionInList){
		EventBus.getDefault().post(new DeleteTweetEvent(itemPositionInList));
	}

	public static void setStar(boolean isStarred, int pos){
		EventBus.getDefault().post(new StarredEvent(isStarred, pos));
	}

	public static class TweetEvent{

		public final Tweet tweet;

		public TweetEvent(Tweet t){ tweet = t;}
	}


	public static class DeleteTweetEvent{

		public final int itemPositionInList;

		public DeleteTweetEvent(int pos){itemPositionInList = pos;}
	}


	public static class StarredEvent{

		public final boolean isStarred;
		public final int     itemPositionInList;

		public StarredEvent(boolean starred, int pos){
			isStarred = starred;
			itemPositionInList = pos;
		}
	}
}
