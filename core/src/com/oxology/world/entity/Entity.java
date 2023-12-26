package com.oxology.world.entity;

import com.oxology.world.GameObject;

public class Entity extends GameObject {
    private static final long serialVersionUID = -3038644230555076026L;

    public static final float GRAVITY = 20.0f;

    protected static final int NONE = 0;
    protected static final int LEFT = 1;
    protected static final int RIGHT = 2;
    protected static final int UP = 3;
    protected static final int DOWN = 4;

    private static final float ACCELERATION_SPEED = 5.0f;
    private static final float ACCELERATION_AIR_SPEED = 2.0f;
    private static final float DECELERATION_SPEED = 0.2f;
    private static final float DECELERATION_AIR_SPEED = 0.01f;

    private static final float JUMP_SPEED = 8.0f;
    private static final float CHAIN_SPEED = 5.0f;

    private float xSpeed, ySpeed;
    protected boolean[] colliders;
    private boolean onChain;
    private boolean jumpedOnChain;
    private boolean jumpedOffChain;
    private int facing;

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
            ySpeed -= GRAVITY*deltaTime;
        }

        if(colliders[0] && ySpeed < 0) {
            ySpeed = 0;
            y = Math.round(y);
        }
    }

    protected void jump() {
        if(colliders[0] || onChain) {
            ySpeed = JUMP_SPEED;
            if(onChain) {
                jumpedOffChain = true;
                switch (facing) {
                    case LEFT:
                        xSpeed = -ACCELERATION_SPEED;
                        break;
                    case RIGHT:
                        xSpeed = ACCELERATION_SPEED;
                        break;
                }
            }
        }
    }

    protected void move(int facing) {
        float acceleration = ACCELERATION_SPEED;
        if(!colliders[0])
            acceleration = ACCELERATION_AIR_SPEED;

        if(facing == RIGHT) {
            if(xSpeed <= acceleration) {
                if (!onChain)
                    xSpeed = acceleration;
                this.facing = RIGHT;
            }
        } else if(facing == LEFT) {
            if(xSpeed >= -acceleration) {
                if (!onChain)
                    xSpeed = -acceleration;
                this.facing = LEFT;
            }
        } else if(onChain && facing == UP) {
            ySpeed = CHAIN_SPEED;
        } else if(onChain && facing == DOWN) {
            ySpeed = -CHAIN_SPEED;
        } else {
            if(onChain)
                ySpeed = 0;

            this.facing = NONE;
            float deceleration = DECELERATION_SPEED;
            if(!colliders[0])
                deceleration = DECELERATION_AIR_SPEED;

            if(Math.abs(xSpeed) <= deceleration) xSpeed = 0.0f;

            if(xSpeed != 0.0f)
                xSpeed += xSpeed > 0.0f ? -deceleration : deceleration;
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
