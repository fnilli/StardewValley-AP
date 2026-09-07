package com.group16.stardewvalley.model.map;

import com.group16.stardewvalley.model.*;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.*;

public enum Direction {
    UP("up", 0, 1),
    DOWN("down", 0, -1),
    LEFT("left", -1, 0),
    RIGHT("right", 1, 0),
    N("n", 0, -1),
    NE("ne", 1, -1),
    E("e", 1, 0),
    SE("se", 1, 1),
    S("s", 0, 1),
    SW("sw", -1, 1),
    W("w", -1, 0),
    NW("nw", -1, -1);

    private final String input;
    private final int xDelta;
    private final int yDelta;

    Direction(String input, int xDelta, int yDelta) {
        this.input = input;
        this.xDelta = xDelta;
        this.yDelta = yDelta;
    }

    public int getxDelta() {
        return xDelta;
    }

    public int getyDelta() {
        return yDelta;
    }

    public static Direction fromString(String input) {
        String normalized = input.toLowerCase().trim();
        for (Direction dir : values()) {
            if (dir.input.equalsIgnoreCase(normalized)) {
                return dir;
            }
        }
        return null;
    }

    public Tile applyPosition(Game game) {
        int x =game.getCurrentPlayer().getX() + xDelta;
        int y = game.getCurrentPlayer().getY() + yDelta;
        if (game.getMap() != null && isValidPos(new Pos(x, y), game.getMapWidth(), game.getMapHeight())) {
            return game.getMap()[y][x];
        }
    return null;
    }


    public Pos applyToPosition(Pos currentPos) {
        return new Pos(currentPos.getX() + xDelta, currentPos.getY() + yDelta);
    }

    private boolean isValidPos(Pos pos, int width, int height) {
        int x = pos.getX(), y = pos.getY();
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public static Pos getDirectionOffset(String direction) {
        return switch (direction.toLowerCase()) {
            case "up" -> new Pos(0, -1);
            case "down" -> new Pos(0, 1);
            case "left" -> new Pos(-1, 0);
            case "right" -> new Pos(1, 0);
            case "up left" -> new Pos(-1, -1);
            case "up right" -> new Pos(1, -1);
            case "down left" -> new Pos(-1, 1);
            case "down right" -> new Pos(1, 1);
            default -> null;
        };
    }
}
