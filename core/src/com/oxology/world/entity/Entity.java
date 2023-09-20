package com.oxology.world.entity;

import com.oxology.world.GameObject;

public class Entity extends GameObject {
    public Entity(int x, int y) {
        super(x, y, ObjectType.ENTITY);
    }
}
