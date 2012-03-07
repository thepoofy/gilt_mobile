package com.thepoofy.gilt;

import java.util.LinkedList;
import java.util.List;


public enum ClothingCategory {

	TOTE_BAG("Tote Bags", "tote bag"),
	MESSENGER_BAG("Messenger Bags", "messenger bag"),
	BAG("Bags","tote","clutch","satchel","bag","hobo","crossbody","pochette","messenger"),
	WALLET("Wallets","wallet","billfold"),
	ACCESSORIES("Accessories","card case","card holder","passport","e-reader","phone","ipad","case","shoelaces","gloves"),
	LUGGAGE("Luggage","suitcase","laptop case","carry on"),
	SOCK("Socks","socks","sock"),

	BLOUSE("Blouses","blouse","blouses"),
	TANKS("Tanks"),
	SWEATSHIRT("Sweatshirts"),
	SWEATPANTS("Sweatpants"),

	GOWN("Gowns","gown","gowns"),

	JUMPSUIT("Jumpsuits","jumpsuit","jumpsuits"),
	TSHIRT("T-Shirts & Polos","t-shirt","polo","tees","tee"),
	BLAZER("Blazers"),
	SWEATER("Sweaters"),
	CARDIGAN("Cardigans"),
	HOODIE("Hoodies"),
	VEST("Vests"),
	SHIRT("Shirts","shirt","workshirt","rugby", "pullover","button up","crewneck","henley","button down"),
	SCARF("Scarves","scarf","scarves"),

	UNDERWEAR("Underwear","boxers","briefs","trunks","long john"), //can't put brief here because of brief case conflict
	PANTS("Pants","pants","pant","chino","chinos","capri","corduroys"),
	TROUSERS("Trousers","trouser","trousers"),
	LEGGING("Leggings","legging","leggings"),
	TIGHT("Tights","tights","tight"),
	SKIRTS("Skirts","skirt"),
	JEANS("Jeans","jean","jeans","bootcut","boot cut"),

	SUNGLASSES("Sunglasses","glasses","frames","frame","sunglasses"),

	SHOES("Shoes","shoe","shoes","plimsolls"),
	BOOTS("Boots","boot","boots"),
	SNEAKERS("Sneakers","sneaker","sneakers","trainer"),
	DRESS_SHOES("Dress Shoes","oxfords","oxford","wingtips","bucks","loafers","loafer","monk","monks","brogues"),
	FOOTWEAR("Footwear","heel","pump","wedge","sandal","flat","slingback","bootie","slip ons", "moccasins"),

	NECKLACE("Necklaces"),
	EARRINGS("Earrings"),
	RING("Rings"),
	BANGLE("Bangles"),
	BRACELET("Bracelets"),
	CUFF("Cuffs"),
	HOOP("Hoops", "hoops","hoop"),
	PENDANT("Pendants"),

	DRESS("Dresses","dress", "frock", "shift","maxi","mini","sheath","romper"),

	COAT("Coats", "coat", "anorak", "parka", "overcoats", "trench","jacket", "peacoat", "raincoat", "windbreaker", "bomber", "rain slicker","bolero"),
	CAPE("Capes","cape","capes"),
	HAT("Caps", "cap","hat"),
	INTIMATES("Intimates","babydoll","lingerie"),
	SHORTS("Shorts","shorts","short","boardshorts", "swim trunks"),

	WATCH("Watches"),
	BELT("Belts","belt"),
	GLOVES("Gloves"),
	POCKET_SQUARES("Pocket Squares","pocket square"),
	TIE("Ties","tie","bow","bowtie"),

	TOP("Tops","top","tank","tunic","cami","wrap","camisole","turtleneck"),

	BLANKET("Blankets"),
	UMBRELLA("Umbrellas"),

	TUXEDO("Tuxedos"),
	SUIT("Suits"),


	//BEDDING("Bedding","pillow","pillow case","sham","duvet","duvet cover","flat sheet","fitted sheet","mattress","bed"),


	SET("Sets"),


	OTHER("Other")
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
