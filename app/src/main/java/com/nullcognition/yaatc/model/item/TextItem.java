package com.nullcognition.yaatc.model.item;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


public class TextItem extends Item{

	public boolean isStarred;
	public String location;

	public TextItem(String text, final boolean stared, String loc){
		super(text);
		isStarred = stared;
		location = loc;
	}
}
