package com.oxology.world;

import java.util.List;

public class World {
    private List<Level> levels;

    public void addLevel(Level level) {
        levels.add(level);
    }
}
