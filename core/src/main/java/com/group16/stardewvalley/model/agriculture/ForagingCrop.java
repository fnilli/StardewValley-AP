package com.group16.stardewvalley.model.agriculture;

import com.group16.stardewvalley.model.items.Item;

public class ForagingCrop extends Item {
    private final CropType cropType;
    public ForagingCrop(String name, int price, CropType cropType) {
        super(name, price);
        this.cropType = cropType;
    }

    public CropType getCropType() {
        return cropType;
    }
}
