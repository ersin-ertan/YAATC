package com.nullcognition.yaatc.model.item;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


public class TextItem extends Item{

	public boolean isStarred;

	public TextItem(String text, final boolean stared){
		super(text);
		isStarred = stared;
	}
}
