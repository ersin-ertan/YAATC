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
	boolean isStarred;

	Tweet(){}

	private Tweet(@Nullable Long id, @NonNull String content, boolean starred){
		this.id = id;
		this.content = content;
		isStarred = starred;
	}

	@NonNull
	public static Tweet newTweet(@Nullable Long id, @NonNull String content, boolean starred){
		return new Tweet(id, content, starred);
	}

	@NonNull
	public static Tweet newTweet(@NonNull String content, boolean starred){
		return new Tweet(null, content, starred);
	}

	@Nullable
	public Long id(){ return id; }


	@NonNull
	public String content(){
		return content;
	}

	public boolean isStarred(){ return isStarred; }
	public void toggleStarred(){ isStarred = !isStarred; }

	@Override
	public boolean equals(Object o){
		if(this == o){ return true; }
		if(o == null || getClass() != o.getClass()){ return false; }

		Tweet tweet = (Tweet) o;

		if(id != null ? !id.equals(tweet.id) : tweet.id != null){ return false; }
		if(isStarred != tweet.isStarred){ return false; }
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
				", starred=" + isStarred +
				'}';
	}
}
