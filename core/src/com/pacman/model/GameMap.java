package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.screens.game.GameScreen;

public class GameMap extends Actor {
    int spaceX = 40; //2.5 * 16
    int spaceY = 40;
    GameScreen gameScreen;
    String id;
    String name;
    int[][] map;

    private TextureRegion[][] tiles;

    public GameMap(GameScreen gameScreen, int[][] map) {
//        this.id = data.id;
//        this.name = data.name;
        this.gameScreen = gameScreen;
        this.map = map;

        tiles = TextureRegion.split(new Texture("tile.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int layer = 0; layer < getLayers(); layer++) {
            for (int row = 0; row < getHeightOfMap(); row++) {
                for (int col = 0; col < getWidthOfMap(); col++) {
                    TileType type = this.getTileTypeByCoordinate(layer, col, row);
                    if (type != null && type.getId() != 4)
                        batch.draw(tiles[0][type.getId() - 1], col * TileType.TILE_SIZE + spaceX, row * TileType.TILE_SIZE + spaceY);
                }
            }
        }
        super.draw(batch, parentAlpha);
    }


    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), getHeightOfMap() - (int) (y / TileType.TILE_SIZE) - 1);
    }

    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        if (col < 0 || col >= getWidthOfMap() || row < 0 || row >= getHeightOfMap())
            return null;

        return TileType.getTileTypeById(map[getHeightOfMap() - row - 1][col]);
    }

    public int getWidthOfMap() {
        return map[0].length;
    }

    public int getHeightOfMap() {
        return map.length;
    }

    public int getLayers() {
        return 1;
    }
}
