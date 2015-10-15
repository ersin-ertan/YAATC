package com.nullcognition.yaatc;

import android.os.Bundle;

import com.nullcognition.yaatc.di.activity.BaseActivity;
import com.nullcognition.yaatc.di.fragment.BaseFragment;

public class MainActivity extends BaseActivity{

	BaseFragment replace;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		replace = new LoginFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.activity_main_rootLayout, replace, LoginFragment.TAG).commit();
	}

	@Override protected void onPostCreate(final Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);
		// did not work right after call, even with internal ft.executePendingTransactions();
		navigator.switchFragment(replace, FeedFragment.class);
	}

	//	@Override public void onActivityResult(int requestCode, int resultCode, Intent data){
//		super.onActivityResult(requestCode, resultCode, data);
//		Fragment fragment = getSupportFragmentManager().findFragmentByTag(navigator.getCurrentFragmentTag());
//		if(fragment != null){
//			fragment.onActivityResult(requestCode, resultCode, data); }
//	}

	@Override protected int getActivityLayout(){ return R.layout.activity_main; }

	@Override protected void injectSelf(){ activityComponent.inject(this); }


}
