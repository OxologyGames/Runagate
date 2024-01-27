package com.oxology.world.block;

import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public abstract class BaseBlock implements Serializable {
    private static final long serialVersionUID = -3286461656821552124L;

    private float x, y;
    private final float density;
    private final float friction;

    protected BaseBlock(float x, float y, float density, float friction) {
        this.x = x;
        this.y = y;
        this.density = density;
        this.friction = friction;
    }

    public void createBlock(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 0.5f, y + 0.5f);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.density = density;
        fixture.friction = friction;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixture);
        shape.dispose();
    }
}
