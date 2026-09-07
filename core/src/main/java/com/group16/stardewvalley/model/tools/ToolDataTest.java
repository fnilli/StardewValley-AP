package com.group16.stardewvalley.model.tools;

public class ToolDataTest {
    public static void main(String[] args) {
        System.out.println("Hoe (copper) energy: " +
                ToolDataManager.getEnergyConsumption("hoe", "copper")); // باید 4 برگرداند

        System.out.println("Scythe energy: " +
                ToolDataManager.getEnergyConsumption("scythe", "")); // باید 2 برگرداند
    }
}

// این برای تست کردن جیسونه صرفا بعدش پاک میشه