package com.group16.stardewvalley.controller.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Mineral;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.graphics.PlaceTextureManager;
import com.group16.stardewvalley.model.graphics.TileRenderer;
import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.map.*;
import com.group16.stardewvalley.model.shops.Shop;
import com.group16.stardewvalley.model.user.Player;
import com.group16.stardewvalley.view.graphics.GameScreen;

import java.util.*;

import static com.group16.stardewvalley.view.graphics.GameScreen.TILE_SIZE;

public class MapController {

    public void createMap() {
        Game game = App.getActiveGame();
        int height = game.getMapHeight();
        int width = game.getMapWidth();
        Tile[][] map = new Tile[height][width];

        for (int i = 0; i < height; i++) {
            int flippedY = height - 1 - i;
            for (int j = 0; j < width; j++) {
                map[flippedY][j] = new Tile(TileType.Ground);
            }
        }

        // مشخص کردن نقطه شروع مزرعه
        Pos[] positions = {
            new Pos(5, 5),
            new Pos(width - 80, 5),
            new Pos(5, height - 70),
            new Pos(width - 80, height - 70)
        };

        int index = 0;
        for (Player player : game.getPlayers()) {
            player.getFarm().setStartPosition(positions[index]);
            int x, y;
            Random r = new Random();
            do {
                x = r.nextInt(player.getFarm().getType().getWidth());
                y = r.nextInt(player.getFarm().getType().getHeight());
            } while (player.getFarm().getType().getTiles()[y][x] != TileType.Cottage);

            // y رو برعکس کن چون map برعکس شده
            int flippedY = height - 1 - (player.getFarm().getStartPosition().getY() + y);
            player.setPosition(new Pos((player.getFarm().getStartPosition().getX() + x), flippedY));
            index++;
        }

        for (Player player : game.getPlayers()) {
            for (int i = 0; i < player.getFarm().getType().getHeight() - 1; i++) {
                int flippedY = height - 1 - (i + player.getFarm().getStartPosition().getY());
                for (int j = 0; j < player.getFarm().getType().getWidth() - 1; j++) {
                    map[flippedY][j + player.getFarm().getStartPosition().getX()] =
                        new Tile(player.getFarm().getType().getTiles()[i][j]);
                    map[flippedY][j + player.getFarm().getStartPosition().getX()].setLocation(Location.Farm);
                }
            }
        }

        for (Shop shop : game.getShops()) {
            PlaceType placeType = shop.getPlaceType();
            for (int i = 0; i < placeType.getHeight() - 1; i++) {
                int flippedY = height - 1 - (i + placeType.getStartPosition().getY());
                for (int j = 0; j < placeType.getWidth() - 1; j++) {
                    map[flippedY][j + placeType.getStartPosition().getX()] =
                        new Tile(placeType.getTiles()[i][j]);
                    map[flippedY][j + placeType.getStartPosition().getX()].setLocation(getLocationByName(shop.getShopName()));
                }
            }
        }

        for (NPC npc : game.getNPCs()) {
            PlaceType placeType = npc.getNpcType().getPlaceType();
            for (int i = 0; i < placeType.getHeight() - 1; i++) {
                int flippedY = height - 1 - (i + placeType.getStartPosition().getY());
                for (int j = 0; j < placeType.getWidth() - 1; j++) {
                    map[flippedY][j + placeType.getStartPosition().getX()] =
                        new Tile(placeType.getTiles()[i][j]);
                    map[flippedY][j + placeType.getStartPosition().getX()].setLocation(Location.NPCFarm);
                }
            }
        }

        game.setMap(map);
    }

    private Location getLocationByName(String name) {
        return switch (name) {
            case "Blacksmith" -> Location.Blacksmith;
            case "Carpenter's Shop" -> Location.CarpentersShop;
            case "Fish Shop" -> Location.FishShop;
            case "JojaMart" -> Location.JojaMart;
            case "Marnie's Ranch" -> Location.MarniesRanch;
            case "Pierr's General Store" -> Location.PierresGeneralStore;
            case "The Stardrop Saloon" -> Location.TheStardropSaloon;
            default -> null;
        };
    }

    public Result askWalking(int x, int y) {
        Pos dest = new Pos(x, y);
        Player player = App.getActiveGame().getCurrentPlayer();

        if (isInOtherPlayersFarm(dest, player, App.getActiveGame().getPlayers())) {
            return new Result(false, "Destination is inside another player's farm.");
        }

        PathInfo pathInfo = calculatePathInfo(player.getPosition(), dest);
        if (!pathInfo.isValid()) {
            return new Result(false, pathInfo.message());
        }

        return new Result(true, "It takes you " + pathInfo.energyCost() + " energy. Do you want to go there?");
    }

