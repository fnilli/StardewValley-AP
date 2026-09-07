package com.group16.stardewvalley.model.animal;



public enum ProductQuality {
	NORMAL(1.0, 0.0, 0.5),
	SILVER(1.25, 0.5, 0.7),
	GOLD(1.5, 0.7, 0.9),
	IRIDIUM(2.0, 0.9, 100.0);

	private final Double priceCoefficient;
	private final Double startOfTheRange;
	private final Double endOfTheRange;

	ProductQuality(Double priceCoefficient, Double startOfTheRange, Double endOfTheRange) {
		this.priceCoefficient = priceCoefficient;
		this.startOfTheRange = startOfTheRange;
		this.endOfTheRange = endOfTheRange;
	}

	public static ProductQuality getProductQuality(Animal animal) {

		double random = Math.random();
		double quality = ((double) animal.getFriendship() / 1000) * (0.5 + 0.5 * random);

		for (ProductQuality value : ProductQuality.values()) {
			if (value.startOfTheRange <= quality &&  quality <= value.endOfTheRange ){
				return value;
			}
		}
		return null;
	}

	public Double getPriceCoefficient() {
		return priceCoefficient;
	}

	//	private double priceCoefficient(Animal animal){
//
//		double random = Math.random();
//		double quality = ((double) animal.getFriendship() / 1000) * (0.5 + 0.5 * random);
//
//		if(quality > 0.0 && quality < 0.5)    return 1;
//		if(quality > 0.5 && quality < 0.7)    return 1.25;
//		if(quality > 0.7 && quality < 0.9)    return 1.5;
//		else                                  return 2;
//
//	}
}