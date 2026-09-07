package com.group16.stardewvalley.model.tools;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ToolDataManager {
    private static final String TOOLS_JSON_PATH = "/Tools/Tools.json";
    private static Map<String, Object> toolsData;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = ToolDataManager.class.getResourceAsStream(TOOLS_JSON_PATH);
            if (inputStream == null) {
                throw new RuntimeException("File not found in classpath: " + TOOLS_JSON_PATH);
            }
            toolsData = mapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tools data from JSON", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static int getEnergyConsumption(String toolName, String material) {
        try {
            Map<String, Object> tools = (Map<String, Object>) toolsData.get("tools");
            Map<String, Object> toolMap = (Map<String, Object>) tools.get(toolName.toLowerCase());

            if (toolMap == null) {
                throw new IllegalArgumentException("Tool not found: " + toolName);
            }

            // برای ابزارهایی مثل scythe که مصرف ثابت دارند
            if (toolMap.containsKey("consumption energy")) {
                return (int) toolMap.get("consumption energy");
            }

            // برای ابزارهای وابسته به متریال
            if (toolMap.containsKey("material")) {
                Map<String, Object> materials = (Map<String, Object>) toolMap.get("material");
                Map<String, Object> materialData = (Map<String, Object>) materials.get(material.toLowerCase());
                if (materialData.get("consumption energy") == null) {
                    return 0;
                }
                return (int) materialData.get("consumption energy");
            }
            return 0;
        } catch (Exception e) {
            throw new RuntimeException("Error getting energy consumption for " + toolName, e);
        }
    }

    public static Map<String, Object> getToolsData() {
        return toolsData;
    }

    public static int getUpgradeCost(String toolName, String currentMaterial, String targetMaterial) {
        try {
            Map<String, Object> tools = (Map<String, Object>) toolsData.get("tools");
            Map<String, Object> toolMap = (Map<String, Object>) tools.get(toolName.toLowerCase());

            if (toolMap == null) {
                return -1;
            }

            if (toolMap.containsKey("material")) {
                Map<String, Object> materials = (Map<String, Object>) toolMap.get("material");
                Map<String, Object> targetMaterialData = (Map<String, Object>) materials.get(targetMaterial
                        .toLowerCase());
                return (int) targetMaterialData.get("Upgrade Cost");
            }

            if (toolMap.containsKey("type")) {
                Map<String, Object> types = (Map<String, Object>) toolMap.get("type");
                Map<String, Object> targetTypeData = (Map<String, Object>) types.get(targetMaterial
                        .toLowerCase());
                return (int) targetTypeData.get("Upgrade Cost");
            }

            throw new IllegalArgumentException("Tool doesn't supports upgrades");
        } catch (Exception e) {
            throw new RuntimeException("Error getting upgrade cost for : " + toolName, e);
        }
    }

    public static int getToolPrice(String toolName, String material) {
        try {
            Map<String, Object> tools = (Map<String, Object>) toolsData.get("tools");
            Map<String, Object> toolMap = (Map<String, Object>) tools.get(toolName.toLowerCase());

            Map<String, Object> materials = (Map<String, Object>) toolMap.get("material");
            Map<String, Object> materialData = (Map<String, Object>) materials.get(material.toLowerCase());

            if (materialData == null) {
                materialData = (Map<String, Object>) materials.get("base");
            }

            return (int) materialData.get("Cost");
        } catch (Exception e) {
            throw new RuntimeException("Error getting price for " + toolName, e);
        }
    }

    public static int getWateringCanCapacity(String material) {
        try {
            Map<String, Object> tools = (Map<String, Object>) toolsData.get("tools");
            Map<String, Object> wateringCan = (Map<String, Object>) tools.get("watering can");
            Map<String, Object> materials = (Map<String, Object>) wateringCan.get("material");

            Map<String, Object> materialData = (Map<String, Object>) materials.get(material.toLowerCase());
            if (materialData == null) {
                throw new IllegalArgumentException("Material not found");
            }
            return (int) materialData.get("capacity");
        } catch (Exception e) {
            throw new RuntimeException("Error getting watering can capacity for " + material);
        }
    }

    public static int getWateringCanUpgradeCost(String currentMaterial) {
        try {
            Map<String, Object> tools = (Map<String, Object>) toolsData.get("tools");
            Map<String, Object> wateringCan = (Map<String, Object>) tools.get("watering can");
            Map<String, Object> materials = (Map<String, Object>) wateringCan.get("material");

            Map<String, Object> currentMaterialData = (Map<String, Object>) materials.get(currentMaterial.toLowerCase());
            if (currentMaterialData == null) {
                throw new IllegalArgumentException("Current material not found for watering can: " + currentMaterial);
            }

            if (!currentMaterialData.containsKey("Upgrade Cost")) {
                throw new IllegalArgumentException("No upgrade available for material: " + currentMaterial);
            }

            return (int) currentMaterialData.get("Upgrade Cost");
        } catch (Exception e) {
            throw new RuntimeException("Error getting watering can upgrade cost from " + currentMaterial, e);
        }
    }
}