    public Result walk(int x, int y) {
        Pos dest = new Pos(x, y);
        Player player = App.getActiveGame().getCurrentPlayer();


        PathInfo pathInfo = calculatePathInfo(player.getPosition(), dest);
        if (!pathInfo.isValid()) {
            return new Result(false, pathInfo.message());
        }
        if (player.getEnergy() < pathInfo.energyCost()) {
            player.faint();
            player.setPosition(dest);
            return new Result(false, "Moved to <" + dest.getX() + "," + dest.getY() + "> but You fainted");
        }
        player.setPosition(dest);
        player.decreaseEnergy(pathInfo.energyCost());
        return new Result(true, "Moved to <" + dest.getX() + "," + dest.getY() + ">");
    }

    private boolean isValidPos(Pos pos, int width, int height) {
        int x = pos.getX(), y = pos.getY();
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private PathInfo calculatePathInfo(Pos start, Pos dest) {
        Tile[][] map = App.getActiveGame().getMap();
        int height = map.length;
        int width = map[0].length;

        if (!isValidPos(dest, width, height) || (isValidPos(dest, width, height) && !map[dest.getY()][dest.getX()].isTileEmpty())) {
            return PathInfo.invalid("Invalid destination.");
        }

        List<Pos> path = findShortestPath(map, start, dest);
        if (path == null) {
            return PathInfo.invalid("No path exists.");
        }

        int energyCost = 0;
        Pos prev = start;
        Direction lastDirection = null;

        for (int i = 1; i < path.size(); i++) {
            Pos curr = path.get(i);
            Tile tile = map[curr.getY()][curr.getX()];

            // Base cost per step
            int stepCost = 1;

            // Additional cost based on tile type
            switch (tile.getType()) {
                case Plowed -> stepCost += 2;
                case Quarry -> stepCost += 1;
                case  GreenHouse -> stepCost -= 1;
                default -> {} // no change for Ground or others
            }

            // Add extra cost for direction change
            Direction currentDirection = getDirection(prev, curr);
            if (lastDirection != null && currentDirection != lastDirection) {
                stepCost += 1; // turning costs more energy
            }

            energyCost += stepCost;
            lastDirection = currentDirection;
            prev = curr;
        }
        energyCost /= 20;

        return PathInfo.valid(path, energyCost);
    }

    private Direction getDirection(Pos from, Pos to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        if (dx == 1) return Direction.RIGHT;
        if (dx == -1) return Direction.LEFT;
        if (dy == 1) return Direction.DOWN;
        if (dy == -1) return Direction.UP;
        return null;
    }

    private List<Pos> findShortestPath(Tile[][] map, Pos start, Pos dest) {
        int height = map.length;
        int width = map[0].length;

        boolean[][] visited = new boolean[height][width];
        Pos[][] parent = new Pos[height][width];
        Queue<Pos> queue = new LinkedList<>();
        queue.add(start);
        visited[start.getY()][start.getX()] = true;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Pos curr = queue.poll();
            if (curr.isEqual(dest)) {
                return reconstructPath(parent, dest, start);
            }

            for (int i = 0; i < 4; i++) {
                int nx = curr.getX() + dx[i];
                int ny = curr.getY() + dy[i];
                Pos next = new Pos(nx, ny);

                if (isValidPos(next, width, height)
                        && !visited[ny][nx]
                        && map[ny][nx].isTileEmpty()) {
                    visited[ny][nx] = true;
                    parent[ny][nx] = curr;
                    queue.add(next);
                }
            }
        }

        return null;
    }


