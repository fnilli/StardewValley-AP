package com.group16.stardewvalley.model.animal;

import com.group16.stardewvalley.model.items.Item;

public class AnimalProduct extends Item implements Sellable{

    AnimalProductTypes productType;
    ProductQuality productQuality;

    public AnimalProduct(AnimalProductTypes productType, int price, ProductQuality productQuality) {
        super(productType.getName(), price);
        this.productType = productType;
        this.productQuality = productQuality;
    }

    public AnimalProductTypes getProductType() {
        return productType;
    }

    public ProductQuality getProductQuality() {
        return productQuality;
    }
}
