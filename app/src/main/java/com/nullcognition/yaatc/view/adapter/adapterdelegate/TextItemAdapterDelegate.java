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
import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.api.TweetHandler;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.TextItem;

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
		return new TextItemViewHolder(inflater.inflate(R.layout.item_text, parent, false), inflater.getContext());
	}

	@Override public void onBindViewHolder(@NonNull List<FeedItem> items, int position,
	                                       @NonNull RecyclerView.ViewHolder holder){

		TextItemViewHolder vh       = (TextItemViewHolder) holder;
		TextItem           textItem = (TextItem) items.get(position);

		vh.text.setText(textItem.text);
		vh.isStarred = textItem.isStarred;
		vh.setStarred();
		vh.positionInList = position;
	}

	static class TextItemViewHolder extends RecyclerView.ViewHolder{

		public AutofitTextView text;
		public int             positionInList;
		public boolean         isStarred;

		public void setStarred(){
			if(isStarred){
				((ImageView) itemView.findViewById(R.id.btn_star)).setImageResource(android.R.drawable.btn_star_big_on);
			}
			else{
				((ImageView) itemView.findViewById(R.id.btn_star)).setImageResource(android.R.drawable.btn_star_big_off);
			}
		}

		public TextItemViewHolder(View itemView, final Context context){
			super(itemView);
			ImageView iv = ((ImageView) itemView.findViewById(R.id.btn_star));

			iv.setOnClickListener(new View.OnClickListener(){
				@Override public void onClick(final View v){
					isStarred = !isStarred;
					TweetHandler.setStar(isStarred, positionInList);
				}
			});
			text = (AutofitTextView) itemView.findViewById(R.id.item_text);

			itemView.setOnLongClickListener(
					new View.OnLongClickListener(){
						@Override public boolean onLongClick(final View v){
							new MaterialDialog.Builder(context)
									.title(R.string.delete)
									.content(text.getText())
									.inputRangeRes(1, 140, R.color.colorPrimary)
									.icon(context.getDrawable(android.R.drawable.ic_delete))
									.positiveText(R.string.yes)
									.onPositive(
											new MaterialDialog.SingleButtonCallback(){
												@Override public void onClick(@NonNull final MaterialDialog materialDialog,
												                              @NonNull final DialogAction dialogAction){
													TweetHandler.deleteTweet(positionInList);
												}
											}
									).show();
							return true;
						}
					}
			);
		}

	}
}
