package com.group16.stardewvalley.model.tools;

public enum ToolAction {
    Plow("Plow"),
    BreakStone("BreakStone"),
    RemoveHoeMarks("RemoveHoeMarks"),
    ClearDroppedItems("ClearDroppedItems"),
    ChopTrees("ChopTrees"),
    ClearBranches("ClearBranches"),
    WaterCrops("WaterCrops"),
    FillWaterContainer("FillWaterContainer"),
    Fishing("Fishing"),
    CutGrass("CutGrass"),
    HarvestCrops("HarvestCrops"),
    MikAnimals("MilkAnimal"),
    ShearSheep("ShearSheep"),
    RemoveFromInventory("RemoveFromInventory");

    private final String actionName;

    ToolAction(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

    public static ToolAction fromString(String value) {
        for (ToolAction action : values()) {
            if (action.name().equalsIgnoreCase(value)) {
                return action;
            }
        }
        return null;
    }
}
