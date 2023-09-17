package com.oxology.world;

public class Level {
    private Tile[][] tiles;
    private int x, y;

    public Level(int x, int y) {
        tiles = new Tile[40][30];
        this.x = x;
        this.y = y;
    }

    public Level(Tile[][] tiles) {
        this.tiles = tiles;
    }
}
