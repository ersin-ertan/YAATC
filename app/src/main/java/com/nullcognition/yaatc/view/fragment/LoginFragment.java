package com.nullcognition.yaatc.view.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.di.fragment.BaseFragment;
import com.nullcognition.yaatc.view.PasswordEvent;
import com.nullcognition.yaatc.view.fragment.presenter.LoginPresenter;

import butterknife.OnClick;
import butterknife.OnLongClick;
import io.paperdb.Paper;

public class LoginFragment extends BaseFragment<LoginPresenter>{

	public static final  String TAG     = "LoginFragment";
	public static final  String PASS    = "pass";
	private static final String DEFAULT = "default";

	@OnLongClick(R.id.centered_image) boolean loginLogo(final View view){
		Paper.book().destroy();
		Paper.book().write(PASS, DEFAULT);
		return true;
	}

	@OnClick(R.id.twitter_login_button) void login(final View view){
		if(DEFAULT.equals(Paper.book().read(PASS, DEFAULT))){
			new MaterialDialog.Builder(this.getContext())
					.title(R.string.set_passkey)
					.inputType(InputType.TYPE_CLASS_NUMBER)
					.icon(this.getContext().getDrawable(android.R.drawable.ic_lock_lock))
					.positiveText(R.string.set_passkey)
					.input("Passkey Here", null, new MaterialDialog.InputCallback(){
						@Override public void onInput(final MaterialDialog materialDialog, final CharSequence charSequence){
							Paper.book().write(PASS, charSequence.toString());
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
										Toast.makeText(LoginFragment.this.getContext(), "Passkey Incorrect", Toast.LENGTH_SHORT).show();
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

	public static class Event{ }

}
