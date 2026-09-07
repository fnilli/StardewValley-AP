package com.group16.stardewvalley.controller.shops;

import com.group16.stardewvalley.model.agriculture.Seed;
import com.group16.stardewvalley.model.animal.Sellable;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.shops.Building;
import com.group16.stardewvalley.model.shops.BuildingType;
import com.group16.stardewvalley.model.shops.Shop;
import com.group16.stardewvalley.model.shops.UpgradeType;
import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.tools.Gadget;
import com.group16.stardewvalley.model.tools.ToolDataManager;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.model.map.Location;

import java.util.Set;
import java.util.regex.Matcher;

public class ShopController {


    public Result handleCommand(String command, Matcher matcher) {
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        String[] parts = command.split(" ");
        Location l = currentPlayer.getLocationLocation();

        // بررسی مکان‌های مجاز
        if (!(l == Location.Blacksmith || l == Location.JojaMart ||
                l == Location.CarpentersShop || l == Location.FishShop ||
                l == Location.MarniesRanch || l == Location.TheStardropSaloon ||
                l == Location.PierresGeneralStore)) {
            return new Result(false, "You're not in a valid shop location");
        }

        switch (parts[0].toLowerCase()) { // تبدیل به حروف کوچک برای عدم حساسیت
            case "upgrade":
                return upgradeTool(matcher);

            case "show":
                if (parts.length < 3) {
                    return new Result(false, "Invalid show command");
                }
                if (parts[2].equalsIgnoreCase("products")) {
                    return handleShowAllProducts();
                } else if (parts[2].equalsIgnoreCase("available")) {
                    return handleShowAvailableProducts();
                }
                break;

            case "purchase":
                return handlePurchase(l, matcher);

            case "tools":
                if (l != Location.Blacksmith) {
                    return new Result(false, "Tool upgrades? You've come to the wrong place. " +
                            "The blacksmith's shop is in another part of town.");
                }
                return upgradeTool(matcher);

            case "sell":
                return handleSellProduct(matcher);

            default:
                return new Result(false, "Invalid command");
        }

        return new Result(false, "Command processing failed");
    }

    public Result handleSellProduct(Matcher matcher) {
        String productName = matcher.group("productName");
        String countStr = matcher.group("count");
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        Item targetItem = currentPlayer.getInventory().getItemByName(productName);

        // اصلا نداشته باشد این کالا را
        if (targetItem == null) {
            return new Result(false, "You don’t have this item in your inventory");
        }

        int count;
        if (countStr == null) {
            count = currentPlayer.getInventory().getNumberOfItem(targetItem);
        } else {
            count = Integer.parseInt(countStr);
        }

        // این تعداد را نداشته باشد
        if (currentPlayer.getInventory().getNumberOfItem(targetItem) < count) {
            return new Result(false, "You don’t have enough of this item to sell!");
        }

        // قابلیت فروش نداشته باشد
        if (!(targetItem instanceof Sellable)) {
            return new Result(false, "This item cannot be sold!");
        }

        int sellPrice = targetItem.getPrice();

        boolean isNear = false;
        for (Building building : App.getActiveGame().getBuildings()) {
            if (building.getBuildingType() == BuildingType.Shipping_Bin) {
                if (building.isNearBuilding(currentPlayer.getPosition())) {
                    isNear = true;
                }
            }
        }
        // نزدیک ان نباشد
        if (!isNear){
            return new Result(false, "You need to be near a Shipping Bin to sell items!");
        }

        currentPlayer.increaseTodayIncome(targetItem.getPrice());
        currentPlayer.getInventory().removeItem(targetItem, count);
        return new Result(true, "Item sold successfully!");
    }


    public Result handleShowAllProducts() {
        Location currentShop = App.getActiveGame().getCurrentPlayer().getLocationLocation();
        if (currentShop == null) {
            return new Result(false, "You should go to shop!");
        }
        if (currentShop.getShopByLocation() == null) {
            return new Result(false, "You should go to shop!");
        }

        Set<Item> items = currentShop.getShopByLocation().getAllProducts();
        if (items == null || items.isEmpty()) {
            return new Result(false, "No products available");
        }

        Shop targetShop = currentShop.getShopByLocation();

        if (!targetShop.isOpen()) {
            return new Result(false, "Sorry! we're closed! Shop hours: 9 AM to 4 PM");
        }

        StringBuilder productsInfo = new StringBuilder("Available Products:\n");

        for (Item item : items) {
            productsInfo.append(String.format(
                    "- %s (Price: %d)%n",
                    item.getName(),
                    item.getPrice()
            ));
        }

        return new Result(true, productsInfo.toString());
    }


    public Result handleShowAvailableProducts() {

        Location currentShop = App.getActiveGame().getCurrentPlayer().getLocationLocation();
        if (currentShop.getShopByLocation() == null) {
            return new Result(false, "You should go to shop!");
        }

        Shop targetShop = currentShop.getShopByLocation();
        if (!targetShop.isOpen()) {
            return new Result(false, "Sorry! we're closed! Shop hours: 9 AM to 4 PM");
        }

        Set<Item> items = currentShop.getShopByLocation().getAllProducts();
        if (items == null || items.isEmpty()) {
            return new Result(false, "No products available");
        }

         Season currentSeason = TimeDate.getInstance(App.getActiveGame()).getSeason();
        StringBuilder productsInfo = new StringBuilder("Available Products:\n");
        int productCount = 0;

        for (Item item : items) {
            if (item instanceof Seed) {
                Seed seed = (Seed) item;
                if (seed.isAvailableInSeason(currentSeason)) {
                    productsInfo.append(String.format(
                            "- %s (Price: %d, Type: %s, Daily Limit: %d)%n",
                            seed.getName(),
                            seed.getPrice(),
                            seed.getType(),
                            seed.getDailyLimit()
                    ));
                    productCount++;
                }
            } else {

                productsInfo.append(String.format(
                        "- %s (Price: %d)%n",
                        item.getName(),
                        item.getPrice()
                ));
                productCount++;
            }
        }

        if (productCount == 0) {
            return new Result(false, "No products available for current season: " + currentSeason);
        }

        return new Result(true, productsInfo.toString());
    }