    private List<Pos> reconstructPath(Pos[][] parent, Pos end, Pos start) {
        List<Pos> path = new ArrayList<>();
        for (Pos at = end; at != null && !at.equals(start); at = parent[at.getY()][at.getX()]) {
            path.add(at);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }


    private boolean isInOtherPlayersFarm(Pos dest, Player currentPlayer, List<Player> players) {
        for (Player player : players) {
            if (player == currentPlayer) continue;
            Pos start = player.getFarm().getStartPosition();
            int farmX = start.getX();
            int farmY = start.getY();
            int farmWidth = player.getFarm().getType().getWidth();
            int farmHeight = player.getFarm().getType().getHeight();

            if (dest.getX() >= farmX && dest.getX() < farmX + farmWidth &&
                    dest.getY() >= farmY && dest.getY() < farmY + farmHeight) {
                return true;
            }
        }
        return false;
    }

    public Result printMap(int x, int y, int size) {
        Tile[][] map = App.getActiveGame().getMap();
        int height = map.length;
        int width = map[0].length;

        StringBuilder builder = new StringBuilder();

        for (int i = y; i < Math.min(y + size, height); i++) {
            for (int j = x; j < Math.min(x + size, width); j++) {
                if (i < 0 || j < 0 || i >= height || j >= width) {
                    continue;
                }

                boolean playerDrawn = false;
                for (Player player : App.getActiveGame().getPlayers()) {
                    if (player.getPosition().getY() == i && player.getPosition().getX() == j) {
                        if (player.isFainted()) {
                            builder.append("\u001B[31mx\u001B[0m");
                        } else {
                            builder.append("\u001B[31m@\u001B[0m");
                        }
                        playerDrawn = true;
                        break; // چون پلیر پیدا شد، بقیه پلیرها مهم نیستن
                    }
                }

                if (playerDrawn) continue; // اگر پلیر چاپ شد، بقیه‌ی شرط‌ها رو چک نکن

                if (map[i][j].isBurned()) {
                    builder.append("B");
                    continue;
                }

                if (map[i][j].getLocation().equals(Location.Farm)){
                    if (map[i][j].getTree() != null) {
                        builder.append(TileType.Tree.getColorCode()).append(TileType.Tree.getSymbol()).append("\033[0m");
                    } else if (map[i][j].getCrop() != null) {
                        builder.append(TileType.Forage.getColorCode()).append(TileType.Forage.getSymbol()).append("\033[0m");
                    } else if (map[i][j].getItem() != null && map[i][j].getItem() instanceof Stone) {
                        builder.append(TileType.Stone.getColorCode()).append(TileType.Stone.getSymbol()).append("\033[0m");
                    } else if (map[i][j].getItem() != null && map[i][j].getItem() instanceof Mineral) {
                        builder.append(TileType.MineralForage.getColorCode()).append(TileType.MineralForage.getSymbol()).append("\033[0m");
                    } else{
                        builder.append(map[i][j].getType().getColorCode()).append(map[i][j].getType().getSymbol()).append("\033[0m");
                    }
                }
                else if (map[i][j].getLocation().equals(Location.Game)) {
                    builder.append(map[i][j].getType().getSymbol());
                }
                else {
                    builder.append(map[i][j].getType().getColorCode()).append(map[i][j].getType().getSymbol()).append("\033[0m");
                }

            }
            builder.append("\n");
        }

        return new Result(true, builder.toString());
    }

    public Result helpReadingMap() {
        StringBuilder builder = new StringBuilder();
        for (TileType tileType : TileType.values()) {
            builder.append(tileType.name()).append("  :  ").append(tileType.getSymbol()).append("\n");
        }
        return new Result(true, builder.toString());
    }

    public static boolean isPlayerInFarm(Player currentPlayer) {
        Pos start = currentPlayer.getFarm().getStartPosition();
        Pos playerPos = currentPlayer.getPosition();
        return playerPos.getX() > start.getX() &&
                playerPos.getX() > start.getX() + currentPlayer.getFarm().getType().getWidth() &&
                playerPos.getY() > start.getY() &&
                playerPos.getY() > start.getY() + currentPlayer.getFarm().getType().getHeight();
    }

    public static boolean isPlayerInCottage(Player currentPlayer) {
        Pos pos = currentPlayer.getPosition();
        return App.getActiveGame().getMap()[pos.getY()][pos.getX()].getType() == TileType.Cottage;
    }

    public void drawMap(SpriteBatch batch, float delta) {
        Tile[][] map = App.getActiveGame().getMap();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Tile tile = map[y][x];
                Texture texture;
                    texture = TileTextureManager.getTileTextureManager().getTexture(tile.getType());
                if (tile.isHasWater()) { //TODO یه عکس واسش درست کن چون زشته
                    batch.setColor(0f, 0f, 1f, 0.2f);
                }
                if (tile.isFertilized()) {
                    texture = GameAssetManager.getGameAssetManager().getFertalizeTexture();
                }
                batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                batch.setColor(Color.WHITE);

            }
        }

        for (int y = map.length - 1; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                TileRenderer.getTileRenderer().renderTile(batch, map[y][x], x, y);
            }
        }

//        for (PlaceType placeType : PlaceType.values()) {
//            TextureRegion texture = PlaceTextureManager.getPlaceTextureManager().getShopTexture(placeType);
//            batch.draw(texture, placeType.getStartPosition().getX() * TILE_SIZE, (App.getActiveGame().getMapHeight() - placeType.getStartPosition().getY() )* TILE_SIZE);
//        }
        for (PlaceType placeType : PlaceType.values()) {
            TextureRegion texture = PlaceTextureManager.getPlaceTextureManager().getShopTexture(placeType);

            float x = placeType.getStartPosition().getX() * TILE_SIZE;
            float y = (App.getActiveGame().getMapHeight() - placeType.getStartPosition().getY()) * TILE_SIZE;

            if (placeType.equals(PlaceType.CarpentersShop) || placeType.equals(PlaceType.MarniesRanch)) {
                batch.draw(
                    texture,
                    x ,
                    y ,
                    texture.getRegionWidth() * 1f,
                    texture.getRegionHeight() * 1f
                );
            } else {
                batch.draw(texture, x, y);
            }
        }

    }


    public static boolean isPlayerInsidePlace(Player player, PlaceType placeType) {
        Pos start = placeType.getStartPosition();
        int width = placeType.getWidth();
        int height = placeType.getHeight();

        int mapHeight = App.getActiveGame().getMapHeight();

        int placeX1 = start.getX();
        int placeX2 = start.getX() + width - 1;

        // invert Y-coordinates to match top-left origin
        int placeY2 = mapHeight - start.getY();  // top edge
        int placeY1 = placeY2 - height + 1;      // bottom edge

        return player.getX() >= placeX1 && player.getX() <= placeX2
            && player.getY() >= placeY1 && player.getY() <= placeY2;
    }



}
