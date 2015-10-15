package com.nullcognition.yaatc.adapterdelegate;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.model.FeedItem;
import com.nullcognition.yaatc.model.ImageItem;

import java.util.List;

import me.grantland.widget.AutofitTextView;

public class ImageItemAdapterDelegate extends AbsAdapterDelegate<List<FeedItem>>{

	private LayoutInflater inflater;
	private Activity       activity;

	public ImageItemAdapterDelegate(Activity activity, int viewType){
		super(viewType);
		this.activity = activity;
		inflater = activity.getLayoutInflater();
	}

	@Override public boolean isForViewType(@NonNull List<FeedItem> items, int position){
		return items.get(position) instanceof ImageItem;
	}

	@NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent){
		return new ImageItemViewHolder(inflater.inflate(R.layout.item_image, parent, false));
	}

	@Override public void onBindViewHolder(@NonNull List<FeedItem> items, int position,
	                                       @NonNull RecyclerView.ViewHolder holder){

		ImageItemViewHolder vh        = (ImageItemViewHolder) holder;
		ImageItem           imageItem = (ImageItem) items.get(position);

		vh.text.setText(imageItem.text);
		Glide.with(activity).load(imageItem.imageUrl).fitCenter().into(vh.image);
	}

	static class ImageItemViewHolder extends RecyclerView.ViewHolder{

		public AutofitTextView text;
		public ImageView       image;

		public ImageItemViewHolder(View itemView){
			super(itemView);
			text = (AutofitTextView) itemView.findViewById(R.id.item_text);
			image = (ImageView) itemView.findViewById(R.id.item_image);
		}
	}
}
