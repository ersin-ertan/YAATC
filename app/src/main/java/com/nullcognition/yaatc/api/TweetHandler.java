package com.nullcognition.yaatc.api;
// ersin 15/10/15 Copyright (c) 2015+ All rights reserved.


import de.greenrobot.event.EventBus;

public class TweetHandler{

	public TweetHandler(){}

	public static void setText(final String text){
		EventBus.getDefault().post(new Tweet(text));
	}

	public static void deleteTweet(final int itemPositionInList){
		EventBus.getDefault().post(new DeleteTweet(itemPositionInList));
	}

	public static class Tweet{

		public String text;

		Tweet(String s){ text = s;}
	}


	public static class DeleteTweet{

		public int itemPositionInList;

		DeleteTweet(int pil){itemPositionInList = pil;}
	}

}
