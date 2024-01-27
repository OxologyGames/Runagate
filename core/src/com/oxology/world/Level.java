package com.oxology.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.World;
import com.oxology.world.block.BaseBlock;
import com.oxology.world.block.BrickBlock;
import com.oxology.world.entity.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable {
    private static final long serialVersionUID = -234042330033409239L;

    transient private World world;

    private BaseBlock[][] tiles;
    private int x, y;
    private transient Texture snippet;
    private List<GameObject> gameObjects;
    private transient Player player;
    private transient Body playerBody;

    public Level(int x, int y) {
        tiles = new BaseBlock[80][45];
        this.x = x;
        this.y = y;
        world = new World(new Vector2(0, -1000), false);

        generateSnippet();

        gameObjects = new ArrayList<>();

        player = new Player(4, 6);
    }

    public void generateWorld() {
        world = new World(new Vector2(0, -10), false);

        for(int i = 0; i < 80; i++) {
            for(int j = 0; j < 45; j++) {
                if(tiles[i][j] instanceof BrickBlock) {
                    tiles[i][j].createBlock(world);
                }
            }
        }

        createPlayer();
    }

    private void createPlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(3+16, 5+16);

        playerBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.05f;

        playerBody.createFixture(fixtureDef);
        playerBody.setFixedRotation(true);

        shape.dispose();
    }

    public void generateSnippet() {
        SpriteBatch batch = new SpriteBatch();
        OrthographicCamera camera = new OrthographicCamera(2560, 1440);
        camera.translate(2560/2f, 1440/2f);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        Texture wallTexture = new Texture("level/wall.png");

        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, 384, 216, false);

        frameBuffer.begin();
        batch.begin();
        for(int i = 0; i < 80; i++) {
            for(int j = 0; j < 45; j++) {
                if(tiles[i][j] instanceof BrickBlock)
                    batch.draw(wallTexture, i*32, j*32, 32, 32);
            }
        }
        batch.end();
        frameBuffer.end();

        snippet = frameBuffer.getColorBufferTexture();
    }

    public BaseBlock[][] getTiles() {
        return tiles;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Texture getSnippet() {
        return snippet;
    }

    public void draw(OrthographicCamera camera, Box2DDebugRenderer debugRenderer) {
        debugRenderer.render(world, camera.combined);
    }

    public void update(float deltaTime) {
        player.update(deltaTime);
        world.step(deltaTime, 6, 2);
        float force = 100.0f;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerBody.applyForceToCenter(new Vector2(-force, 0), false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerBody.applyForceToCenter(new Vector2(force, 0), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerBody.applyForceToCenter(new Vector2(0, force), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            playerBody.applyForceToCenter(new Vector2(0, -force), true);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public World getWorld() {
        return world;
    }
}
