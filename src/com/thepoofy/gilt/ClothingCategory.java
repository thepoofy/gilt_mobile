package com.thepoofy.gilt;

import java.util.LinkedList;
import java.util.List;


public enum ClothingCategory {

	TOTE_BAG("Tote Bags", "tote bag"),
	MESSENGER_BAG("Messenger Bags", "messenger bag"),

	TEE("Tees"),
	BLOUSE("Blouses"),
	TANKS("Tanks"),
	SWEATSHIRT("Sweatshirts"),
	SWEATPANTS("Sweatpants"),

	NECKLACE("Necklaces"),
	EARRINGS("Earrings"),
	RING("Rings"),
	BANGLE("Bangles"),
	BRACELET("Bracelets"),
	CUFF("Cuffs"),
	HOOP("Hoops", "hoops","hoop"),

	TUXEDO("Tuxedos"),
	SUIT("Suits"),

	TSHIRT("T-Shirts","t-shirt"),
	BLAZER("Blazers"),
	SWEATER("Sweaters"),
	CARDIGAN("Cardigans"),
	HOODIE("Hoodies"),
	VEST("Vests"),
	HENLEY("Henleys"),
	SHIRT("Shirts"),

	BOARDSHORTS("Boardshorts"),
	PANTS("Pants","pants","pant"),
	TROUSERS("Trousers"),
	CHINOS("Chinos"),
	JEANS("Jeans"),
	SKIRTS("Skirts","skirt"),

	SHOES("Shoes"),
	BOOTS("Boots"),
	SNEAKERS("Sneakers"),
	OXFORDS("Oxfords"),
	FOOTWEAR("Footwear","heel","pump","wedge"),

	DRESS("Dresses","dress", "frock", "shift"),
	COAT("Coats", "anorak", "parka", "overcoats", "trench","jacket", "peacoat"),

	CAPS("Caps", "cap"),

	SUNGLASSES("Sunglasses"),
	WATCH("Watches"),
	TIE("Ties"),

	TOP("Tops"),


	//BEDDING("Bedding","pillow","pillow case","sham","duvet","duvet cover","flat sheet","fitted sheet","mattress","bed"),


	SET("Sets"),


//	OTHER("Other")
	;

	public final String name;
	public final List<String> searchText;

	private ClothingCategory(String name)
	{
		this.name = name;
		this.searchText = new LinkedList<String>();
		searchText.add(name().toLowerCase());

	}

	private ClothingCategory(String name, String... keys)
	{
		this.name = name;
		this.searchText = new LinkedList<String>();
		for(String key : keys)
		{
			searchText.add(key);
		}
	}


	/**
	 *
	 * @param name
	 * @return
	 */
	public static ClothingCategory find(String name)
	{
		if(name == null)
		{
			return SUNGLASSES;
		}

		for(ClothingCategory cat : ClothingCategory.values()){
			if(cat.name().equalsIgnoreCase(name))
			{
				return cat;
			}
			if(cat.name.equals(name)){
				return cat;
			}
			if(cat.searchText.contains(name)){
				return cat;
			}
		}

		//DEFAULT TO SUNGLASSES - COMPLETELY ARBITRARY
		//TODO determine best category to default to
		return SUNGLASSES;
	}
}
