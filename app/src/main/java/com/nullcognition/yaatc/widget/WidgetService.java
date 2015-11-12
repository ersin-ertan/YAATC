//package com.nullcognition.yaatc.widget;
//// ersin 11/11/15 Copyright (c) 2015+ All rights reserved.
//
//import android.appwidget.AppWidgetManager;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.widget.RemoteViews;
//import android.widget.RemoteViewsService;
//
//import com.nullcognition.yaatc.R;
//import com.nullcognition.yaatc.db.TweetsTable;
//import com.nullcognition.yaatc.model.Tweet;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WidgetService extends RemoteViewsService{
//
//	@Override
//	public RemoteViewsFactory onGetViewFactory(Intent intent){
//		return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
//	}
//}
//
//
//class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
//
//	private List<Tweet> mTweets = new ArrayList<Tweet>();
//	private Context mContext;
//	public  int     mAppWidgetId;
//
//	public StackRemoteViewsFactory(Context context, Intent intent){
//		mContext = context;
//		mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//				AppWidgetManager.INVALID_APPWIDGET_ID);
//	}
//
//	public void onCreate(){
//		// In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
//		// for example downloading or creating content etc, should be deferred to onDataSetChanged()
//		// or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
//
//		Cursor cursor = mContext.getContentResolver().query(TweetsTable.TWEET_URI, new String[]{ TweetsTable.COLUMN_CONTENT }, TweetsTable.COLUMN_STARRED, null, null);
//		// + " = " + "TRUE" for the column starred is not needed as the value is stored as a 0 or 1 and where value is sufficient to poll for true
//		mTweets.clear();
//		if(cursor != null){
//			int index = cursor.getColumnIndex(TweetsTable.COLUMN_CONTENT);
//			while(cursor.moveToNext()){
//				mTweets.add(Tweet.newTweet(cursor.getString(index), true, null));
//			}
//			cursor.close();
//		}
//	}
//
//	public void onDestroy(){
//		// In onDestroy() you should tear down anything that was setup for your data source,
//		// eg. cursors, connections, etc.
//		mTweets.clear();
//	}
//
//	public int getCount(){
//		return mTweets.size();
//	}
//
//	public RemoteViews getViewAt(int position){
//
//		// position will always range from 0 to getCount() - 1.
//		// We construct a remote views item based on our widget item xml file, and set the
//		// text based on the position.
//
//		RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
//		rv.setTextViewText(R.id.widget_item, mTweets.get(position).content());
//
//		// Next, we set a fill-intent which will be used to fill-in the pending intent template
//		// which is set on the collection view in StackWidgetProvider.
//
//		Bundle extras = new Bundle();
//		extras.putInt(WidgetProvider.EXTRA_ITEM, position);
//		Intent fillInIntent = new Intent();
//		fillInIntent.putExtras(extras);
//		rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);
//
//		// Return the remote views object.
//		return rv;
//	}
//
//	public RemoteViews getLoadingView(){
//		// You can create a custom loading view (for instance when getViewAt() is slow.) If you
//		// return null here, you will get the default loading view.
//		return null;
//	}
//
//	public int getViewTypeCount(){
//		return 1;
//	}
//
//	public long getItemId(int position){
//		return position;
//	}
//
//	public boolean hasStableIds(){
//		return true;
//	}
//
//	public void onDataSetChanged(){
//		// This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
//		// on the collection view corresponding to this factory. You can do heaving lifting in
//		// here, synchronously. For example, if you need to process an image, fetch something
//		// from the network, etc., it is ok to do it here, synchronously. The widget will remain
//		// in its current state while work is being done here, so you don't need to worry about
//		// locking up the widget.
//
//		Cursor cursor = mContext.getContentResolver().query(TweetsTable.TWEET_URI, new String[]{ TweetsTable.COLUMN_CONTENT }, TweetsTable.COLUMN_STARRED, null, null);
//		// + " = " + "TRUE" for the column starred is not needed as the value is stored as a 0 or 1 and where value is sufficient to poll for true
//		mTweets.clear();
//		if(cursor != null){
//			int index = cursor.getColumnIndex(TweetsTable.COLUMN_CONTENT);
//			while(cursor.moveToNext()){
//				mTweets.add(Tweet.newTweet(cursor.getString(index), true, null));
//			}
//			cursor.close();
//		}
//	}
//}
