package com.nullcognition.yaatc.api;
// ersin 15/10/15 Copyright (c) 2015+ All rights reserved.


import de.greenrobot.event.EventBus;

public class TweetHandler{

	public TweetHandler(){}

	public static void setText(final String text){
		EventBus.getDefault().post(new TweetEvent(text));
	}

	public static void deleteTweet(final int itemPositionInList){
		EventBus.getDefault().post(new DeleteTweetEvent(itemPositionInList));
	}

	public static class TweetEvent{

		public String text;

		TweetEvent(String s){ text = s;}
	}


	public static class DeleteTweetEvent{

		public int itemPositionInList;

		DeleteTweetEvent(int pil){itemPositionInList = pil;}
	}

}
