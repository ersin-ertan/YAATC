package com.nullcognition.yaatc.di.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.api.TweetHandler;

import dagger.Module;
import dagger.Provides;

@Module public class FragmentModule{

	private final BaseFragment baseFragment;
	public FragmentModule(final BaseFragment bf){ baseFragment = bf; }

	@Provides BaseFragment provideBaseFragment(){ return baseFragment; }

	@Provides public MaterialDialog.Builder provideTwitterDialog(final BaseFragment baseFragment, final Context context,
	                                                             final TweetHandler tweetHandler){

		return new MaterialDialog.Builder(context)
				.title(R.string.app_name)
				.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI)
				.inputRangeRes(1, 140, R.color.colorPrimary)
				.icon(context.getDrawable(R.drawable.ic_chat_24dp))
				.positiveText(R.string.tweet)
				.input(R.string.whats_happ, R.string.empty, new MaterialDialog.InputCallback(){
							@Override
							public void onInput(MaterialDialog dialog, CharSequence input){
								tweetHandler.setText(String.valueOf(input));
								Snackbar.make(baseFragment.getView(), baseFragment.getResources().getString(R.string.new_tweet), Snackbar.LENGTH_LONG)
								        .setAction(baseFragment.getResources().getString(R.string.scroll_home),
										        new View.OnClickListener(){
											        @Override public void onClick(final View v){
												        Toast.makeText(baseFragment.getActivity(), "RecyclerViewSmoothScroll", Toast.LENGTH_SHORT).show();
											        }
										        }).show();
							}
						}

				);
	}


}
