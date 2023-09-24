package com.oxology.world;

import java.io.Serializable;

public class GameObject implements Serializable {
    protected float x, y;
    private ObjectType objectType;

    public enum ObjectType implements Serializable {
        CHAIN,
        ENTITY,
    }

    public GameObject(float x, float y, ObjectType objectType) {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public ObjectType getObjectType() {
        return objectType;
    }
}