    private Result handlePurchase(Location location, Matcher matcher) {
        String productName = matcher.group("productName");
        String countStr = matcher.group("count");
        int count;
        if (countStr == null) {
            count = 1;
        } else {
            count = Integer.parseInt(countStr);
        }
        Item targetItem = null;
        Shop targetShop = App.getActiveGame().getCurrentPlayer().getLocationLocation().getShopByLocation();

        targetItem = targetShop.findItemByName(productName);

        //مراجعه در زمان نامناسب
        if (!targetShop.isOpen()) {
            return new Result(false, "Sorry! we're closed! Shop hours: 9 AM to 4 PM");
        }

        // فروشگاه مورد نظر این محصولو نداشته باشه
        if (targetItem == null) {
            return new Result(false, "Sorry, we don't stock that item. " +
                    "Try the specialty shops around town.");
        }

        //  موجودی لازم واسه خرید نداشته باشه
        if (App.getActiveGame().getCurrentPlayer().getCoin() < targetItem.getPrice()) {
            return new Result(false, "Oops! Too expensive!");
        }

        // فروشگاه برای امروز تعداد کافی برای فروش نداشته
        if (targetShop.getAvailableCountForToday(targetItem) <= count) {
            return new Result(false, "Shop's stock is empty for today! Come back tomorrow.");
        }

        // اینونتوری اش جا نداشته باشد
        if (App.getActiveGame().getCurrentPlayer().getInventory().isFull()) {
            return new Result(false, "Oops! Your backpack is completely full!");
        }

        // با موفقیت خرید کند و به اینونتوریش اضافه شه
        App.getActiveGame().getCurrentPlayer().getInventory().addItem(targetItem, count);
        int price = 0;
        if (targetItem instanceof Seed) {
            if (! ((Seed) targetItem).getAvailableSeasons().contains(TimeDate.getInstance(App.getActiveGame()).getSeason())) {
                price = ((Seed) targetItem).getOutOfSeasonPrice();
            }
        }
        price = targetItem.getPrice();
        App.getActiveGame().getCurrentPlayer().decreaseCoin(price);
        targetShop.addBalance(targetItem.getPrice());
        return new Result(true, "Purchase complete! Enjoy your new item");

    }

    public Result upgradeTool(Matcher matcher) {
        String toolName = matcher.group("toolName");
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        // خطای مراجعخ در زمان نامناسب
        if (!App.getActiveGame().getBlacksmith().isOpen()) {
            return new Result(false, "Sorry! we're closed! Shop hours: 9 AM to 4 PM");
        }

        // خطای نداشتن این ابزار
        if (currentPlayer.getInventory().findToolByName(toolName) == null) {
            return new Result(false, "You want me to upgrade... " +
                    "what exactly? You don't even have this tool!");
        }

        Gadget currentTool = currentPlayer.getInventory().findToolByName(toolName);
        String currentMaterial = currentTool.getMaterial();

        // خطای اینکه این ابزار ارتفا پذیر نیست
        if (currentMaterial == null) {
            return new Result(false, "This tool cannot be upgraded");
        }

        // خطای اینکه ابزار تا بالاترین مرحله ارتقا یافته
        String nextMaterial = getNextMaterial(currentMaterial);
        if (nextMaterial == null) {
            return new Result(false, "Your " + toolName + " is already at the highest upgrade level!");
        }


        int upgradeCost = ToolDataManager.getUpgradeCost(toolName, currentMaterial, nextMaterial);
        // خطای پول کافی نداشتن
        if (currentPlayer.getCoin() < upgradeCost) {
            return new Result(false, "Heh. Looks like your wallet’s " +
                    "as rusty as this tool. Bring more gold next time!");
        }

        UpgradeType upgradeType;
        try {
            upgradeType = UpgradeType.valueOf(nextMaterial.toUpperCase() + "_TOOL");
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid upgrade type for " + toolName);
        }

        // خطای اینکه امروز یکبار انجام شده
        if (!App.getActiveGame().getBlacksmith().cabUpgradeToday(upgradeType)) {
            return new Result(false, "My anvil needs a break! " +
                    "One upgrade a day keeps the warranty valid ^ ^");
        }

        // اپگرید کند
        App.getActiveGame().getBlacksmith().increaseBalance(upgradeCost);
        currentPlayer.decreaseCoin(upgradeCost);
        currentTool.setMaterial(getNextMaterial(currentMaterial));
        return new Result(true, "Upgrade complete! Your new material is : " + currentTool.getMaterial());

    }

    private String getNextMaterial(String currentMaterial) {
        if (currentMaterial.equalsIgnoreCase("base")) {
            return "Copper";
        }

        if (currentMaterial.equalsIgnoreCase("Copper")) {
            return "Iron";
        }

        if (currentMaterial.equalsIgnoreCase("Iron")) {
            return "Gold";
        }

        if (currentMaterial.equalsIgnoreCase("Gold")) {
            return "Iridium";
        }

        if (currentMaterial.equalsIgnoreCase("Iridium")) {
            return null;
        }
        else return null;
    }


}
