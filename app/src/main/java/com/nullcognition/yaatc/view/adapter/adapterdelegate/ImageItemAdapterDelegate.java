package com.nullcognition.yaatc.view.adapter.adapterdelegate;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.api.TweetHandler;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.ImageItem;

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
		return new ImageItemViewHolder(inflater.inflate(R.layout.item_image, parent, false), inflater.getContext());
	}

	@Override public void onBindViewHolder(@NonNull List<FeedItem> items, int position,
	                                       @NonNull RecyclerView.ViewHolder holder){

		ImageItemViewHolder vh        = (ImageItemViewHolder) holder;
		ImageItem           imageItem = (ImageItem) items.get(position);

		vh.text.setText(imageItem.text);
		vh.positionInList = position;
		Glide.with(activity).load(imageItem.imageUrl).fitCenter().into(vh.image);
	}

	static class ImageItemViewHolder extends RecyclerView.ViewHolder{

		public AutofitTextView text;
		public ImageView       image;
		public int positionInList;

		public ImageItemViewHolder(View itemView, final Context context){
			super(itemView);
			text = (AutofitTextView) itemView.findViewById(R.id.item_text);
			image = (ImageView) itemView.findViewById(R.id.item_image);

			itemView.setOnLongClickListener(new View.OnLongClickListener(){
				                                @Override public boolean onLongClick(final View v){
					                                new MaterialDialog.Builder(context)
							                                .title(R.string.delete)
							                                .content(text.getText())
							                                .inputRangeRes(1, 140, R.color.colorPrimary)
							                                .icon(context.getDrawable(android.R.drawable.ic_delete))
							                                .positiveText(R.string.yes)
							                                .onPositive(new MaterialDialog.SingleButtonCallback(){
								                                @Override public void onClick(@NonNull final MaterialDialog materialDialog, @NonNull final DialogAction dialogAction){
									                                TweetHandler.deleteTweet(positionInList);
								                                }
							                                }).show();


					                                return true;
				                                }
			                                }

			);
		}
	}
}
