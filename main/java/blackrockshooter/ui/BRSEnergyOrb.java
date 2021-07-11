package blackrockshooter.ui;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import static blackrockshooter.BlackRockShooterMod.makeEnergyOrbPath;

public class BRSEnergyOrb extends CustomEnergyOrb {
    private static final float ORB_IMG_SCALE = 1.15f * Settings.scale;
    private static final int LAYER_COUNT = 5;
    private final Texture mask;
    private final Texture border;
    private final FrameBuffer fbo;
    private final Texture[] layers = new Texture[5];
    private final Texture[] layers_d = new Texture[5];

    public BRSEnergyOrb(String[] orbTexturePaths) {
        super(orbTexturePaths, makeEnergyOrbPath("vfx.png"), null);
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
        this.mask = ImageMaster.loadImage(makeEnergyOrbPath("mask.png"));
        this.border = ImageMaster.loadImage(makeEnergyOrbPath("border.png"));
        for (int i = 0; i<LAYER_COUNT; ++i) {
            layers[i] = ImageMaster.loadImage(makeEnergyOrbPath("layer" + (i+1) + ".png"));
        }
        for (int i = 0; i<LAYER_COUNT; ++i) {
            layers_d[i] = ImageMaster.loadImage(makeEnergyOrbPath("layer" + (i+1) + "d.png"));
        }
    }

    @Override
    public Texture getEnergyImage() {
        return this.orbVfx;
    }

    @Override
    public void updateOrb(int energyCount)
    {
        if (energyCount == 0) {
            angles[0] += Gdx.graphics.getDeltaTime() * -5;
            angles[1] += Gdx.graphics.getDeltaTime() * 5;
            angles[2] += Gdx.graphics.getDeltaTime() * -8;
            angles[3] += Gdx.graphics.getDeltaTime() * 8;
            angles[4] += Gdx.graphics.getDeltaTime() * 12;
        } else {
            angles[0] += Gdx.graphics.getDeltaTime() * -20;
            angles[1] += Gdx.graphics.getDeltaTime() * 20;
            angles[2] += Gdx.graphics.getDeltaTime() * -40;
            angles[3] += Gdx.graphics.getDeltaTime() * 40;
            angles[4] += Gdx.graphics.getDeltaTime() * 60;
        }
    }
    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y)
    {
        sb.setColor(Color.WHITE);
        sb.end();
        fbo.begin();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true, true, true, true);
        sb.begin();

        sb.setColor(Color.WHITE);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if (enabled) {
            for (int i = 0; i < LAYER_COUNT; ++i) {
                sb.draw(layers[i], current_x - 64, current_y - 64, 64, 64, 128, 128, ORB_IMG_SCALE, ORB_IMG_SCALE, angles[i], 0, 0, 128, 128, false, false);
            }
        } else {
            for (int i = 0; i < LAYER_COUNT; ++i) {
                sb.draw(layers_d[i], current_x - 64, current_y - 64, 64, 64, 128, 128, ORB_IMG_SCALE, ORB_IMG_SCALE, angles[i], 0, 0, 128, 128, false, false);
            }
        }

        sb.setBlendFunction(0, GL20.GL_SRC_ALPHA);
        sb.setColor(new Color(1, 1, 1, 1));
        sb.draw(mask, current_x - 64, current_y - 64, 64, 64, 128, 128, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sb.end();

        fbo.end();
        sb.begin();
        TextureRegion drawTex = new TextureRegion(fbo.getColorBufferTexture());
        drawTex.flip(false, true);
        sb.draw(drawTex, -Settings.VERT_LETTERBOX_AMT, -Settings.HORIZ_LETTERBOX_AMT);
        sb.draw(border, current_x - 64, current_y - 64, 64, 64, 128, 128, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
    }
}