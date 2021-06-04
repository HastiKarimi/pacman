package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.screens.game.GameScreen;
import com.pacman.tools.timer.ExampleTimer;

public class GameMap extends Actor {
    public int spaceX = 40; //2.5 * 16
    public int spaceY = 40;
    public GameScreen gameScreen;
    public String id;
    public String name;
    public int[][] map;

    public Pacman pacman;
    public Ghost[] ghosts;
    public ExampleTimer gameTimer;


    private TextureRegion[][] tiles;

    public GameMap(GameScreen gameScreen, int[][] map) {
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
                        batch.draw(tiles[0][type.getId() - 1], row * TileType.TILE_SIZE + spaceX, col * TileType.TILE_SIZE + spaceY);
                }
            }
        }
        super.draw(batch, parentAlpha);
    }


    public TileType getTileTypeByLocation(int layer, float x, float y) {
        int newX = (int) ((x - spaceX) / TileType.TILE_SIZE);
        int newY = (int) ((y - spaceY) / TileType.TILE_SIZE);
//
//        newX -= spaceX;
//        newY -= spaceY;
//
//        newX = newX / TileType.TILE_SIZE;
//        newY = newY / TileType.TILE_SIZE;

//        newX *= TileType.TILE_SIZE;
//        newY *= TileType.TILE_SIZE;
//        newX += spaceX;
//        newY += spaceY;
        return this.getTileTypeByCoordinate(layer, newX, newY);
    }

    public TileType getTileTypeByCoordinate(int layer, int row, int col) {
        if (col < 0 || col >= getHeightOfMap() || row < 0 || row >= getWidthOfMap())
            return null;
        System.out.println(row + " " + col);
        return TileType.getTileTypeById(map[row][col]);
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

    public void update(float delta) {
        pacman.update(delta);
    }
}
