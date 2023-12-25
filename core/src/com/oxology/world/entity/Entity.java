package com.oxology.world.entity;

import com.oxology.world.GameObject;

public class Entity extends GameObject {
    private static final long serialVersionUID = -3038644230555076026L;

    protected static final int NONE = 0;
    protected static final int LEFT = 1;
    protected static final int RIGHT = 2;

    protected static final float ACCELERATION_SPEED = 5.0f;
    protected static final float ACCELERATION_AIR_SPEED = 2.0f;
    protected static final float DECELERATION_SPEED = 0.2f;
    protected static final float DECELERATION_AIR_SPEED = 0.01f;

    protected static final float JUMP_SPEED = 8.0f;
    protected static final float CHAIN_SPEED = 5.0f;

    final float gravity = 20.0f;
    protected float xSpeed, ySpeed;
    protected boolean[] colliders;
    protected boolean onChain;
    private boolean jumpedOnChain;
    protected boolean jumpedOffChain;

    public Entity(float x, float y) {
        super(x, y, ObjectType.ENTITY);

        colliders = new boolean[4];
        onChain = false;
        jumpedOnChain = true;
        jumpedOffChain = false;
    }

    public void update(float deltaTime) {
        if(jumpedOffChain) {
            onChain = false;
            jumpedOnChain = true;
        }

        if(onChain && jumpedOnChain) {
            jumpedOnChain = false;

            xSpeed = 0;
            ySpeed = 0;
        }

        x += xSpeed*deltaTime;
        y += ySpeed*deltaTime;

        if((!colliders[0] && !onChain) || ySpeed > 0) {
            ySpeed -= gravity*deltaTime;
        }

        if(colliders[0] && ySpeed < 0) {
            ySpeed = 0;
            y = Math.round(y);
        }
    }

    public boolean[] getColliders() {
        return colliders;
    }

    public void setCollider(boolean value, int index) {
        this.colliders[index] = value;
    }

    public void onChain(boolean isOnChain) {
        this.onChain = isOnChain;

        if(!isOnChain) {
            jumpedOnChain = true;
            jumpedOffChain = false;
        } else if(!jumpedOffChain) {
            x = (float) Math.floor(x)+0.5f;
        }
    }
}
