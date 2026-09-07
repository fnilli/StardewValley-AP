package com.group16.stardewvalley.model.tools;


import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.*;

public abstract class Gadget extends Item {
   String name;
   String material;

   public Gadget(String name, int price) {
      super(name, price);
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public abstract Result use(Tile targetTile, Game game);

   public String getMaterial() {
      return material;
   }

   public void setMaterial(String newMaterial) {
      this.material = material;
   }

   @Override
   public String getAssetPath() {
       return "tools/" + name + "/" + material + "_" + name + ".png";
   }

}
