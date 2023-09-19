package com.oxology.world;

import java.io.Serializable;

public class GameObject implements Serializable {
    private int x, y;
    private ObjectType objectType;

    public enum ObjectType implements Serializable {
        CHAIN,
    }

    public GameObject(int x, int y, ObjectType objectType) {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ObjectType getObjectType() {
        return objectType;
    }
}
