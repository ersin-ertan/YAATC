package com.nullcognition.yaatc.widget;
// ersin 12/11/15 Copyright (c) 2015+ All rights reserved.


import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.db.TweetsTable;

import java.util.ArrayList;
import java.util.List;

public class StackWidgetService extends RemoteViewsService{

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent){
		return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
	}
}


class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

	private List<WidgetItem> widgetItems = new ArrayList<WidgetItem>();
	private Context context;
	private int     appWidgetId;

	public StackRemoteViewsFactory(Context context, Intent intent){
		this.context = context;
		appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	public void onCreate(){ updateWidgets(); }

	private void updateWidgets(){

		Cursor cursor = createCursor();

		widgetItems.clear();
		if(cursor != null && cursor.getCount() > 0){
			while(cursor.moveToNext()){
				widgetItems.add(
						new WidgetItem(cursor.getString(
								cursor.getColumnIndexOrThrow(TweetsTable.COLUMN_CONTENT)
						))
				);
			}
		}
		if(cursor != null){ cursor.close(); }
	}

	private Cursor createCursor(){

		ContentResolver contentResolver = context.getContentResolver();

		return contentResolver.query(
				TweetsTable.TWEET_URI,
				new String[]{ TweetsTable.COLUMN_CONTENT },
				TweetsTable.COLUMN_STARRED + " = ?",
				new String[]{ "1" },
				null
		);
	}

	public RemoteViews getViewAt(int position){

		RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
		rv.setTextViewText(R.id.widget_item, widgetItems.get(position).text);

		Bundle extras = new Bundle();
		extras.putInt(StackWidgetProvider.EXTRA_ITEM, position);
		Intent fillInIntent = new Intent();
		fillInIntent.putExtras(extras);
		rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

		return rv;
	}

	public void onDestroy(){ widgetItems.clear(); }

	public int getCount(){ return widgetItems.size(); }

	public RemoteViews getLoadingView(){ return null; }

	public int getViewTypeCount(){
		return 1;
	}

	public long getItemId(int position){
		return position;
	}

	public boolean hasStableIds(){
		return true;
	}

	public void onDataSetChanged(){
		// Triggered AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(...)
		updateWidgets();
	}
}
