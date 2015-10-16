package com.nullcognition.yaatc.model.item;
// ersin 14/10/15 Copyright (c) 2015+ All rights reserved.


public class ImageItem extends Item{

	public final String imageUrl;
	public ImageItem(String text, String imageUrl){
		super(text);
		this.imageUrl = imageUrl;
	}
}
