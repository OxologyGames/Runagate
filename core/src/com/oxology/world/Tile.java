package com.oxology.world;

import java.io.Serializable;

public enum Tile implements Serializable {
    AIR,
    WALL(0),
    CHAIN,
    PIPE;

    private int variant;

    Tile() {
        variant = -1;
    }

    Tile(int variant) {
        this.variant = variant;
    }

    public int getVariant() {
        return variant;
    }
}
