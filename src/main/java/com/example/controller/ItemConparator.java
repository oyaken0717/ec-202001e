package com.example.controller;

import java.util.Comparator;

import com.example.domain.Item;

public class ItemConparator implements Comparator<Item> {

	@Override
	public int compare(Item o1, Item o2) {

		return o1.priceM > o2.priceM ? -1 : 1;
	}

}
