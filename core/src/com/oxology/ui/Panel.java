package com.oxology.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Panel extends UIElement {
    private static final float MAX_SPEED = 600;

    private static final int HIDDEN = 0;
    private static final int HIDING = 1;
    private static final int SHOWING = 2;
    private static final int SHOWED = 3;

    private final List<UIElement> elements;
    private final List<Float> elementX;
    private final float initialX;
    private final float width;

    private int state;
    private float speed;

    public Panel(float x, float y, float width) {
        this.elements = new ArrayList<>();
        this.elementX = new ArrayList<>();

        this.initialX = x;
        this.y = y;
        changeX(x - width);
        this.width = width;
        this.state = 0;
    }

    @Override
    public void draw(SpriteBatch batch) {
        for(UIElement element : elements) {
            element.draw(batch);
        }
    }

    @Override
    public void update(float delta) {
        if(state == SHOWING) {
            if(x > initialX-width/3) {
                if(speed - 9.5f > 0)
                    speed -= 9.5f;
                else
                    speed = 0;
            } else {
                if(speed + 15 < MAX_SPEED)
                    speed += 15;
                else
                    speed = MAX_SPEED;
            }

            if(x + speed * delta < initialX) {
                changeX(x + speed * delta);
            } else {
                changeX(initialX);
                state = SHOWED;
            }
        } else if(state == HIDING) {
            if(speed + 10 < MAX_SPEED)
                speed += 10;
            else
                speed = MAX_SPEED;

            if(x - speed * delta > initialX - width) {
                changeX(x - speed * delta);
            } else {
                changeX(initialX - width);
                state = HIDDEN;
            }
        }

        for(UIElement element : elements) {
            element.update(delta);
        }
    }

    private void changeX(float x) {
        this.x = x;
        for(int i = 0; i < elements.size(); i++) {
            elements.get(i).x = x + elementX.get(i);
        }
    }

    public void addElement(UIElement element) {
        this.elements.add(element);
        this.elementX.add(element.x);

        changeX(x);
    }

    public UIElement getElement(UIElement element) {
        for(UIElement oldElement : elements) {
            if(oldElement == element)
                return oldElement;
        }

        return null;
    }

    public void toggle() {
        if(state == HIDING || state == HIDDEN) {
            state = SHOWING;
        } else if(state == SHOWING || state == SHOWED) {
            state = HIDING;
        }

        speed = 0;
    }
}
