package com.nullcognition.yaatc.view.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.di.fragment.BaseFragment;
import com.nullcognition.yaatc.view.PasswordEvent;
import com.nullcognition.yaatc.view.fragment.presenter.LoginPresenter;

import butterknife.OnClick;
import butterknife.OnLongClick;
import io.paperdb.Paper;

public class LoginFragment extends BaseFragment<LoginPresenter> implements GoogleApiClient.OnConnectionFailedListener{

	public static final  String TAG        = "LoginFragment";
	public static final  String PASS       = "pass";
	private static final String DEFAULT    = "default";
	private static final int    RC_SIGN_IN = 9001;

	@OnLongClick(R.id.centered_image) boolean loginLogo(final View view){
		Paper.book().destroy();
		Paper.book().write(PASS, DEFAULT);
		return true;
	}

	@OnClick(R.id.twitter_login_button) void login(final View view){

		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//				.requestEmail()
				.build();

		GoogleApiClient gapiClient = new GoogleApiClient.Builder(getContext())
				.enableAutoManage(getActivity(), this)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();

		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(gapiClient);
		startActivityForResult(signInIntent, RC_SIGN_IN);

		if(DEFAULT.equals(Paper.book().read(PASS, DEFAULT))){
			new MaterialDialog.Builder(this.getContext())
					.title(R.string.set_passkey)
					.inputType(InputType.TYPE_CLASS_NUMBER)
					.icon(this.getContext().getDrawable(android.R.drawable.ic_lock_lock))
					.positiveText(R.string.set_passkey)
					.input("Passkey Here", null, new MaterialDialog.InputCallback(){
						@Override public void onInput(final MaterialDialog materialDialog, final CharSequence charSequence){
							Paper.book().write(PASS, charSequence.toString());
							navigator.switchFragment(LoginFragment.this, FeedFragment.class);
						}
					}).show();
		}
		else{
			new MaterialDialog.Builder(this.getContext())
					.title(R.string.unlock)
					.inputType(InputType.TYPE_CLASS_NUMBER)
					.icon(this.getContext().getDrawable(android.R.drawable.ic_partial_secure))
					.positiveText(R.string.unlock)
					.input("Passkey Here", null, new MaterialDialog.InputCallback(){
								@Override public void onInput(final MaterialDialog materialDialog, final CharSequence charSequence){
									if(charSequence.toString().equals(Paper.book().read(PASS))){
										navigator.switchFragment(LoginFragment.this, FeedFragment.class);
									}
									else{
										Toast.makeText(LoginFragment.this.getContext(), R.string.passkey_incorrect, Toast.LENGTH_SHORT).show();
									}
								}
							}

					).show();
		}
	}

	public void onEvent(PasswordEvent passwordEvent){}

	@Override protected void createPresenter(){ }

	@Override protected void injectSelf(){
		fragmentComponent.inject(this);
	}

	@Override protected int getFragmentLayout(){ return R.layout.fragment_login; }

	@Override public void onConnectionFailed(final ConnectionResult connectionResult){

	}

	public static class Event{ }

	@Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			handleSignInResult(result);
		}
	}

	private void handleSignInResult(GoogleSignInResult result) {
		Log.d(TAG, "handleSignInResult:" + result.isSuccess());
		if (result.isSuccess()) {
			// Signed in successfully, show authenticated UI.
			GoogleSignInAccount acct = result.getSignInAccount();
//			mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
//			updateUI(true);
		} else {
			// Signed out, show unauthenticated UI.
//			updateUI(false);
		}
	}

	// no logout/unauth, too messy/complication ridden
//	Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//			new ResultCallback<Status>() {
//		@Override
//		public void onResult(Status status) {
//		}
//	});

}
