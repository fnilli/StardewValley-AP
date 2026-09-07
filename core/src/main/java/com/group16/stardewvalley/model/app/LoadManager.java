package com.group16.stardewvalley.model.app;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class LoadManager {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static GameData load(String filename) {
        try {
            return mapper.readValue(new File(filename), GameData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


//// Saving
//GameData data = new GameData(currentUser, currentPlayer, currentGame);
//SaveManager.save(data, "savefile.json");
//
//// Loading
//GameData loaded = LoadManager.load("savefile.json");
//if (loaded != null) {
//        System.out.println("Welcome back, " + loaded.user.getUsername());
//        }
