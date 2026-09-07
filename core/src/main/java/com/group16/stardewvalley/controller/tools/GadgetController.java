package com.group16.stardewvalley.controller.tools;

import com.group16.stardewvalley.model.app.*;
import com.group16.stardewvalley.model.user.*;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.map.*;
import com.group16.stardewvalley.model.tools.*;

import  java.util.Map;
import java.util.regex.Matcher;

import static com.group16.stardewvalley.model.map.Direction.getDirectionOffset;

public class GadgetController {

    public Result equip(Matcher matcher) {
        String toolName = matcher.group("toolName");
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();

        if (currentPlayer.getInventory().findToolByName(toolName) == null) {
            return new Result(false, "Oops! You don't have this tool (~_^) ");
        }

        Gadget toolToEquip = App.getActiveGame().getCurrentPlayer().getInventory().findToolByName(toolName);
        currentPlayer.equip(toolToEquip);

        return new Result(true, "Now you have " + toolName + " equipped!");
    }

    public Result showCurrentTool() {
        Gadget targetTool = App.getActiveGame().getCurrentPlayer().getCurrentEquipment();
        if (targetTool == null) {
            return new Result(false, "you don't have any gadget in your hand (~_^)");
        }
        return new Result(true, targetTool.getName());
    }

    public Result showAvailableTools() {
        Map<Gadget, Integer> tools = App.getActiveGame().getCurrentPlayer().getInventory().getTools();

        if (tools.isEmpty()) {
            return new Result(false, "Your inventory is empty! (•_•)");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Available tools:\n");

        for (Map.Entry<Gadget, Integer> entry : tools.entrySet()) {
            Gadget tool = entry.getKey();
            int count = entry.getValue();
            sb.append("- ").append(tool.getName()).append("\n");
        }

        return new Result(true, sb.toString());
    }

    public Result useTool(Matcher matcher) {
        String direction = matcher.group("direction");
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        Gadget gadget = currentPlayer.getCurrentEquipment();
        if (gadget == null) {
            return new Result(false, "You don't have any gadget in your hand (~_^)");
        }
        Direction dir = Direction.fromString(direction);
        if (dir == null) {
            return new Result(false, "invalid direction");
        }
        Tile targetTile = dir.applyPosition(App.getActiveGame());

        if (targetTile == null) {
            return new Result(false, "Out of bound");
        }

        return gadget.use(targetTile, App.getActiveGame());
    }

    // یا هیچی روی اون موقعیت سازگار نیست یا یکی از این کارهای ابزاره هست
    //TODO mirshekar
    /*
    private ToolAction whichOneIsSuitable() {

    }
     */

    private boolean hasEnoughEnergy(Gadget gadget, ToolAction action) {
        Player currentPlayer = App.getActiveGame().getCurrentPlayer();
        int requiredEnergy = ToolDataManager.getEnergyConsumption(gadget.getClass().getSimpleName()
                .toLowerCase(), gadget.getMaterial());
        return requiredEnergy > currentPlayer.getEnergy();

    }

    //

    public Result fishing(String name) {
        FishingPole fishingPole = App.getActiveGame().getCurrentPlayer().getInventory().getFishingPole(name);
        if (fishingPole == null) {
            return new Result(false, "You don't have this pole in your inventory");
        }
        Tile targetTile = getFirstLakeNearby(App.getActiveGame().getMap(), App.getActiveGame().getCurrentPlayer().getPosition());
        return fishingPole.use(targetTile, App.getActiveGame());
    }

    public Tile getFirstLakeNearby(Tile[][] map, Pos position) {
        int rows = map.length;
        int cols = map[0].length;

        int[] dx = {-1, -1, -1,  0, 0, 1, 1, 1};
        int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newX = position.getX() + dx[i];
            int newY = position.getY() + dy[i];

            if (newX >= 0 && newX < cols && newY >= 0 && newY < rows) {
                Tile neighbor = map[newY][newX];
                if (neighbor.getType() == TileType.Lake) {
                    return neighbor; // اولین تایل با نوع دریاچه
                }
            }
        }

        return null; // هیچ دریاچه‌ای در اطراف نیست
    }



}
