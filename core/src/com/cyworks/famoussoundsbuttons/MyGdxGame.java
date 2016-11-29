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
    Texture plaquePic, buttonPic, pressedButtonPic, backgroundPic;
    Sprite background;
    Sprite[] plaque, buttons, pressedButton;
    Music sounds[];
    long buttonTime[];
    OrthographicCamera camera;
    Viewport viewport;
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont plaqueFont, instructionsFont;
    public static final String TAG = "myMessage";
    boolean swiped;
	
	@Override
	public void create () {
        float x, y;

        Gdx.input.setInputProcessor(new GestureDetector(this));

        app_state = 1;
        nrPage = 1;
        nrMaxPages = 5;

        camera = new OrthographicCamera();
        viewport = new FillViewport(1080, 1920, camera);
        viewport.apply();

        batch = new SpriteBatch();

        screenHeight = 1920;
        screenWidth = 1080;

        plaquePic = new Texture("plaque.png");
        buttonPic = new Texture("button.png");
        pressedButtonPic = new Texture("pressedButton.png");
        backgroundPic = new Texture("background.jpg");
        background = new Sprite(backgroundPic);
        background.setPosition(0, 0);
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

        nrSounds = 24;
        nrMaxSounds = 40;
        sounds = new Music[nrMaxSounds];
        sounds[1] = Gdx.audio.newMusic(Gdx.files.internal("baDumTsss.mp3"));
        sounds[2] = Gdx.audio.newMusic(Gdx.files.internal("silence.mp3"));
        sounds[3] = Gdx.audio.newMusic(Gdx.files.internal("oooh.mp3"));
        sounds[4] = Gdx.audio.newMusic(Gdx.files.internal("best_cry_ever.mp3"));
        sounds[5] = Gdx.audio.newMusic(Gdx.files.internal("applause.mp3"));
        sounds[6] = Gdx.audio.newMusic(Gdx.files.internal("old_ladies_clapping.mp3"));
        sounds[7] = Gdx.audio.newMusic(Gdx.files.internal("party_whistle.mp3"));
        sounds[8] = Gdx.audio.newMusic(Gdx.files.internal("party_whistle_beat.mp3"));
        sounds[9] = Gdx.audio.newMusic(Gdx.files.internal("fart.mp3"));
        sounds[10] = Gdx.audio.newMusic(Gdx.files.internal("this_is_patrick.mp3"));
        sounds[11] = Gdx.audio.newMusic(Gdx.files.internal("it_was_at_this_moment.mp3"));
        sounds[12] = Gdx.audio.newMusic(Gdx.files.internal("surprise_motherfucker.mp3"));
        sounds[13] = Gdx.audio.newMusic(Gdx.files.internal("no_god_no.mp3"));
        sounds[14] = Gdx.audio.newMusic(Gdx.files.internal("nope.mp3"));
        sounds[15] = Gdx.audio.newMusic(Gdx.files.internal("oh_yeah.mp3"));
        sounds[16] = Gdx.audio.newMusic(Gdx.files.internal("uh_oh.mp3"));
        sounds[17] = Gdx.audio.newMusic(Gdx.files.internal("laugh1.mp3"));
        sounds[18] = Gdx.audio.newMusic(Gdx.files.internal("laugh2.mp3"));
        sounds[19] = Gdx.audio.newMusic(Gdx.files.internal("booing.mp3"));
        sounds[20] = Gdx.audio.newMusic(Gdx.files.internal("wow.mp3"));
        sounds[21] = Gdx.audio.newMusic(Gdx.files.internal("dun_dun_dun.mp3"));
        sounds[22] = Gdx.audio.newMusic(Gdx.files.internal("nobody_expects.mp3"));
        sounds[23] = Gdx.audio.newMusic(Gdx.files.internal("wilhelm_scream.mp3"));
        sounds[24] = Gdx.audio.newMusic(Gdx.files.internal("boo.mp3"));
        sounds[25] = Gdx.audio.newMusic(Gdx.files.internal("japanese_yes.mp3"));
        sounds[26] = Gdx.audio.newMusic(Gdx.files.internal("japanese_no.mp3"));
        sounds[27] = Gdx.audio.newMusic(Gdx.files.internal("ladies_and_gentlemen.mp3"));
        sounds[28] = Gdx.audio.newMusic(Gdx.files.internal("beep.mp3"));
        sounds[29] = Gdx.audio.newMusic(Gdx.files.internal("honk.mp3"));
        sounds[30] = Gdx.audio.newMusic(Gdx.files.internal("wrong.mp3"));
        buttonTime = new long[nrMaxSounds];

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("motionPicture.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 75;
        fontParameter.color = Color.BLACK;
        plaqueFont = fontGenerator.generateFont(fontParameter);
        fontParameter.size = 100;
        instructionsFont = fontGenerator.generateFont(fontParameter);
	}

	@Override
    public void render () {
        camera.update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        if (app_state == 1) {
            background.draw(batch);
            if (nrPage > 1)
                swiped = true;
            if (!swiped)
                instructionsFont.draw(batch, "Swipe to change the page", screenWidth / 2 - 320, screenHeight - 30);
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
        backgroundPic.dispose();
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
            plaqueFont.draw(batch, "Oooohhhh", plaque[3].getX() + 110, plaque[3].getY() + plaque[3].getHeight()- 40);
            plaqueFont.draw(batch, "Best Cry\n    Ever", plaque[4].getX() + 120, plaque[4].getY() + plaque[4].getHeight()- 10);
            plaqueFont.draw(batch, "Applause", plaque[5].getX() + 110, plaque[5].getY() + plaque[5].getHeight()- 40);
            plaqueFont.draw(batch, "Small Applause", plaque[6].getX() + 60, plaque[6].getY() + plaque[6].getHeight()- 40);
        }
        else if (x == 2) {
            plaqueFont.draw(batch, "Party Whistle", plaque[1].getX() + 60, plaque[1].getY() + plaque[1].getHeight()- 40);
            plaqueFont.draw(batch, "Party Whistle\n      Beat", plaque[2].getX() + 60, plaque[2].getY() + plaque[2].getHeight()- 10);
            plaqueFont.draw(batch, "Fart", plaque[3].getX() + 160, plaque[3].getY() + plaque[3].getHeight()- 40);
            plaqueFont.draw(batch, "This Is Patrick", plaque[4].getX() + 50, plaque[4].getY() + plaque[4].getHeight()- 40);
            plaqueFont.draw(batch, "It Was At\n This Moment", plaque[5].getX() + 60, plaque[5].getY() + plaque[5].getHeight() - 10);
            plaqueFont.draw(batch, "Surprise\n Motherf**ker", plaque[6].getX() + 60, plaque[6].getY() + plaque[6].getHeight()- 10);
        }
        else if (x == 3) {
            plaqueFont.draw(batch, "No God No", plaque[1].getX() + 70, plaque[1].getY() + plaque[1].getHeight()- 40);
            plaqueFont.draw(batch, "Nope", plaque[2].getX() + 140, plaque[2].getY() + plaque[2].getHeight()- 40);
            plaqueFont.draw(batch, "Ooooh Yeah", plaque[3].getX() + 80, plaque[3].getY() + plaque[3].getHeight()- 40);
            plaqueFont.draw(batch, "Uh Oh", plaque[4].getX() + 120, plaque[4].getY() + plaque[4].getHeight()- 40);
            plaqueFont.draw(batch, "Laugh One", plaque[5].getX() + 90, plaque[5].getY() + plaque[5].getHeight() - 40);
            plaqueFont.draw(batch, "Laugh Two", plaque[6].getX() + 90, plaque[6].getY() + plaque[6].getHeight()- 40);
        }
        else if (x == 4) {
            plaqueFont.draw(batch, "Crowd Booing", plaque[1].getX() + 70, plaque[1].getY() + plaque[1].getHeight()- 40);
            plaqueFont.draw(batch, "Wow!", plaque[2].getX() + 140, plaque[2].getY() + plaque[2].getHeight()- 40);
            plaqueFont.draw(batch, "Dun Dun\n  Duuun!", plaque[3].getX() + 110, plaque[3].getY() + plaque[3].getHeight()- 10);
            plaqueFont.draw(batch, "Nobody Expects", plaque[4].getX() + 50, plaque[4].getY() + plaque[4].getHeight()- 40);
            plaqueFont.draw(batch, "Wilhelm Scream", plaque[5].getX() + 40, plaque[5].getY() + plaque[5].getHeight() - 40);
            plaqueFont.draw(batch, "One Man\n  Booing", plaque[6].getX() + 110, plaque[6].getY() + plaque[6].getHeight()- 10);
        }
        else if (x == 5) {
            plaqueFont.draw(batch, "Japanese Yes", plaque[1].getX() + 70, plaque[1].getY() + plaque[1].getHeight()- 40);
            plaqueFont.draw(batch, "Japanese No", plaque[2].getX() + 70, plaque[2].getY() + plaque[2].getHeight()- 40);
            plaqueFont.draw(batch, "Ladies And\n  Gentlemen", plaque[3].getX() + 80, plaque[3].getY() + plaque[3].getHeight()- 10);
            plaqueFont.draw(batch, "Beep", plaque[4].getX() + 140, plaque[4].getY() + plaque[4].getHeight()- 40);
            plaqueFont.draw(batch, "Honk", plaque[5].getX() + 140, plaque[5].getY() + plaque[5].getHeight() - 40);
            plaqueFont.draw(batch, "Wrong", plaque[6].getX() + 140, plaque[6].getY() + plaque[6].getHeight()- 40);
        }
    }
}
