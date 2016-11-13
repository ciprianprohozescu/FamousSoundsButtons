package com.cyworks.famoussoundsbuttons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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
    int app_state, i, j, nrSounds, nrMaxSounds, nrPage, nrMaxPages; //1 buttons pages
    float screenWidth, screenHeight;
    Texture plaquePic, buttonPic, pressedButtonPic;
    Sprite[] plaque, buttons, pressedButton;
    Music sounds[];
    long buttonTime[];
    OrthographicCamera camera;
    Viewport viewport;
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont plaqueFont;
    public static final String TAG = "myMessage";
	
	@Override
	public void create () {
        float x, y;

        Gdx.input.setInputProcessor(new GestureDetector(this));

        app_state = 1;
        nrPage = 1;
        nrMaxPages = 3;

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
                y = 1400 + plaque[i].getHeight();
            else if (i <= 4)
                y = 800 + plaque[i].getHeight();
            else
                y = 400;
            plaque[i].setPosition(x,  y);
            x += (plaque[i].getWidth() - buttons[i].getWidth()) / 2;
            y -= 300;
            buttons[i].setPosition(x, y);
            pressedButton[i].setPosition(x, y);
        }

        nrSounds = 18;
        nrMaxSounds = 20;
        sounds = new Music[nrMaxSounds];
        sounds[1] = Gdx.audio.newMusic(Gdx.files.internal("baDumTsss.mp3"));
        sounds[2] = Gdx.audio.newMusic(Gdx.files.internal("silence.mp3"));
        sounds[3] = Gdx.audio.newMusic(Gdx.files.internal("oooh.mp3"));
        sounds[4] = Gdx.audio.newMusic(Gdx.files.internal("best_cry_ever.mp3"));
        sounds[5] = Gdx.audio.newMusic(Gdx.files.internal("applause.mp3"));
        sounds[6] = Gdx.audio.newMusic(Gdx.files.internal("old_ladies_clapping.mp3"));
        sounds[7] = Gdx.audio.newMusic(Gdx.files.internal("party_whistle.mp3"));
        sounds[8] = Gdx.audio.newMusic(Gdx.files.internal("party_whistle_beat.mp3"));
        sounds[9] = Gdx.audio.newMusic(Gdx.files.internal("drum1.mp3"));
        sounds[10] = Gdx.audio.newMusic(Gdx.files.internal("drum2.mp3"));
        sounds[11] = Gdx.audio.newMusic(Gdx.files.internal("it_was_at_this_moment.mp3"));
        sounds[12] = Gdx.audio.newMusic(Gdx.files.internal("surprise_motherfucker.mp3"));
        sounds[13] = Gdx.audio.newMusic(Gdx.files.internal("no_god_no.mp3"));
        sounds[14] = Gdx.audio.newMusic(Gdx.files.internal("nope.mp3"));
        sounds[15] = Gdx.audio.newMusic(Gdx.files.internal("oh_yeah.mp3"));
        sounds[16] = Gdx.audio.newMusic(Gdx.files.internal("uh_oh.mp3"));
        sounds[17] = Gdx.audio.newMusic(Gdx.files.internal("laugh1.mp3"));
        sounds[18] = Gdx.audio.newMusic(Gdx.files.internal("laugh2.mp3"));
        buttonTime = new long[nrMaxSounds];

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("motionPicture.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 75;
        fontParameter.color = Color.BLACK;
        plaqueFont = fontGenerator.generateFont(fontParameter);
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
        drawPlaqueText(nrPage);
		batch.end();
        for (i = 1; i <= 6; i++) {
            if (buttonTime[i] > 0) {
                if (System.currentTimeMillis() - buttonTime[i] > 300)
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
        plaqueFont.dispose();
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (velocityX < 0) {
            if (nrPage < nrMaxPages)
                nrPage++;
            else
                nrPage = 1;
        }
        else if (velocityX > 0) {
            if (nrPage > 1)
                nrPage--;
            else
                nrPage = nrMaxPages;
        }
        return true;
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

    public void drawPlaqueText(int x) {
        if (x == 1) {
            plaqueFont.draw(batch, "Ba Dum Tsss", plaque[1].getX() + 60, plaque[1].getY() + plaque[1].getHeight()- 40);
            plaqueFont.draw(batch, "Silence", plaque[2].getX() + 120, plaque[2].getY() + plaque[2].getHeight()- 40);
            plaqueFont.draw(batch, "Oooohhhh", plaque[3].getX() + 100, plaque[3].getY() + plaque[3].getHeight()- 40);
            plaqueFont.draw(batch, "Best Cry\n    Ever", plaque[4].getX() + 120, plaque[4].getY() + plaque[4].getHeight()- 10);
            plaqueFont.draw(batch, "Applause", plaque[5].getX() + 110, plaque[5].getY() + plaque[5].getHeight()- 40);
            plaqueFont.draw(batch, "Small Applause", plaque[6].getX() + 60, plaque[6].getY() + plaque[6].getHeight()- 40);
        }
        else if (x == 2) {
            plaqueFont.draw(batch, "Party Whistle", plaque[1].getX() + 60, plaque[1].getY() + plaque[1].getHeight()- 40);
            plaqueFont.draw(batch, "Party Whistle\n      Beat", plaque[2].getX() + 60, plaque[2].getY() + plaque[2].getHeight()- 10);
            plaqueFont.draw(batch, "Drum One", plaque[3].getX() + 90, plaque[3].getY() + plaque[3].getHeight()- 40);
            plaqueFont.draw(batch, "Drum Two", plaque[4].getX() + 90, plaque[4].getY() + plaque[4].getHeight()- 40);
            plaqueFont.draw(batch, "It Was At\n This Moment", plaque[5].getX() + 60, plaque[5].getY() + plaque[5].getHeight() - 10);
            plaqueFont.draw(batch, "Surprise\n Motherf**ker", plaque[6].getX() + 60, plaque[6].getY() + plaque[6].getHeight()- 10);
        }
        else if (x == 3) {
            plaqueFont.draw(batch, "No God No", plaque[1].getX() + 60, plaque[1].getY() + plaque[1].getHeight()- 40);
            plaqueFont.draw(batch, "Nope", plaque[2].getX() + 60, plaque[2].getY() + plaque[2].getHeight()- 10);
            plaqueFont.draw(batch, "Ooooh Yeah", plaque[3].getX() + 90, plaque[3].getY() + plaque[3].getHeight()- 40);
            plaqueFont.draw(batch, "Uh Oh", plaque[4].getX() + 90, plaque[4].getY() + plaque[4].getHeight()- 40);
            plaqueFont.draw(batch, "Laugh One", plaque[5].getX() + 60, plaque[5].getY() + plaque[5].getHeight() - 10);
            plaqueFont.draw(batch, "Laugh Two", plaque[6].getX() + 60, plaque[6].getY() + plaque[6].getHeight()- 10);
        }
    }
}
