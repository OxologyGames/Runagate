package com.oxology.world;

public class Level {
    private Tile[][] tiles;

    public Level() {
        tiles = new Tile[40][30];
    }

    public Level(Tile[][] tiles) {
        this.tiles = tiles;
    }
}
