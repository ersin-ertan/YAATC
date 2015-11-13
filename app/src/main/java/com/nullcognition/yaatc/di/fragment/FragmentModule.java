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
import com.nullcognition.yaatc.di.activity.BaseActivity;
import com.nullcognition.yaatc.di.application.navigation.Navigator;
import com.nullcognition.yaatc.model.Tweet;
import com.nullcognition.yaatc.view.activity.MainActivity;
import com.nullcognition.yaatc.view.fragment.FeedFragment;
import com.nullcognition.yaatc.view.fragment.LoginFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.paperdb.Paper;

@Module public class FragmentModule{

	private final BaseFragment baseFragment;

	public FragmentModule(final BaseFragment bf){ baseFragment = bf; }

	@Provides BaseFragment provideBaseFragment(){ return baseFragment; }

	@Provides @Named(FeedFragment.TWEET)
	public MaterialDialog provideTwitterDialog(final Context context, final BaseActivity baseActivity){

		return new MaterialDialog.Builder(context)
				.title(R.string.app_name)
				.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI)
				.inputRangeRes(1, 140, R.color.colorPrimary)
				.icon(context.getDrawable(R.drawable.ic_chat_24dp))
				.positiveText(R.string.tweet)
				.input(R.string.whats_happ, R.string.empty, new MaterialDialog.InputCallback(){
							@Override
							public void onInput(MaterialDialog dialog, CharSequence input){

								TweetHandler.sendTweet(Tweet.newTweet(String.valueOf(input), 0, ((MainActivity) baseActivity).getLastLocation()));

								Snackbar.make(baseFragment.getView(), baseFragment.getResources().getString(R.string.new_tweet), Snackbar.LENGTH_LONG)
								        .setAction(baseFragment.getResources().getString(R.string.scroll_home),
										        new View.OnClickListener(){
											        @Override public void onClick(final View v){
												        if(baseFragment instanceof FeedFragment){
													        ((FeedFragment) baseFragment).smoothScrollToTop();
												        }
											        }
										        }).show();
							}
						}

				).build();
	}

	@Provides @Named(LoginFragment.UNLOCK)
	public MaterialDialog provideUnlockDialog(final Context context, final Navigator navigator){

		return new MaterialDialog.Builder(context)
				.title(R.string.unlock)
				.inputType(InputType.TYPE_CLASS_NUMBER)
				.icon(context.getDrawable(android.R.drawable.ic_partial_secure))
				.positiveText(R.string.unlock)
				.input(context.getString(R.string.passkey_here), null, new MaterialDialog.InputCallback(){
							@Override public void onInput(final MaterialDialog materialDialog, final CharSequence charSequence){
								if(charSequence.toString().equals(Paper.book().read(LoginFragment.PASS))){
									navigator.switchFragment(baseFragment, FeedFragment.class);
								}
								else{
									Toast.makeText(context, R.string.passkey_incorrect, Toast.LENGTH_SHORT).show();
								}
							}
						}
				).build();
	}
}
