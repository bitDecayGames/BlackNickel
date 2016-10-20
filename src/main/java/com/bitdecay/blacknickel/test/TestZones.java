package com.bitdecay.blacknickel.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bytebreakstudios.input.Key;
import com.bytebreakstudios.input.Keyboard;
import com.typesafe.config.Config;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TestZones {

    private final static Logger log = Logger.getLogger(TestZones.class);

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private List<Circle> startingCircles = new ArrayList<>();
    private List<Circle> endingCircles = new ArrayList<>();

    private boolean pressed = false;
    private boolean lastPressed = false;
    private boolean justPressed = false;
    private boolean justReleased = false;

    private OrthographicCamera cam = null;
    private Circle tmp = null;

    public TestZones(){}

    public TestZones(Config conf){
        conf.getConfigList("start").forEach(circleConf -> startingCircles.add(new Circle(circleConf)));
        conf.getConfigList("end").forEach(circleConf -> endingCircles.add(new Circle(circleConf)));
    }

    public void update(){
        lastPressed = pressed;
        pressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        justPressed = pressed && !lastPressed;
        justReleased = !pressed && lastPressed;

        Vector2 mousePos = mousePos();
        if (justPressed) {
            tmp = new Circle(mousePos.x, mousePos.y, 0);
            if (Keyboard.isKeyPressed(Key.COMMA)) startingCircles.add(tmp);
            else if (Keyboard.isKeyPressed(Key.PERIOD)) endingCircles.add(tmp);
            else startingCircles.add(tmp);
        } else if (justReleased) {
            tmp = null;
        } else if (pressed && tmp != null){
            tmp.r = Vector2.dst(tmp.x, tmp.y, mousePos.x, mousePos.y);
        }
    }

    public void render(OrthographicCamera cam) {
        this.cam = cam;
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
        startingCircles.forEach(circle -> circle.draw(shapeRenderer));
        shapeRenderer.setColor(Color.RED);
        endingCircles.forEach(circle -> circle.draw(shapeRenderer));
        shapeRenderer.end();
    }

    public boolean containedWithinStartingCircles(float x, float y){
        return startingCircles.stream().filter(circle -> circle.contains(x, y)).findFirst().isPresent();
    }

    public boolean containedWithinEndingCircles(float x, float y){
        return endingCircles.stream().filter(circle -> circle.contains(x, y)).findFirst().isPresent();
    }

    private Vector2 mousePos(){
        if (cam != null){
            Vector3 worldPos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            return new Vector2(worldPos.x, worldPos.y);
        } else return new Vector2(0, 0);
    }

    public String serialize(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"start\": ").append(startingCircles.toString()).append(",");
        sb.append("\"end\": ").append(endingCircles.toString());
        sb.append("}");
        return sb.toString();
    }

    private class Circle{
        public float x;
        public float y;
        public float r;
        public Circle(float x, float y, float r){
            this.x = x;
            this.y = y;
            this.r = r;
        }
        public Circle(Config conf){
            x = (float) conf.getDouble("x");
            y = (float) conf.getDouble("y");
            r = (float) conf.getDouble("r");
        }

        public boolean contains(float x, float y){
            return Vector2.dst(this.x, this.y, x, y) <= r;
        }

        public void draw(ShapeRenderer shapeRenderer){
            shapeRenderer.circle(x, y, r);
        }

        @Override
        public String toString() {
            return "{ \"x\": " + x + ", \"y\": " + y + ", \"r\": " + r + " }";
        }
    }
}

