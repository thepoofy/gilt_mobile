package com.thepoofy.gilt;

import java.util.LinkedList;
import java.util.List;


public enum ClothingCategory {

	DRESS("Dresses","dress", "frock", "shift"),
	TOP("Tops"),
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

	OVERCOAT("Overcoats"),
	JACKET("Jackets"),
	BLAZER("Blazers"),
	COAT("Coats"),
	ANORAK("Anoraks"),
	SWEATER("Sweaters"),
	TRENCH("Trench Coats"),
	PEACOAT("Peacoats"),
	CARDIGAN("Cardigans"),
	HOODIE("Hoodies"),
	VEST("Vests"),
	HENLEY("Henleys"),
	SHIRT("Shirts"),
	TSHIRT("T-Shirts","t-shirt"),

	PANTS("Pants"),
	TROUSERS("Trousers"),
	CHINOS("Chinos"),
	JEANS("Jeans"),
	BOARDSHORTS("Boardshorts"),

	BOOTS("Boots"),
	SNEAKERS("Sneakers"),

	SUNGLASSES("Sunglasses"),
	WATCH("Watches"),
	TIE("Ties"),

	TOTE_BAG("Tote Bags", "tote bag"),
	MESSENGER_BAG("Messenger Bags", "messenger bag"),

	SET("Sets"),
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
