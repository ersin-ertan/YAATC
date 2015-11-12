package com.nullcognition.yaatc.model;
// ersin 24/10/15 Copyright (c) 2015+ All rights reserved.

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nullcognition.yaatc.db.TweetsTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = TweetsTable.TABLE)
public class Tweet{

	@Nullable
	@StorIOSQLiteColumn(name = TweetsTable.COLUMN_ID, key = true)
	Long id;

	@StorIOSQLiteColumn(name = TweetsTable.COLUMN_CONTENT)
	String content;

	@StorIOSQLiteColumn(name = TweetsTable.COLUMN_STARRED)
	Integer isStarred;

	@StorIOSQLiteColumn(name = TweetsTable.COLUMN_LOCATION)
	String location;

	Tweet(){}

	private Tweet(@Nullable Long id, @NonNull String content, Integer starred, String loc){
		this.id = id;
		this.content = content;
		this.isStarred = starred;
		this.location = loc;
	}

	@NonNull
	public static Tweet newTweet(@Nullable Long id, @NonNull String content, Integer starred, String location){
		return new Tweet(id, content, starred, location);
	}

	@NonNull
	public static Tweet newTweet(@NonNull String content, Integer starred, String location){
		return new Tweet(null, content, starred, location);
	}

	@Nullable
	public Long id(){ return id; }

	@NonNull
	public String content(){
		return content;
	}

	public Integer isStarred(){ return isStarred; }

	public String location(){ return location; }

	public void toggleStarred(){
		if(isStarred == 1){ isStarred = 0; }
		else{ isStarred = 1; }
	}

	@Override
	public boolean equals(Object o){
		if(this == o){ return true; }
		if(o == null || getClass() != o.getClass()){ return false; }

		Tweet tweet = (Tweet) o;

		if(id != null ? !id.equals(tweet.id) : tweet.id != null){ return false; }
		if(!isStarred.equals(tweet.isStarred)){ return false; }
		if(location != null ? !location.equals(tweet.location) : tweet.location != null){
			return false;
		}
		return content.equals(tweet.content);
	}

	@Override
	public int hashCode(){
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + content.hashCode();
//		result += isStarred ? 1 : 0; // could this hash modification be why the put operation are not referencing the same object?
		return result;
	}

	@Override
	public String toString(){
		return "Tweet{" +
				"id=" + id +
				", content='" + content + '\'' +
				", starred=" + isStarred.toString() +
				", location=" + location +
				'}';
	}
}
