package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.tools.boardcreator.BoardCreator;

public class PreMap extends Actor {
    private static final TextureRegion[][] tiles;
    public int[][] map;
    public User user;

    static {
        tiles = TextureRegion.split(new Texture("tile.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
    }

    public PreMap(User user) {
        this.user = user;
        createNewMap();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int layer = 0; layer < getLayers(); layer++) {
            for (int row = 0; row < getHeightOfMap(); row++) {
                for (int col = 0; col < getWidthOfMap(); col++) {
                    TileType type = this.getTileTypeByCoordinate(layer, col, row);
                    if (type != null && type.getId() != 4)
                        batch.draw(tiles[0][type.getId() - 1], col * TileType.TILE_SIZE + 40, row * TileType.TILE_SIZE + 150);
                }
            }
        }
        super.draw(batch, parentAlpha);
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

    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        if (col < 0 || col >= getWidthOfMap() || row < 0 || row >= getHeightOfMap())
            return null;

        return TileType.getTileTypeById(map[row][col]);
    }

    public void createNewMap() {
        map = BoardCreator.createMaze();
    }

    public void saveMap() {
        if (user!=null && !user.mazes.contains(map))
            user.mazes.add(map);
    }
}
