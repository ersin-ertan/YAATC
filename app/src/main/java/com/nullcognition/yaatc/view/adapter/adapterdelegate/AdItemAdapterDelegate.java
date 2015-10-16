package com.nullcognition.yaatc.view.adapter.adapterdelegate;
// ersin 15/10/15 Copyright (c) 2015+ All rights reserved.


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.model.item.AdItem;
import com.nullcognition.yaatc.model.item.FeedItem;

import java.util.List;

public class AdItemAdapterDelegate extends AbsAdapterDelegate<List<FeedItem>>{

	private LayoutInflater inflater;
	private Activity       activity;

	public AdItemAdapterDelegate(Activity activity, int viewType){
		super(viewType);
		this.activity = activity;
		inflater = activity.getLayoutInflater();
	}

	@Override public boolean isForViewType(@NonNull List<FeedItem> items, int position){
		return items.get(position) instanceof AdItem;
	}

	@NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent){
		return new AdItemViewHolder(inflater.inflate(R.layout.item_ad, parent, false));
	}

	@Override public void onBindViewHolder(@NonNull List<FeedItem> items, int position,
	                                       @NonNull RecyclerView.ViewHolder holder){
		AdItemViewHolder vh     = (AdItemViewHolder) holder;
		AdItem           adItem = (AdItem) items.get(position);


//		vh.image(adItem.); // src set in xml
	}

	static class AdItemViewHolder extends RecyclerView.ViewHolder{

		public ImageView image;

		public AdItemViewHolder(View itemView){
			super(itemView);
			image = (ImageView) itemView.findViewById(R.id.ad_view);

		}
	}
}
