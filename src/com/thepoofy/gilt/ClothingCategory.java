package com.thepoofy.gilt;


public enum ClothingCategory {

	JACKET,
	BLAZER,
	COAT,
	ANORAK,
	SWEATER,
	TRENCH,
	PEACOAT,
	CARDIGAN,
	HOODIE,
	VEST,
	HENLEY,
	SHIRT,
	TSHIRT("t-shirt"),
	
	PANTS,
	TROUSERS,
	CHINOS,
	JEANS,
	
	BAG,
	TOTE_BAG("tote bag"),
	
	BOOTS,
	SNEAKERS,
	
	SUNGLASSES,
	
	TIE,
	
	TUXEDO
	;
	
	public final String searchText;
	
	private ClothingCategory()
	{
		searchText = name().toLowerCase();
	}
	
	private ClothingCategory(String key)
	{
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
			if(cat.searchText.equals(name)){
				return cat;
			}
		}
		
		//DEFAULT TO SUNGLASSES - COMPLETELY ARBITRARY
		//TODO determine best category to default to
		return SUNGLASSES;
	}
}
