package com.cyworks.famoussoundsbuttons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MyGdxGame extends ApplicationAdapter implements GestureListener {
	SpriteBatch batch;
    int app_state, i, nrSounds, nrMaxSounds, nrPage, nrMaxPages; //1 buttons pages
    float screenWidth, screenHeight;
    Texture plaquePic, buttonPic, pressedButtonPic;
    Sprite[] plaque, buttons, pressedButton;
    Sound sounds[];
    long buttonTime[];
    OrthographicCamera camera;
    Viewport viewport;
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont plaqueFont;
	
	@Override
	public void create () {
        float x, y;

        Gdx.input.setInputProcessor(new GestureDetector(this));

        app_state = 1;
        nrPage = 1;
        nrMaxPages = 1;

        camera = new OrthographicCamera();
        viewport = new FillViewport(1080, 1920, camera);
        viewport.apply();

        batch = new SpriteBatch();

        screenHeight = 1920;
        screenWidth = 1080;

        plaquePic = new Texture("plaque.png");
        buttonPic = new Texture("button.png");
        pressedButtonPic = new Texture("pressedButton.png");
        plaque = new Sprite[10];
        buttons = new Sprite[10];
        pressedButton = new Sprite[10];
        for (i = 1; i <= 6; i++) {
            plaque[i] = new Sprite(plaquePic);
            buttons[i] = new Sprite(buttonPic);
            pressedButton[i] = new Sprite(pressedButtonPic);
            if (i % 2 == 1)
                x = 25;
            else
                x = screenWidth / 2 + 100;
            if (i <= 2)
                y = 400;
            else if (i <= 4)
                y = 800 + plaque[i].getHeight();
            else
                y = 1400 + plaque[i].getHeight();
            plaque[i].setPosition(x,  y);
            x += (plaque[i].getWidth() - buttons[i].getWidth()) / 2;
            y -= 300;
            buttons[i].setPosition(x, y);
            pressedButton[i].setPosition(x, y);
        }

        nrSounds = 6;
        nrMaxSounds = 20;
        sounds = new Sound[nrMaxSounds];
        for (i = 1; i <= nrSounds; i++)
            sounds[i] = Gdx.audio.newSound(Gdx.files.internal("baDumTsss.mp3"));
        buttonTime = new long[nrMaxSounds];
	}

	@Override
    public void render () {
        camera.update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        if (app_state == 1) {
            for (i = 1; i <= 6; i++) {
                plaque[i].draw(batch);
                if (buttonTime[i] == 0)
                    buttons[i].draw(batch);
                else
                    pressedButton[i].draw(batch);
            }
        }
		batch.end();
        for (i = 1; i <= 6; i++) {
            if (buttonTime[i] > 0) {
                if (System.currentTimeMillis() - buttonTime[i] > 500)
                    buttonTime[i] = 0;
            }
        }
	}

    public void dispose() {
        int i;
        batch.dispose();
        buttonPic.dispose();
        pressedButtonPic.dispose();
        plaquePic.dispose();
        for (i = 1; i <= nrSounds; i++)
            sounds[i].dispose();
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector3 posScreen = new Vector3(), posWorld;
        posScreen.x = x;
        posScreen.y = y;
        posWorld = camera.unproject(posScreen);
        x = posWorld.x;
        y = posWorld.y;
        for (i = 1; i <= 6; i++) {
            if (x >= buttons[i].getX() && x <= buttons[i].getX() + buttons[i].getWidth() && y >= buttons[i].getY() && y <= buttons[i].getY() + buttons[i].getHeight()) {
                buttonTime[i] = System.currentTimeMillis();
                sounds[(nrPage - 1) * 6 + i].play();
                break;
            }
        }
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(screenWidth/2,screenHeight/2,0);
    }
}
