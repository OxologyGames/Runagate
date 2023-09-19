package com.oxology.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private Tile[][] tiles;
    private int x, y;
    private Texture snippet;
    private List<Chain> chains;

    public Level(int x, int y) {
        tiles = new Tile[40][30];
        this.x = x;
        this.y = y;

        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 30; j++) {
                tiles[i][j] = Tile.AIR;
            }
        }

        snippet = new Texture("level/levelBorder2.png");
        chains = new ArrayList<>();
    }

    public void generateSnippet() {
        SpriteBatch batch = new SpriteBatch();
        OrthographicCamera camera = new OrthographicCamera(320, 180);
        camera.translate(320/2f, 180/2f);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        Texture airTexture = new Texture("level/air.png");
        Texture wallTexture = new Texture("level/wall.png");

        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, 320, 180, false);

        frameBuffer.begin();
        batch.begin();
        for(int i = 0; i < 40; i++) {
            for(int j = 0; j < 30; j++) {
                Texture texture = airTexture;
                if(tiles[i][j] == Tile.WALL) texture = wallTexture;
                batch.draw(texture, 9+i*8, 18+j*6, 8, 6);
            }
        }
        batch.end();
        frameBuffer.end();

        TextureRegion region = new TextureRegion(frameBuffer.getColorBufferTexture());
        region.flip(true, true);
        snippet = frameBuffer.getColorBufferTexture();
    }

    public Level(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public List<Chain> getChains() {
        return chains;
    }

    public Tile[][] getTiles() {
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
}
