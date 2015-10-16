package com.nullcognition.yaatc.view.fragment;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.di.fragment.BaseFragment;
import com.nullcognition.yaatc.model.item.FeedItem;
import com.nullcognition.yaatc.model.item.TextItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

public class TestFragment extends BaseFragment{

	public static final String TAG = TestFragment.class.getSimpleName();

	@Override protected void injectSelf(){
		fragmentComponent.inject(this);

	}
	@Override protected int getFragmentLayout(){
		return R.layout.fragment_test;
	}

	@Bind(R.id.recyclerView) RecyclerView recyclerView;

	private void initRecyclerView(final AppCompatActivity activity){
		recyclerView.setLayoutManager(new LinearLayoutManager(activity));
//		FeedAdapter adapter = new FeedAdapter(activity, getAnimals());
//		recyclerView.setAdapter(adapter);
	}

	@Override public void onViewCreated(final View view, final Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		AppCompatActivity activity = (AppCompatActivity) getActivity();
		initRecyclerView(activity);
	}


	private List<FeedItem> getAnimals(){
		List<FeedItem> animals = new ArrayList<>();

		animals.add(new TextItem(" snth teu esnh toeu  nhtoaeu snthoaeu  eu u asuhtnaosu thaouo aou snaoe us nthaoeus nhaoeu ouos nthaoeu snteu uaeouo aeoe ua ueoue ouoea  ee"));
		animals.add(new TextItem("Baliness of the things that go in the night"));
		animals.add(new TextItem("Bengal because the things that areth me most random are often the thingks nthat go"));
		animals.add(new TextItem("Corat"));
		animals.add(new TextItem("Manx"));
		animals.add(new TextItem("Nebelung"));
		animals.add(new TextItem("Aidi"));
		animals.add(new TextItem("Chinook"));
		animals.add(new TextItem("Appenzeller"));
		animals.add(new TextItem("Collie"));
		animals.add(new TextItem("American Curl"));
		animals.add(new TextItem("Baliness"));
		animals.add(new TextItem("Bengal"));
		animals.add(new TextItem("Corat"));
		animals.add(new TextItem("Manx"));
		animals.add(new TextItem("Nebelung"));
		animals.add(new TextItem("Aidi"));
		animals.add(new TextItem("Chinook"));
		animals.add(new TextItem("Appenzeller"));
		animals.add(new TextItem("Collie"));
		animals.add(new TextItem("American Curl"));
		animals.add(new TextItem("Baliness"));
		animals.add(new TextItem("Bengal"));
		animals.add(new TextItem("Corat"));
		animals.add(new TextItem("Manx"));
		animals.add(new TextItem("Nebelung"));
		animals.add(new TextItem("Aidi"));
		animals.add(new TextItem("Chinook"));
		animals.add(new TextItem("Appenzeller"));
		animals.add(new TextItem("Collie"));

		Collections.shuffle(animals);
		return animals;
	}
}
