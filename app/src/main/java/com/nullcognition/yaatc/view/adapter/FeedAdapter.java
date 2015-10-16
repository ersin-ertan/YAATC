package com.nullcognition.yaatc.view.adapter;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates.AdapterDelegatesManager;
import com.nullcognition.yaatc.model.item.AdItem;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.TextItem;
import com.nullcognition.yaatc.view.adapter.adapterdelegate.AdItemAdapterDelegate;
import com.nullcognition.yaatc.view.adapter.adapterdelegate.ImageItemAdapterDelegate;
import com.nullcognition.yaatc.view.adapter.adapterdelegate.TextItemAdapterDelegate;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter{

	private AdapterDelegatesManager<List<FeedItem>> delegatesManager;
	private List<FeedItem>                          items;
	private long tweetCount = 0L;

	public FeedAdapter(Activity activity, List<FeedItem> items){
		this.items = items;

		delegatesManager = new AdapterDelegatesManager<>();
		delegatesManager.addDelegate(new TextItemAdapterDelegate(activity, 0));
		delegatesManager.addDelegate(new ImageItemAdapterDelegate(activity, 1));

		delegatesManager.addDelegate(new AdItemAdapterDelegate(activity, 2));

	}

	public void addItem(String text){
		items.add(new TextItem(text));
		if(++tweetCount % 3 == 0){ items.add(new AdItem());}
	}

	@Override public int getItemViewType(int position){
		return delegatesManager.getItemViewType(items, position);
	}

	@Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		return delegatesManager.onCreateViewHolder(parent, viewType);
	}

	@Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
		delegatesManager.onBindViewHolder(items, position, holder);
	}

	@Override public int getItemCount(){
		return items.size();
	}
}
