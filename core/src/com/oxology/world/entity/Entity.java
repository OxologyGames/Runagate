package com.oxology.world.entity;

import com.oxology.world.GameObject;

public class Entity extends GameObject {
    final float gravity = 8f;
    protected float xSpeed, ySpeed;
    protected boolean[] colliders;

    public Entity(float x, float y) {
        super(x, y, ObjectType.ENTITY);

        colliders = new boolean[4];
    }

    public void update(float deltaTime) {
        x += xSpeed*deltaTime;
        y += ySpeed*deltaTime;

        if(!colliders[0])
            ySpeed -= gravity*deltaTime;
    }

    public boolean[] getColliders() {
        return colliders;
    }

    public void setCollider(boolean value, int index) {
        this.colliders[index] = value;
    }
}
