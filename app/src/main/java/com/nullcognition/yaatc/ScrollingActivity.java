package com.nullcognition.yaatc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

public class ScrollingActivity extends AppCompatActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrolling);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){

				// can be provided through dagger

				new MaterialDialog.Builder(ScrollingActivity.this)
						.title(R.string.app_name_full)
						.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI)
						.inputRangeRes(1, 140, R.color.colorPrimary)
						.icon(getDrawable(R.drawable.ic_chat_24dp))
						.positiveText(R.string.tweet)
						.input(R.string.whats_happ, R.string.empty, new MaterialDialog.InputCallback(){
							@Override
							public void onInput(MaterialDialog dialog, CharSequence input){
								// twitter call
							}
						}).show();

//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//				        .setAction("Action", null).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu_scrolling, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();

		if(id == R.id.action_settings){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
