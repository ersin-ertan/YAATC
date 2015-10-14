package com.nullcognition.yaatc.adapterdelegate;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.model.FeedItem;
import com.nullcognition.yaatc.model.TextItem;

import java.util.List;

import me.grantland.widget.AutofitTextView;

public class TextItemAdapterDelegate extends AbsAdapterDelegate<List<FeedItem>>{

	private LayoutInflater inflater;

	public TextItemAdapterDelegate(Activity activity, int viewType){
		super(viewType);
		inflater = activity.getLayoutInflater();
	}

	@Override public boolean isForViewType(@NonNull List<FeedItem> items, int position){
		return items.get(position) instanceof TextItem;
	}

	@NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent){
		return new TextItemViewHolder(inflater.inflate(R.layout.item_text, parent, false));
	}

	@Override public void onBindViewHolder(@NonNull List<FeedItem> items, int position,
	                                       @NonNull RecyclerView.ViewHolder holder){

		TextItemViewHolder vh       = (TextItemViewHolder) holder;
		TextItem           textItem = (TextItem) items.get(position);

		vh.name.setText(textItem.text);
	}

	static class TextItemViewHolder extends RecyclerView.ViewHolder{

		public AutofitTextView name;

		public TextItemViewHolder(View itemView){
			super(itemView);
			name = (AutofitTextView) itemView.findViewById(R.id.item_text);
		}
	}
}
