package com.pacman.model;

import lombok.Getter;

import java.util.HashMap;

public enum TileType {
    WALL(1, true),
    FOOD(2, false),
    ENERGY_BOMB(3, false),
    NONE(4, false);

    public static int TILE_SIZE = 16;

    @Getter private final boolean isCollidable;
    @Getter private final int id;

    TileType(int id, boolean isCollidable) {
        this.id = id;
        this.isCollidable = isCollidable;
    }


    private static HashMap<Integer, TileType> tileMap;

    static {
        tileMap = new HashMap<Integer, TileType>();
        for (TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }

    public static TileType getTileTypeById (int id) {
        return tileMap.get(id);
    }
}
