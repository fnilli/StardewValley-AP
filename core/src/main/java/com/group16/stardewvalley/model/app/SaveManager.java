package com.group16.stardewvalley.model.app;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class SaveManager {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void save(GameData data, String filename) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), data);
            System.out.println("Game saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

