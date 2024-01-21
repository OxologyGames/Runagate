package com.oxology.world.entity;

import com.oxology.world.GameObject;
import com.oxology.world.Level;
import com.oxology.world.Tile;

public class Entity extends GameObject {
    private static final long serialVersionUID = -3038644230555076026L;

    public static final float GRAVITY = 23.0f;

    protected static final int NONE = 0;
    protected static final int LEFT = 1;
    protected static final int RIGHT = 2;
    protected static final int UP = 3;
    protected static final int DOWN = 4;

    private static final float ACCELERATION_SPEED = 7.0f;
    private static final float ACCELERATION_AIR_SPEED = 4.5f;
    private static final float DECELERATION_SPEED = 0.8f;
    private static final float DECELERATION_AIR_SPEED = 0.04f;

    private static final float JUMP_SPEED = 8.0f;
    private static final float CHAIN_SPEED = 5.0f;

    protected final boolean[] colliders;
    private float xSpeed, ySpeed;
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

        if((!colliders[0] && !onChain) || ySpeed > 0) {
            ySpeed -= GRAVITY*deltaTime;
        }

        if(colliders[0] && ySpeed < 0) {
            ySpeed = 0;
            y = Math.round(y);
        }

        if(colliders[1] && xSpeed < 0) {
            xSpeed = 0;
            x = Math.round(x*2f)/2f;
        }

        if(colliders[2] && xSpeed > 0) {
            xSpeed = 0;
            x = Math.round(x*2f)/2f;
        }

        x += xSpeed*deltaTime;
        y += ySpeed*deltaTime;
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

        if(!colliders[2] && facing == RIGHT) {
            if(xSpeed <= acceleration) {
                if (!onChain)
                    xSpeed = acceleration;
                this.facing = RIGHT;
            }
        } else if(!colliders[1] && facing == LEFT) {
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

    private void setCollider(boolean value, int index) {
        this.colliders[index] = value;
    }

    public void checkForCollisions(float deltaTime, Level level) {
        Tile[][] tiles = level.getTiles();

        boolean collider0 =
                tiles[(int) (getNextX(deltaTime) + 0.6f)][getNextY(deltaTime) % 1 == 0 ? (int) Math.floor(getNextY(deltaTime)-1) : (int) Math.floor(getNextY(deltaTime))] == Tile.WALL ||
                        tiles[(int) (getNextX(deltaTime) + 1.4f)][getNextY(deltaTime) % 1 == 0 ? (int) Math.floor(getNextY(deltaTime)-1) : (int) Math.floor(getNextY(deltaTime))] == Tile.WALL;

        boolean collider1 =
                tiles[(getNextX(deltaTime)*2) % 1 == 0 ? (int) (getNextX(deltaTime)-0.5f) : (int) (getNextX(deltaTime)+0.5f)][(int) Math.floor(getNextY(deltaTime)+0.1f)] == Tile.WALL ||
                        tiles[(getNextX(deltaTime)*2) % 1 == 0 ? (int) (getNextX(deltaTime)-0.5f) : (int) (getNextX(deltaTime)+0.5f)][(int) Math.floor(getNextY(deltaTime)+1.1f)] == Tile.WALL ||
                        tiles[(getNextX(deltaTime)*2) % 1 == 0 ? (int) (getNextX(deltaTime)-0.5f) : (int) (getNextX(deltaTime)+0.5f)][(int) Math.floor(getNextY(deltaTime)+1.9f)] == Tile.WALL;

        boolean collider2 =
                tiles[((getNextX(deltaTime)*2) % 1 == 0 ? (int) (getNextX(deltaTime)+1.0f) : (int) (getNextX(deltaTime)+0.5f))+1][(int) Math.floor(getNextY(deltaTime)+0.1f)] == Tile.WALL ||
                        tiles[((getNextX(deltaTime)*2) % 1 == 0 ? (int) (getNextX(deltaTime)+1.0f) : (int) (getNextX(deltaTime)+0.5f))+1][(int) Math.floor(getNextY(deltaTime)+1.1f)] == Tile.WALL ||
                        tiles[((getNextX(deltaTime)*2) % 1 == 0 ? (int) (getNextX(deltaTime)+1.0f) : (int) (getNextX(deltaTime)+0.5f))+1][(int) Math.floor(getNextY(deltaTime)+1.9f)] == Tile.WALL;

        boolean collider3 = false;//TODO

        boolean onChain =
                tiles[(int) (getNextX(deltaTime) + 1.0f)][getNextY(deltaTime) % 1 == 0 ? (int) Math.floor(getNextY(deltaTime)) : (int) Math.floor(getNextY(deltaTime)+1)] == Tile.CHAIN &&
                        Math.abs((getNextX(deltaTime) - 1) - (float) Math.floor(getNextX(deltaTime)) + 0.5f) < 0.05f;

        colliders[0] = collider0;
        colliders[1] = collider1;
        colliders[2] = collider2;
        colliders[3] = collider3;
        onChain(onChain);
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

    private float getNextX(float deltaTime) {
        return x + (xSpeed * deltaTime);
    }

    private float getNextY(float deltaTime) {
        return y + (ySpeed * deltaTime);
    }

    protected float getXSpeed() {
        return xSpeed;
    }

    protected float getYSpeed() {
        return ySpeed;
    }
}
