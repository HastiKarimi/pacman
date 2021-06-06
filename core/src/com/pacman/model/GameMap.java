package com.pacman.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.screens.game.GameScreen;
import jdk.javadoc.internal.doclets.formats.html.markup.Table;

import java.util.HashMap;

public class GameMap extends Actor {
    public int spaceX = 40; //2.5 * 16
    public int spaceY = 40;
    public GameScreen gameScreen;
    public String id;
    public String name;
    public int[][] map;

    public Pacman pacman;
    public Ghost[] ghosts;
    public boolean isGamePaused = false;


    private final TextureRegion[][] tiles;

    public GameMap(GameScreen gameScreen, int[][] map) {
        this.gameScreen = gameScreen;
        this.map = map;
        ghosts = new Ghost[4];
        tiles = TextureRegion.split(new Texture("tile.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
        createFramesForOtherAnimation();
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
        int newX = (int) ((x - spaceX) / TileType.TILE_SIZE);
        int newY = (int) ((y - spaceY) / TileType.TILE_SIZE);

        return this.getTileTypeByCoordinate(layer, newX, newY);
    }

    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        if (col < 0 || col >= getWidthOfMap() || row < 0 || row >= getHeightOfMap())
            return null;

        return TileType.getTileTypeById(map[row][col]);
    }

    public void eatTileTypeContent(int layer, float x, float y) {
        int newX = (int) ((x - spaceX) / TileType.TILE_SIZE);
        int newY = (int) ((y - spaceY) / TileType.TILE_SIZE);

        map[newY][newX] = TileType.NONE.getId();
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
        if (!isGamePaused) {
            pacman.update(delta);
            for (Ghost ghost : ghosts) {
                ghost.update(delta);
            }

            for (Ghost ghost : ghosts) {
                if (pacman.shape.overlaps(ghost.shape) && ghost.isAvailable) {
                    pacman.overlapWithGhost(ghost);
                    break;
                }
            }
        } else
            processMovingKeys();
    }

    public void processMovingKeys() {
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT) ||
                Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.UP)) {
            isGamePaused = false;
        }
    }


    public void createFramesForOtherAnimation() {
        HashMap<Move, TextureRegion[]> frames = Ghost.otherFrames;
        Texture energyGhostSheet = new Texture(Gdx.files.internal("energy-ghost.png"));
        TextureRegion[][] otherTmp = TextureRegion.split(energyGhostSheet,
                TileType.TILE_SIZE, TileType.TILE_SIZE);

        int j = 0;
        for (Move move : new Move[]{Move.DOWN, Move.LEFT, Move.RIGHT, Move.UP}) {
            TextureRegion[] textureRegion = new TextureRegion[6];
            for (int i = 0; i < 6; i++) {
                textureRegion[i] = otherTmp[j][i];
            }
            frames.put(move, textureRegion);
            j++;
        }
    }


}
