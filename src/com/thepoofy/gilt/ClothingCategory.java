package com.thepoofy.gilt;


public enum ClothingCategory {

	TUXEDO("Tuxedos"),
	SUIT("Suits"),

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

	BOOTS("Boots"),
	SNEAKERS("Sneakers"),

	SUNGLASSES("Sunglasses"),
	WATCH("Watches"),
	TIE("Ties"),

	TOTE_BAG("Tote Bags", "tote bag"),
	MESSENGER_BAG("Messenger Bags", "messenger bag"),
	;

	public final String name;
	public final String searchText;

	private ClothingCategory(String name)
	{
		this.name = name;
		this.searchText = name().toLowerCase();
	}

	private ClothingCategory(String name, String key)
	{
		this.name = name;
		searchText = key;
	}


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
			if(cat.searchText.equals(name)){
				return cat;
			}
		}

		//DEFAULT TO SUNGLASSES - COMPLETELY ARBITRARY
		//TODO determine best category to default to
		return SUNGLASSES;
	}
}
