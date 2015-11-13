package com.nullcognition.yaatc.view.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.di.fragment.BaseFragment;
import com.nullcognition.yaatc.view.PasswordEvent;
import com.nullcognition.yaatc.view.fragment.presenter.LoginPresenter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import butterknife.OnClick;
import butterknife.OnLongClick;
import io.paperdb.Paper;

public class LoginFragment extends BaseFragment<LoginPresenter>{

	public static final  String TAG               = "LoginFragment";
	public static final  String PASS              = "pass";
	private static final String DEFAULT           = "default";
	private static final int    RC_SIGN_IN        = 9001;
	public static final  String LOGIN_GAPI_CLIENT = "loginGoogleApiClient";
	public static final  String UNLOCK            = "unlock";

	@Inject @Named(LOGIN_GAPI_CLIENT)    GoogleApiClient          googleApiClient;
	@Inject @Named(LoginFragment.UNLOCK) Provider<MaterialDialog> unlockDialog;

	@OnLongClick(R.id.centered_image) boolean loginLogo(final View view){
		Paper.book().destroy();
		Paper.book().write(PASS, DEFAULT);
		return true;
	}

	@OnClick(R.id.twitter_login_button) void login(final View view){

		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}

	public void onEvent(PasswordEvent pe){} // needed for event bus

	@Override protected void createPresenter(){ }

	@Override protected void injectSelf(){ fragmentComponent.inject(this); }

	@Override protected int getFragmentLayout(){ return R.layout.fragment_login; }

	@Override public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == RC_SIGN_IN){
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			handleSignInResult(result);
		}
	}

	private void handleSignInResult(GoogleSignInResult result){
		Log.d(TAG, "handleSignInResult:" + result.isSuccess());
		if(result.isSuccess()){
			final GoogleSignInAccount acct = result.getSignInAccount();
			if(DEFAULT.equals(
					Paper.book(acct.getId()).read(PASS, DEFAULT))){
				Context context = getContext();
				new MaterialDialog.Builder(context)
						.title(R.string.set_passkey)
						.inputType(InputType.TYPE_CLASS_NUMBER)
						.icon(context.getDrawable(android.R.drawable.ic_lock_lock))
						.positiveText(R.string.set_passkey)
						.input(context.getString(R.string.passkey_here), null, new MaterialDialog.InputCallback(){
							@Override public void onInput(final MaterialDialog materialDialog, final CharSequence charSequence){
								Paper.book(acct.getId()).write(LoginFragment.PASS, charSequence.toString());
								navigator.switchFragment(LoginFragment.this, FeedFragment.class);
							}
						}).build().show();
			}
			else{
				Toast.makeText(getContext(), "Hey, " + acct.getDisplayName(), Toast.LENGTH_SHORT).show();
				unlockDialog.get().show();
			}
		}
		else{ Toast.makeText(getContext(), "Trouble Connecting...", Toast.LENGTH_SHORT).show(); }
	}
}
